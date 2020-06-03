package com.example.dkuqa;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryTab extends Fragment {
    RecyclerView categoryList;   // 리사이클러
    CategoryAdapter adapter;    // 리사이클러 뷰홀더
    QuestionDatabaseManager QuestionDBManager;

    EditText searchWord;
    Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.category_tab, container, false);

        initUI(rootView);

        return rootView;
    }
    private void initUI(ViewGroup rootView) {
        QuestionDBManager = QuestionDatabaseManager.getInstance(getActivity());

        categoryList = rootView.findViewById(R.id.categoryList);
        searchWord = rootView.findViewById(R.id.searchWord);
        searchButton = rootView.findViewById(R.id.serachButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        categoryList.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter();

        Cursor cursor = QuestionDBManager.rawQuery("SELECT DISTINCT Qcategory FROM Question", null);
        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            String Qcategory = cursor.getString(0);
            adapter.addItem(new CategoryModel(Qcategory));   // 리싸이클러뷰에 question 아이템 넣기
        }
        cursor.close();
        categoryList.setAdapter(adapter);   // 리싸이클러뷰 적용

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cursor cursor = QuestionDBManager.rawQuery("SELECT DISTINCT Qcategory FROM Question WHERE Qcategory LIKE " + "'%" + searchWord.getText().toString() + "%'" , null);
                int recordCount = cursor.getCount();
                adapter.clearItems();   // 먼저 adapter의 아이템들을 비워줘야 함
                for (int i = 0; i < recordCount; i++) {
                    cursor.moveToNext();
                    String Qcategory = cursor.getString(0);
                    adapter.addItem(new CategoryModel(Qcategory));   // 리싸이클러뷰에 question 아이템 넣기
                }
                cursor.close();
                categoryList.setAdapter(adapter);   // 리싸이클러뷰 적용
            }
        }); // 카테고리명 기반 검색 기능
    }
}
