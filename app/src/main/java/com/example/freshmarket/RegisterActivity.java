package com.example.freshmarket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class RegisterActivity extends AppCompatActivity {
    EditText txtname,txtphone,txtemail,txtpassword,txtdate;
    ImageView img;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtname=findViewById(R.id.txtname);
        img=findViewById(R.id.imgloc);
        txtemail=findViewById(R.id.txtemail);
        txtpassword=findViewById(R.id.txtpass);
        txtphone=findViewById(R.id.txtphone);
        txtdate=findViewById(R.id.txtdate);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        txtdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtdate.setText(sdf.format(myCalendar.getTime()));
    }

    public void register(View view) {
        if(txtname.getText().toString().isEmpty()){
            txtname.setError("Enter username");
            txtname.requestFocus();
        }
        else if(txtphone.getText().toString().isEmpty()){
            txtphone.setError("Enter phone");
            txtphone.requestFocus();
        }
        else if(txtpassword.getText().toString().isEmpty()){
            txtpassword.setError("Enter password");
            txtpassword.requestFocus();
        }
        else if(txtemail.getText().toString().isEmpty()){
            txtemail.setError("Enter E-mail");
            txtemail.requestFocus();
        }

        else if(txtdate.getText().toString().isEmpty()){
            txtdate.setError("Enter Birthdate");
            txtdate.requestFocus();
        }
        else {
            String em="^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
            if(txtemail.getText().toString().matches(em))
            {
                Database db=new Database();
                Connection conn=db.ConnectDB();
                if(conn==null)
                    Toast.makeText(this,"No Internet connection",Toast.LENGTH_SHORT).show();
                else {
                    String msg=db.RunDML("insert into  customers (name,password,phone,email,latitude,longitude,birthdate) values ('"+txtname.getText()+"','"+txtpassword.getText()+"','"+txtphone.getText()+"','"+txtemail.getText()+"','"+MapsActivity.lat+"','"+MapsActivity.lang+"','"+txtdate.getText()+"')");
                    if(msg.equals("Ok")) {
                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(RegisterActivity.this).setTitle("Registration")
                                .setMessage("You registerd successfully").setIcon(R.drawable.marketlogo)
                                .setPositiveButton("Thanks",null).setNegativeButton("Go to login", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                    }
                                }) ;
                        alertDialog.create().show();
                    }
                    else if(msg.contains("UQPhone"))
                    {
                        txtphone.setError("This phone already exist , try again");
                        txtphone.requestFocus();
                    }
                    else if(msg.contains("UQEmail"))
                    {
                        txtemail.setError("This email already exsit");
                        txtemail.requestFocus();
                    }
                    else
                        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();


                }
            }
            else {
                Toast.makeText(this, "Invaild E-mail format", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void map(View view) {
        startActivity(new Intent(RegisterActivity.this,MapsActivity.class));

    }
}