package com.example.dkuqa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PostListActivity extends AppCompatActivity {

    RecyclerView questionList;   // 리사이클러
    QuestionAdapter adapter;    // 리사이클러 뷰홀더
    QuestionDatabaseManager QuestionDBManager;

    EditText searchWord;
    Button searchButton;

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        QuestionDBManager = QuestionDatabaseManager.getInstance(this);

        questionList = findViewById(R.id.questionList);
        searchWord = findViewById(R.id.searchWord);
        searchButton = findViewById(R.id.searchButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        questionList.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter();

        Cursor cursor = QuestionDBManager.rawQuery("SELECT Qtitle, Qcategory FROM Question WHERE Qcategory = " + "'"  + category + "'", null);
        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            String Qtitle = cursor.getString(0);
            String Qcategory = cursor.getString(1);
            adapter.addItem(new QuestionModel(Qtitle, Qcategory));   // 리싸이클러뷰에 question 아이템 넣기
        }
        cursor.close();
        questionList.setAdapter(adapter);   // 리싸이클러뷰 적용

        adapter.setOnItemClickListener(new OnQuestionItemClickListener() {
            @Override
            public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ViewPostActivity.class);
                Cursor cursor = QuestionDBManager.rawQuery("SELECT Qtitle, Qcontent FROM Question WHERE Qcategory = " + "'"  + category + "'", null);
                cursor.moveToPosition(position);
                String Qtitle = cursor.getString(0);
                String Qcontent = cursor.getString(1);
                intent.putExtra("title", Qtitle);
                intent.putExtra("content", Qcontent);
                startActivity(intent);
            }
        }); // 리사이클러뷰에 클릭리스너 추가 (카드뷰로 구현한 자세히 보기 화면 나옴)

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cursor cursor = QuestionDBManager.rawQuery("SELECT Qtitle, Qcategory FROM Question WHERE Qtitle LIKE " + "'%" + searchWord.getText().toString() + "%'"
                        + " AND Qcategory = " + "'" + category + "'" , null);
                int recordCount = cursor.getCount();
                adapter.clearItems();   // 먼저 adapter의 아이템들을 비워줘야 함
                for (int i = 0; i < recordCount; i++) {
                    cursor.moveToNext();
                    String Qtitle = cursor.getString(0);
                    String Qcategory = cursor.getString(1);
                    adapter.addItem(new QuestionModel(Qtitle, Qcategory));   // 리싸이클러뷰에 question 아이템 넣기
                }
                cursor.close();
                questionList.setAdapter(adapter);   // 제목 기반 검색 기능

                adapter.setOnItemClickListener(new OnQuestionItemClickListener() {
                    @Override
                    public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), ViewPostActivity.class);
                        Cursor cursor = QuestionDBManager.rawQuery("SELECT Qtitle, Qcontent FROM Question WHERE Qtitle LIKE " + "'%" + searchWord.getText().toString() + "%'"
                                + " AND Qcategory = " + "'" + category + "'" , null);
                        cursor.moveToPosition(position);
                        String Qtitle = cursor.getString(0);
                        String Qcontent = cursor.getString(1);
                        intent.putExtra("title", Qtitle);
                        intent.putExtra("content", Qcontent);
                        startActivity(intent);
                    }
                }); // 리사이클러뷰에 클릭리스너 추가 (카드뷰로 구현한 자세히 보기 화면 나옴)
            }
        });
    }
}
