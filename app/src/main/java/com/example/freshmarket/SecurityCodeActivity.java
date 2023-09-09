package com.example.freshmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecurityCodeActivity extends AppCompatActivity {

    EditText txtcode,txtnewpass;
    Button btnuppass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_code);
        txtcode=findViewById(R.id.txtcode);
        txtnewpass=findViewById(R.id.txtnewpass);
        btnuppass=findViewById(R.id.btnuppass);
    }

    public void confirmcode(View view) {

        if(LoginActivity.newpass==Integer.parseInt(txtcode.getText().toString()))
        {
            txtnewpass.setVisibility(View.VISIBLE);
            btnuppass.setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(this, "Security code invaild", Toast.LENGTH_SHORT).show();
            txtnewpass.setVisibility(View.INVISIBLE);
            btnuppass.setVisibility(View.INVISIBLE);
        }

    }

    public void updatepass(View view) {

        Database db=new Database();
        db.ConnectDB();
        db.RunDML("update customers set password='"+txtnewpass.getText().toString()+"' where id='"+LoginActivity.id+"'");
        Toast.makeText(this, "Password has been updated", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SecurityCodeActivity.this,LoginActivity.class));
    }
}