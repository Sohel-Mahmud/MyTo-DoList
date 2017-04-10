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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private ProgressDialog pd;
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
        pd=new ProgressDialog(this);
        pd.setTitle("loading...");
        pd.setMessage("Please Wait");

        btnLogin=(Button) findViewById(R.id.btn_login_login);
        btnRegister=(Button) findViewById(R.id.btn_login_register);
        registrationNetworkQueue= Volley.newRequestQueue(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
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
                String[] userData=new String[4];
                Log.d("serverResponse",response);
               // Toast.makeText(Login.this,response , Toast.LENGTH_SHORT).show();
                try {
                    JSONObject fullData= new JSONObject(response);
                    userData[0]=fullData.getString("email");
                    userData[1]=fullData.getString("name");
                    userData[2]=fullData.getString("password");
                    startActivity(new Intent(Login.this,Home.class));
                    pd.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this, "Data is not JSON", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }

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
