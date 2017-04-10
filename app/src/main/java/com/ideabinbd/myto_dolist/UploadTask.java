package com.ideabinbd.myto_dolist;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class UploadTask extends AppCompatActivity {
    private ProgressDialog pd;
    private EditText edtTitle,edtDesc;
    private Button btnUpload;
    private RequestQueue rq;
    private String URL="http://10.0.3.2/mycrud/uploadtask.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_task);

        pd=new ProgressDialog(this);
        pd.setTitle("loading...");
        pd.setMessage("Please Wait");

        edtTitle=(EditText) findViewById(R.id.edt_task_title);
        edtDesc=(EditText) findViewById(R.id.edt_task_desc);

        btnUpload=(Button) findViewById(R.id.btn_task_upload);

        rq= Volley.newRequestQueue(this);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strTitle,strDesc;
                strTitle=edtTitle.getText().toString();
                strDesc=edtDesc.getText().toString();
            }
        });
    }
}
