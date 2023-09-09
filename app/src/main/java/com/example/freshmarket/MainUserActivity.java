package com.example.freshmarket;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.freshmarket.databinding.ActivityMainUserBinding;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class MainUserActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMainUser.toolbar);
//        binding.appBarMainUser.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               startActivity(new Intent(MainUserActivity.this,CartActivity.class));
//            }
//        });
        binding.appBarMainUser.cart12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainUserActivity.this,CartActivity.class));
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_user);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerview=navigationView.getHeaderView(0);
        TextView txtuser=headerview.findViewById(R.id.txtuser);
        txtuser.setText( "Welcome:"+LoginActivity.name);
        TextView txtcount =findViewById(R.id.txtcount);
        Database database=new Database();

        database.ConnectDB();
        ResultSet rs=database.RunSearch("select count(*) as count from carts where cust_id="+LoginActivity.id+" ");
        try {
            if(rs.next()){
                txtcount.setText(String.valueOf(rs.getInt("count")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_user, menu);
        MenuItem item=menu.findItem(R.id.main_search_icon);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_user);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
           finishAffinity();
            return true;
        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
            if(id==R.id.action_logout)
            {
                getSharedPreferences("Fresh",MODE_PRIVATE)
                        .edit().clear().commit();
                startActivity(new Intent(MainUserActivity.this,LoginActivity.class));
            }
            else if(id==R.id.action_delete)
            {
                AlertDialog.Builder ms=new AlertDialog.Builder(MainUserActivity.this)
                        .setTitle("Delete Account :(.....")
                        .setMessage("Are you sure delete your account ?")
                        .setPositiveButton("No",null)
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Database db=new Database();
                                db.ConnectDB();
                                db.RunDML("delete from customers where id='"+LoginActivity.id+"'");
                                getSharedPreferences("Fresh",MODE_PRIVATE)
                                        .edit().clear().commit();
                                startActivity(new Intent(MainUserActivity.this,LoginActivity.class));

                            }
                        });
                ms.create().show();
            }
            else if(id==R.id.action_profile){
                startActivity(new Intent(MainUserActivity.this,ProfileActivity.class));


            }else if(id==R.id.main_search_icon)
            {
                startActivity(new Intent(MainUserActivity.this,SearchActivity.class));
            }else if(id==R.id.main_voice_icon)
            {
//                startActivity(new Intent(MainUserActivity.this,audioSearchActivity.class));
            }else if(id==R.id.main_camera_icon)
            {

            }


        return super.onOptionsItemSelected(item);
    }
}