package com.example.dmobapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                new SallesFragment()).commit();
        // nav

        BottomNavigationView btnNav = findViewById(R.id.bottomNav);
        btnNav.setOnNavigationItemSelectedListener(navListener);

    }

//    Listener nav

    private  BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()){

                        case R.id.salleNav:
                            selectedFragment = new SallesFragment();
                            break;
                        case R.id.mapsNav:
                            selectedFragment = new MapsFragment();
                            break;
                        case R.id.adminBtn:
                            selectedFragment = new LogInFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
                            selectedFragment).commit();
                    return true;
                }
            };


}