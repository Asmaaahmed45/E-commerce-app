package com.example.freshmarket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class LoginActivity extends AppCompatActivity {

    EditText txtuser,txtpass;
    TextView textAdmin;
    CheckBox chk;

    public static String id=null ,name;
    public static int  newpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtpass=findViewById(R.id.txtpassword);
        txtuser=findViewById(R.id.txtuser);
        chk=findViewById(R.id.checkBox);
        textAdmin=findViewById(R.id.text_admin);

        SharedPreferences sh=getSharedPreferences("Fresh",MODE_PRIVATE);
        name=sh.getString("name",null);
        id=sh.getString("id",null);
        if(name!=null)
            startActivity(new Intent(LoginActivity.this,MainUserActivity.class));

    }

    public void gotoregitser(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    public void LoginNow(View view) {

        if(txtuser.getText().toString().isEmpty())
        {
            txtuser.setError("Enter Email / Phone");
            txtuser.requestFocus();
        }
        else
        {
            if(txtpass.getText().toString().isEmpty())
            {
                txtpass.setError("Enter Password");
                txtpass.requestFocus();
            }
            else
            {
                Database db=new Database();

                Connection conn=db.ConnectDB();
                if(conn==null)
                {
                    Toast.makeText(LoginActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
                else {
                    ResultSet rs=db.RunSearch("select * from customers where email='"+txtuser.getText()+"' or phone='"+txtuser.getText()+"' and password='"+txtpass.getText()+"'");
                    try {
                        if(rs.next())
                        {

                            if(chk.isChecked())
                            {
                                getSharedPreferences("Fresh",MODE_PRIVATE)
                                        .edit()
                                        .putString("id",rs.getString(1))
                                        .putString("name",rs.getString(2))
                                        .commit();
                            }
                            id=rs.getString(1);
                            name=rs.getString(2);
                            startActivity(new Intent(LoginActivity.this,MainUserActivity.class));

                        }
                        else {
                            AlertDialog.Builder mm=new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Login.....")
                                    .setMessage(" Invaild email login data")
                                    .setIcon(R.drawable.marketlogo)
                                    .setPositiveButton("Try again",null)
                                    .setNegativeButton("Go to Register", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                                        }
                                    });
                            mm.create().show();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }


    }

    public void forgetpassword(View view) {
        LayoutInflater inflater=LayoutInflater.from(LoginActivity.this);
        View vv=inflater.inflate(R.layout.forgetpassword,null);
        AlertDialog.Builder mf=new AlertDialog.Builder(LoginActivity.this);

        EditText txtfemail=vv.findViewById(R.id.txtfemail);

        mf.setView(vv);
        mf.setPositiveButton("Send Security Code", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Database db=new Database();
                db.ConnectDB();
                ResultSet rs=db.RunSearch("select * from customers where email ='"+txtfemail.getText().toString()+"'");
                try {
                    if(rs.next())
                    {

                        id=rs.getString(1);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final String username = "myjob8854@gmail.com";
                                    final String password = "ygmgywopqzxippah";
                                    Properties props = new Properties();
                                    props.put("mail.smtp.auth", "true");
                                    props.put("mail.smtp.starttls.enable", "true");
                                    props.put("mail.smtp.host", "smtp.gmail.com");
                                    props.put("mail.smtp.port", "587");

                                    Session session = Session.getInstance(props,
                                            new javax.mail.Authenticator() {

                                                protected PasswordAuthentication getPasswordAuthentication() {
                                                    return new PasswordAuthentication(username, password);
                                                }
                                            });

                                    try {
                                        Message message = new MimeMessage(session);
                                        message.setFrom(new InternetAddress("myjob8854@gmail.com"));
                                        message.setRecipients(Message.RecipientType.TO,
                                                InternetAddress.parse(txtfemail.getText().toString()));

                                        message.setSubject("Forget password code Fresh App.");
                                        Random r=new Random();
                                          newpass=r.nextInt(99999-11111+1)+11111;
                                        message.setText("Dear : " + rs.getString(2).toString() + "\n" + "Your Security Code  is : " + newpass + "\n" + "Thanks :) ");
                                        Transport.send(message);


                                    } catch (MessagingException e) {
                                        Toast.makeText(getApplication(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                        throw new RuntimeException(e);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }).start();
                        Toast.makeText(LoginActivity.this, "Security Code has been sent", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,SecurityCodeActivity.class));




                    }
                    else
                        Toast.makeText(LoginActivity.this, "This email not exist", Toast.LENGTH_SHORT).show();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }).setNegativeButton("Thanks",null);
        mf.create().show();
    }
}