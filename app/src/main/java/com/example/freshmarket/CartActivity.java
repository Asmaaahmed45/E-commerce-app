package com.example.freshmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CartActivity extends AppCompatActivity {
    ListView listView;
    Getcart getcart=new Getcart();
    Adaptercart adaptercart;
    CartModel cartModel;
    ArrayList<CartModel> list;
    TextView txttotal;
Button comfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        listView=findViewById(R.id.lstcart);
        list=new ArrayList<>(getcart.GetData());
        adaptercart=new Adaptercart(CartActivity.this,list);
        listView.setAdapter(adaptercart);
        txttotal=findViewById(R.id.txttotal);
        comfirm=(Button)findViewById(R.id.btn_con);
        Database db=new Database();
        db.ConnectDB();
//        ResultSet rs=db.RunSearch("SELECT SUM(price*quantity) as total FROM carts WHERE cust_id="+LoginActivity.id+" ");
//        try {
//            if(rs.next()){
//                txttotal.setText("Total :" +String.valueOf(rs.getInt("total")));
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rating=new Intent(CartActivity.this,FeedbackActivity.class);
                startActivity(rating);
            }
        });
    }

    public void send(View view) {
        Database db=new Database();
        db.ConnectDB();

        ResultSet rs=db.RunSearch("select * from customers where id="+LoginActivity.id+" ");
        try {
            if(rs.next())
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String username = "amra07775@gmail.com";
                            final String password = "Engamr26?";
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
                            String email=rs.getString(5);

                            try {
                                Message message = new MimeMessage(session);
                                message.setFrom(new InternetAddress("amra07775@gmail.com"));
                                message.setRecipients(Message.RecipientType.TO,
                                        InternetAddress.parse(rs.getString(5)));

                                message.setSubject("Order confirmation Fresh App.");

                                message.setText("Dear : "+LoginActivity.name+" \n"+ "Your order  has been  Submitted : \n" + "Thanks :) ");
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
                Toast.makeText(CartActivity.this, "Confirmed", Toast.LENGTH_SHORT).show();

            }
            else
                Toast.makeText(CartActivity.this, "This email not exist", Toast.LENGTH_SHORT).show();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }
}