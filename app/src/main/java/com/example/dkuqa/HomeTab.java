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

import java.util.ArrayList;

public class HomeTab extends Fragment {
    MoreInfo moreInfo;  // 카드뷰
    static RecyclerView questionList;   // 리사이클러
    QuestionAdapter adapter;    // 리사이클러 뷰홀더
    QuestionDatabaseManager QuestionDBManager;

    EditText editText;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_tab, container, false);

        InitUI(rootView);

        return rootView;
    }

    private void InitUI(ViewGroup rootView) {
        QuestionDBManager = QuestionDatabaseManager.getInstance(getActivity());

        moreInfo = rootView.findViewById(R.id.moreInfo);
        questionList = rootView.findViewById(R.id.questionList);
        editText = rootView.findViewById(R.id.editText);
        button = rootView.findViewById(R.id.button);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        questionList.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter();

        Cursor cursor = QuestionDBManager.rawQuery("SELECT Qtitle, Qcategory FROM Question", null);
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
                Cursor cursor = QuestionDBManager.rawQuery("SELECT Qtitle, Qcategory FROM Question", null);
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

        button.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Cursor cursor = QuestionDBManager.rawQuery("SELECT Qtitle, Qcategory FROM Question WHERE Qtitle LIKE" + "%" + editText.getText().toString() + "%" , null);
            int recordCount = cursor.getCount();
            adapter.clearItems();   // 먼저 adapter의 아이템들을 비워줘야 함
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
                    Cursor cursor = QuestionDBManager.rawQuery("SELECT Qtitle, Qcategory FROM Question", null);
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
        }); // 제목 기반 검색 기능
    }
}