package com.danlearning.dkuqna;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeTab extends Fragment {
    RecyclerView questionList;   // 리사이클러
    QuestionAdapter adapter;    // 리사이클러 뷰홀더

    EditText searchWord;
    Button searchButton;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_tab, container, false);

        InitUI(rootView);

        return rootView;
    }

    private void InitUI(ViewGroup rootView) {
        questionList = rootView.findViewById(R.id.questionList);
        searchWord = rootView.findViewById(R.id.searchWord);
        searchButton = rootView.findViewById(R.id.searchButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        questionList.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter();

        firestore.collection("questions").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(getActivity(), ViewPostActivity.class);

                intent.putExtra("title", adapter.getItem(position).getTitle());
                intent.putExtra("content", adapter.getItem(position).getContent());
                startActivity(intent);
            }
        }); // 리사이클러뷰에 클릭리스너 추가 (카드뷰로 구현한 자세히 보기 화면 나옴)

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("questions").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
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
            }
        });
    }
}