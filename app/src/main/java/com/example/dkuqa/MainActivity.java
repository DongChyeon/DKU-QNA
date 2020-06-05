package com.example.dkuqa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    HomeTab home;
    CategoryTab category;
    SiteTab sites;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = new HomeTab();
        category = new CategoryTab();
        sites = new SiteTab();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();
                                return true;
                            case R.id.category:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, category).commit();
                                return true;
                            case R.id.sites:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, sites).commit();
                                return true;
                        }
                        return false;
                    }
                });     // 프래그먼트 전환
    }
}
