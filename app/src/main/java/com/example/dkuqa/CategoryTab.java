package com.example.dkuqa;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryTab extends Fragment {
    RecyclerView categoryList;   // 리사이클러
    CategoryAdapter adapter;    // 리사이클러 뷰홀더
    QuestionDatabaseManager QuestionDBManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.category_tab, container, false);

        initUI(rootView);

        return rootView;
    }
    private void initUI(ViewGroup rootView) {
        QuestionDBManager = QuestionDatabaseManager.getInstance(getActivity());

        categoryList = rootView.findViewById(R.id.categoryList);

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
    }
}
