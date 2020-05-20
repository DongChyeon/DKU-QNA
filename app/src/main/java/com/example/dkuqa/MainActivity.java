package com.example.dkuqa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
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
        database = openOrCreateDatabase("dku_QA.db", MODE_PRIVATE, null);
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
        });
    }   // 리사이클러뷰를 클릭리스너 (카드뷰로 구현한 자세히 보기 화면 나옴)

    /*public void onTabSelected(int position) {
        switch (position) {
            case 0:
                bottomNavigationView.setSelectedItemId(R.id.home);
                break;
        }
    }   bottomNavigation 구현시 필요한 메소드 */
}
