package com.example.freshmarket;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.freshmarket.ui.dashboard.DashboardFragment;
import com.example.freshmarket.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.freshmarket.databinding.ActivityProductsBinding;

public class ProductsActivity extends AppCompatActivity {

    private ActivityProductsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_products);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        DashboardFragment dashboardFragment=new DashboardFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,dashboardFragment);
        transaction.commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_product:
                    DashboardFragment dashboardFragment=new DashboardFragment();
                    FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,dashboardFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_offers:
                    NotificationsFragment dashboardFragment1=new NotificationsFragment();
                    FragmentTransaction transaction1=getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.container,dashboardFragment1);
                    transaction1.commit();
                    return true;

            }
            return false;
        }
    };
}