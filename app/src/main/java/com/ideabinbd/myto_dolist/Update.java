package com.ideabinbd.myto_dolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Update extends AppCompatActivity {

    EditText edtUpdateTitle,edtUpdateDesc;
    Button btnUpdate;
    String id,title,description;

    RequestQueue updateReq;
    private String URL="http://10.0.3.2/mycrud/updatedata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        id=getIntent().getExtras().getString("id");
        title=getIntent().getExtras().getString("title");
        description=getIntent().getExtras().getString("desc");

        updateReq= Volley.newRequestQueue(this);

        edtUpdateDesc=(EditText) findViewById(R.id.edt_task_descupdate);
        edtUpdateTitle=(EditText) findViewById(R.id.edt_task_titleupdate);

        btnUpdate=(Button) findViewById(R.id.btn_task_update);

        edtUpdateTitle.setText(title);
        edtUpdateDesc.setText(description);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedTitle,updatedDescription;
                updatedTitle= edtUpdateTitle.getText().toString();
                updatedDescription= edtUpdateDesc.getText().toString();
            }
        });
    }
}
