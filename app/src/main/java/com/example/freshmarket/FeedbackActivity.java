package com.example.freshmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;

public class FeedbackActivity extends AppCompatActivity {

    TextView tvFeedback;
    RatingBar rbStars;
    EditText feedback1;
    Button btnSend;
    float rating1;
    CartModel cartModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        tvFeedback = findViewById(R.id.tvFeedback);
        rbStars = findViewById(R.id.rbStars);
        feedback1= findViewById(R.id.feedback);
        btnSend=findViewById(R.id.btnSend);
        cartModel=new CartModel();
        rbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating1=rating;
                if(rating==0)
                {
                    tvFeedback.setText("Very Dissatisfied");
                }
                else if(rating==1)
                {
                    tvFeedback.setText("Dissatisfied");
                }
                else if(rating==2 || rating==3)
                {
                    tvFeedback.setText("OK");
                }
                else if(rating==4)
                {
                    tvFeedback.setText("Satisfied");
                }
                else if(rating==5)
                {
                    tvFeedback.setText("Very Satisfied");
                }
                else
                {
                    tvFeedback.setText("Very Satisfied");
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db=new Database();
                Connection conn=db.ConnectDB();
                if(conn==null)
                {
                    Toast.makeText(FeedbackActivity.this,"No Internet connection",Toast.LENGTH_SHORT).show();
                }
                else {
                    String msg = db.RunDML("insert into  Feedback2 (comment,order_id,rate) values ('" + feedback1.getText() + "','" + cartModel.orderno+ "','" + rating1 +"')");
                    if (msg.equals("Ok")) {
                        Toast.makeText(FeedbackActivity.this,"Thnak you for feedback",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(FeedbackActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}