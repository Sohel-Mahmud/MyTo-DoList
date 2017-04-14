package com.ideabinbd.myto_dolist;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    private ProgressDialog pd;
    ListView dataList;
    ArrayAdapter listArray;

    String[] id,title,description;

    String URL="http://10.0.3.2/mycrud/gettasks.php";

    String deleteURL="http://10.0.3.2/mycrud/deletedata.php";

    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        pd=new ProgressDialog(this);
        pd.setTitle("loading...");
        pd.setMessage("Please Wait");

        dataList=(ListView) findViewById(R.id.list_home_datalist);
        rq= Volley.newRequestQueue(this);

        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent updateIntent=new Intent(Home.this,Update.class);
                updateIntent.putExtra("id",id[i]);
                updateIntent.putExtra("title",title[i]);
                updateIntent.putExtra("desc",description[i]);
                startActivity(updateIntent);
            }
        });

        dataList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder confirmDelete=new AlertDialog.Builder(Home.this);

                confirmDelete.setTitle("Do you really want to delete??");
                confirmDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendIdToServer(id[position]);
                        dialogInterface.dismiss();
                        fetchData();
                    }
                });
                confirmDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                confirmDelete.show();
                return true;
            }
        });

        fetchData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(new Intent(Home.this,UploadTask.class));
            }
        });


    }

    private void sendIdToServer(final String s) {
        StringRequest deleteData=new StringRequest(Request.Method.POST, deleteURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Home.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("id",s);
                return map;
            }
        };
        rq.add(deleteData);
    }

    private void fetchData() {
        StringRequest getAllData= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("serverResponse",response);
                try {
                    JSONArray totalData=new JSONArray(response);
                    id=new String[totalData.length()];
                    title=new String[totalData.length()];
                    description=new String[totalData.length()];

                    listArray=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,title);
                    for(int i=0;i< totalData.length();i++){
                        JSONObject singleData= totalData.getJSONObject(i);
                        id[i]= singleData.getString("id");
                        title[i]= singleData.getString("title");
                        description[i]= singleData.getString("description");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

               dataList.setAdapter(listArray);
                listArray.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(getAllData);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchData();
    }
}
