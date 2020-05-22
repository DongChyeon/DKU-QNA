package com.example.dkuqa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private static String DB_PATH = "/data/data/com.example.dkuqa/databases/";
    private static String DB_NAME = "dku_QA.db";

    private String myPath = DB_PATH + DB_NAME;
    public static SQLiteDatabase database;

    Home home;
    Category category;
    Search search;  // 프래그먼트들

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            createDatabase();
            database = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (IOException e) {
            println("DB 파일을 생성할 수 없습니다.");
        }

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


    private void println(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

    // 데이터베이스 구현 관련 메소드

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (checkDB != null) { checkDB.close(); }
        if (checkDB != null) return true;
        else return false;
    }   // 해당 경로에 DB파일이 있는지 확인 (정상 작동함)

    private void copyDatabase() throws IOException {
        InputStream myInput = this.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0 , length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }   // DB파일 복사

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if (!dbExist) {
            try {
                openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);  // DB파일 생성 후
                copyDatabase(); // 복사
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }   // DB파일 생성
}
