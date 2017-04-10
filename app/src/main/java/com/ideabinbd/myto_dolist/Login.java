package com.ideabinbd.myto_dolist;

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

public class Login extends AppCompatActivity {

    private EditText edtEmail,edtPassword;
    private Button btnLogin,btnRegister;
    private RequestQueue registrationNetworkQueue;
    private String onlineURL="http://10.0.3.2/mycrud/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail=(EditText) findViewById(R.id.edt_login_email);
        edtPassword=(EditText) findViewById(R.id.edt_login_password);

        btnLogin=(Button) findViewById(R.id.btn_login_login);
        btnRegister=(Button) findViewById(R.id.btn_login_register);
        registrationNetworkQueue= Volley.newRequestQueue(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail,strPassword;
                strEmail=edtEmail.getText().toString();
                strPassword=edtPassword.getText().toString();
               // startActivity(new Intent(Login.this,Home.class));
                sendDataToServer(strEmail,strPassword);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });
    }

    private void sendDataToServer(final String strEmail, final String strPassword) {
        StringRequest saveDataRequest= new StringRequest(Request.Method.POST, onlineURL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("serverResponse",response);
                Toast.makeText(Login.this,response , Toast.LENGTH_SHORT).show();

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
                myUserData.put("email",strEmail);
                myUserData.put("password",strPassword);
                return myUserData;
            }
        };
        registrationNetworkQueue.add(saveDataRequest);
    }
}
