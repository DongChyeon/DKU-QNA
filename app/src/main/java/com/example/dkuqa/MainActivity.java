package com.example.dkuqa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Home home;
    Category category;
    Search search;  // 프래그먼트들

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = new Home();
        category = new Category();
        search = new Search();

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
                            case R.id.search:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, search).commit();
                                return true;
                        }
                        return false;
                    }
                });     // home, category와 search 기능 추가 예정
    }
}
