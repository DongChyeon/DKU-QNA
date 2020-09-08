package com.danlearning.dkuqna;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import com.danlearning.dkuqna.model.CategoryModel;
import com.danlearning.dkuqna.model.SiteModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashSet;
import java.util.Iterator;

public class CategoryTab extends Fragment {
    RecyclerView categoryList;   // 리사이클러
    CategoryAdapter adapter;    // 리사이클러 뷰홀더

    FirebaseFirestore firestore;

    HashSet<String> categories; // select distinct 대체

    EditText searchWord;
    Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.category_tab, container, false);

        initUI(rootView);

        return rootView;
    }
    private void initUI(ViewGroup rootView) {
        firestore = FirebaseFirestore.getInstance();
        categories = new HashSet();

        categoryList = rootView.findViewById(R.id.categoryList);
        searchWord = rootView.findViewById(R.id.searchWord);
        searchButton = rootView.findViewById(R.id.serachButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        categoryList.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter();

        firestore.collection("questions").orderBy("category").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }   // 에러가 발생하면 종료

                categories.clear();
                adapter.clearItems();

                for (QueryDocumentSnapshot doc : snapshot) {
                    categories.add(doc.getString("category"));
                }

                for (String category : categories) {
                    adapter.addItem(new CategoryModel(category));
                }
                adapter.notifyDataSetChanged();
                categoryList.setAdapter(adapter);
            }
        }); // 파이어스토어 sites 컬렉션에서 데이터를 불러와 리사이클러뷰에 적용

        adapter.setOnItemClickListener(new OnCategoryItemClickListener() {
            @Override
            public void onItemClick(CategoryAdapter.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(getActivity(), PostListActivity.class);

                intent.putExtra("category", adapter.getItem(position).getCategory());
                startActivity(intent);
            }
        }); // 리사이클러뷰에 클릭리스너 추가 (카드뷰로 구현한 자세히 보기 화면 나옴)

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                firestore.collection("questions").orderBy("category").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }   // 에러가 발생하면 종료

                        categories.clear();
                        adapter.clearItems();

                        for (QueryDocumentSnapshot doc : snapshot) {
                            if (doc.getString("category").contains(searchWord.toString())) {
                                categories.add(doc.getString("category"));
                            }
                        }

                        for (String category : categories) {
                            adapter.addItem(new CategoryModel(category));
                        }
                        adapter.notifyDataSetChanged();
                        categoryList.setAdapter(adapter);
                    }
                }); // 파이어스토어 sites 컬렉션에서 데이터를 불러와 리사이클러뷰에 적용

                adapter.setOnItemClickListener(new OnCategoryItemClickListener() {
                    @Override
                    public void onItemClick(CategoryAdapter.ViewHolder holder, View view, int position) {
                        Intent intent = new Intent(getActivity(), PostListActivity.class);

                        intent.putExtra("category", adapter.getItem(position).getCategory());
                        startActivity(intent);
                    }
                }); // 리사이클러뷰에 클릭리스너 추가
            }
        }); // 카테고리명 기반 검색 기능
    }
}
