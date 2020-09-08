package com.danlearning.dkuqna;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danlearning.dkuqna.model.QuestionModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PostListActivity extends AppCompatActivity {

    RecyclerView questionList;   // 리사이클러
    QuestionAdapter adapter;    // 리사이클러 뷰홀더
    FirebaseFirestore firestore;

    EditText searchWord;
    Button searchButton;

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        Intent intent = getIntent();
        category = intent.getStringExtra("category");   // 인텐트에서 category 값을 받아옴

        firestore = FirebaseFirestore.getInstance();

        questionList = findViewById(R.id.questionList);
        searchWord = findViewById(R.id.searchWord);
        searchButton = findViewById(R.id.searchButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        questionList.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter();

        firestore.collection("questions").whereEqualTo("category", category).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }   // 에러가 발생하면 종료

                adapter.clearItems();

                for (QueryDocumentSnapshot doc : snapshot) {
                    adapter.addItem(new QuestionModel(doc.getString("title"), doc.getString("category"), doc.getString("content")));
                }
                adapter.notifyDataSetChanged();
                questionList.setAdapter(adapter);
            }
        }); // 파이어스토어 questions 컬렉션에서 데이터를 불러와 리사이클러뷰에 적용

        adapter.setOnItemClickListener(new OnQuestionItemClickListener() {
            @Override
            public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ViewPostActivity.class);

                intent.putExtra("title", adapter.getItem(position).getTitle());
                intent.putExtra("content", adapter.getItem(position).getContent());
                startActivity(intent);
            }
        }); // 리사이클러뷰에 클릭리스너 추가 (카드뷰로 구현한 자세히 보기 화면 나옴)

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                firestore.collection("questions").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }   // 에러가 발생하면 종료

                        adapter.clearItems();

                        for (QueryDocumentSnapshot doc : snapshot) {
                            if (doc.getString("title").contains(searchWord.getText().toString())) { // 타이틀에 정해진 키워드가 있는지 판단
                                adapter.addItem(new QuestionModel(doc.getString("title"), doc.getString("category"), doc.getString("content")));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        questionList.setAdapter(adapter);
                    }
                }); // 파이어스토어 questions 컬렉션에서 데이터를 불러와 리사이클러뷰에 적용

                adapter.setOnItemClickListener(new OnQuestionItemClickListener() {
                    @Override
                    public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), ViewPostActivity.class);

                        intent.putExtra("title", adapter.getItem(position).getTitle());
                        intent.putExtra("content", adapter.getItem(position).getContent());
                        startActivity(intent);
                    }
                }); // 리사이클러뷰에 클릭리스너 추가 (카드뷰로 구현한 자세히 보기 화면 나옴)
            }
        });
    }
}
