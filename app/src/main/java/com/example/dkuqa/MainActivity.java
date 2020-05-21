package com.example.dkuqa;

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

    MoreInfo moreInfo;  // 카드뷰
    static RecyclerView questionList;   // 리사이클러
    QuestionAdapter adapter;    // 리사이클러 뷰홀더

    SQLiteDatabase database;
    //Home home;    home은 fragment로 추가 예정

    //BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moreInfo = findViewById(R.id.moreInfo);
        questionList = findViewById(R.id.questionList);
        /*home = new Home();
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
                        }
                        return false;
                    }
                });     bottomNavigation 기능 구현*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        questionList.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter();

        try {
            createDatabase();
        } catch (IOException e) {
            println("DB 파일을 생성할 수 없습니다.");
        }
        database = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);

        Cursor cursor = database.rawQuery("SELECT Qtitle, Qcategory FROM Question", null);
        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            String Qtitle = cursor.getString(0);
            String Qcategory = cursor.getString(1);
            adapter.addItem(new Question(Qtitle, Qcategory));   // 리싸이클러뷰에 question 아이템 넣기
        }
        cursor.close();
        questionList.setAdapter(adapter);   // 리싸이클러뷰 적용

        adapter.setOnItemClickListener(new OnQuestionItemClickListener() {
            @Override
            public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position) {
                Cursor cursor = database.rawQuery("SELECT Qtitle, Qcontent FROM Question", null);
                cursor.moveToPosition(position);
                String Qtitle = cursor.getString(0);
                String Qcontent = cursor.getString(1);
                moreInfo.setImage(R.drawable.applogo);
                moreInfo.setTitle(Qtitle);
                moreInfo.setContent(Qcontent);
                moreInfo.setVisibility(View.VISIBLE);
                questionList.setVisibility(View.INVISIBLE);
            }
        }); // 리사이클러뷰에 클릭리스너 추가 (카드뷰로 구현한 자세히 보기 화면 나옴)
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

    /*public void onTabSelected(int position) {
        switch (position) {
            case 0:
                bottomNavigationView.setSelectedItemId(R.id.home);
                break;
        }
    }   bottomNavigation 구현시 필요한 메소드 */
}
