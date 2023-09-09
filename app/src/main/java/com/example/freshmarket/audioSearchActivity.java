package com.example.freshmarket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class audioSearchActivity extends AppCompatActivity {
    private TextView textView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiuo_search);
        textView = (TextView) findViewById(R.id.text2);
        listView = (ListView) findViewById(R.id.list2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }
}

//    public void startprocess() {
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Give Your Command");
//        try
//        {
//            startActivityForResult(intent,1000);
//        }catch (Exception ex)
//        {
//            Toast.makeText(getApplicationContext(),"Error in Code", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1000 && resultCode == RESULT_OK) {
//            ArrayList<String> commands = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            String raw = commands.get(0).toString(); // insert imran (0)
//            String[] arrSplit = raw.split(" "); // insert[0]  imran[1] .... abc[2]...a dil
//            if (arrSplit[0].equals("fetch")) {
//                tv.setText("");
//                DatabaseReference dr = FirebaseDatabase.getInstance().getReference("audiorecords");
//                dr.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot ds : snapshot.getChildren()) {
//
//                            tv.append(ds.getValue(String.class) + "\n");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//
//                });
//            }
//        }
//    }
//}