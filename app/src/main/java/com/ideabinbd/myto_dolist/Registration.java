package com.ideabinbd.myto_dolist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private ProgressDialog pd;
    private EditText edtName,edtEmail,edtPassword;
    private Button btnReg;
    private RequestQueue registrationNetworkQueue;
    private String URL="http://10.0.3.2/mycrud/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        pd=new ProgressDialog(this);
        pd.setTitle("loading...");
        pd.setMessage("Please Wait");

        edtEmail=(EditText) findViewById(R.id.edt_reg_email);
        edtName=(EditText) findViewById(R.id.edt_reg_name);
        edtPassword=(EditText) findViewById(R.id.edt_reg_password);
        btnReg= (Button) findViewById(R.id.btn_reg_register);
        registrationNetworkQueue= Volley.newRequestQueue(this);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                String strEmail,strName,strPassword;
                strEmail=edtEmail.getText().toString();
                strName=edtName.getText().toString();
                strPassword=edtPassword.getText().toString();

                sendDataToServer(strEmail,strName,strPassword);
            }
        });

    }

    private void sendDataToServer(final String strEmail, final String strName, final String strPassword) {
        StringRequest saveDataRequest= new StringRequest(Request.Method.POST, URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("serverResponse",response);
                Toast.makeText(Registration.this,response , Toast.LENGTH_SHORT).show();
                if (response.equals("data Entered")){
                    finish();
                    startActivity(new Intent(Registration.this,Login.class));
                }
                /*
                try {
                    JSONArray fullData= new JSONArray(response);
                    names=new String[fullData.length()];
                    listArray=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,names);
                    dataList.setAdapter(listArray);
                    for (int i=0;i<fullData.length();i++){
                        JSONObject singleData= fullData.getJSONObject(i);
                        names[i]=singleData.getString("name");
                      Log.d("serverResponse",names[i]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Data is not JSON", Toast.LENGTH_SHORT).show();
                }

                listArray.notifyDataSetChanged();

                */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("serverError",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> myUserData=new HashMap<>();
                myUserData.put("name",strName);
                myUserData.put("email",strEmail);
                myUserData.put("password",strPassword);
                return myUserData;
            }
        };
        registrationNetworkQueue.add(saveDataRequest);
    }
}

