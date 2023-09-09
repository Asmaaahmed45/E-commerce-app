package com.example.freshmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileActivity extends AppCompatActivity {
    EditText txtname,txtphone,txtemail,txtpassword,txtaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txtname=findViewById(R.id.txtname);
        txtaddress=findViewById(R.id.txtaddress);
        txtemail=findViewById(R.id.txtemail);
        txtpassword=findViewById(R.id.txtpass);
        txtphone=findViewById(R.id.txtphone);
        Database db=new Database();
        Connection connection= db.ConnectDB();
        if(connection==null){
            Toast.makeText(ProfileActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }
        else {
            ResultSet resultSet=db.RunSearch("select * from customers  where id='"+LoginActivity.id+"' ");
            try {
                if(resultSet.next()){
                    txtname.setText(resultSet.getString(2));
                    txtpassword.setText(resultSet.getString(3));
                    txtphone.setText(resultSet.getString(4));
                    txtemail.setText(resultSet.getString(5));
                    txtaddress.setText(resultSet.getString(6));

                }



            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void update(View view) {
        Database db=new Database();
        Connection connection= db.ConnectDB();
        if(connection==null){
            Toast.makeText(ProfileActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }
        else {
           String res= db.RunDML("update customers set name='"+txtname.getText()+"',email='"+txtemail.getText()+"',password='"+txtpassword.getText()+"',phone='"+txtphone.getText()+"',address='"+txtaddress.getText()+"' where id='"+LoginActivity.id+"'");
           if(res.equals("Ok"))
               Toast.makeText(ProfileActivity.this,"your profile updated successfully",Toast.LENGTH_LONG).show();


        }
    }
}