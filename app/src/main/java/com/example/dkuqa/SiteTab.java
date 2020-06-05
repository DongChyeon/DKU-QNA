package com.example.dkuqa;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SiteTab extends Fragment {
    RecyclerView siteList;   // 리사이클러
    SiteAdapter adapter;    // 리사이클러 뷰홀더
    DatabaseManager DBManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.site_tab, container, false);

        InitUI(rootView);

        return rootView;
    }

    private void InitUI(ViewGroup rootView) {
        DBManager = DatabaseManager.getInstance(getActivity());

        siteList = rootView.findViewById(R.id.siteList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        siteList.setLayoutManager(layoutManager);
        adapter = new SiteAdapter();

        Cursor cursor = DBManager.rawQuery("SELECT Stitle, Scontent FROM Site", null);
        int recordCount = cursor.getCount();

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            String Stitle = cursor.getString(0);
            String Scontent = cursor.getString(1);
            adapter.addItem(new SiteModel(Stitle, Scontent));   // 리싸이클러뷰에 site 아이템 넣기
        }
        cursor.close();
        siteList.setAdapter(adapter);   // 리싸이클러뷰 적용

        adapter.setOnItemClickListener(new OnSiteItemClickListener() {
            @Override
            public void onItemClick(SiteAdapter.ViewHolder holder, View view, int position) {
                Cursor cursor = DBManager.rawQuery("SELECT Slink FROM Site" , null);
                cursor.moveToPosition(position);
                String Slink = cursor.getString(0);
                Uri uri = Uri.parse(Slink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }); // 리사이클러뷰에 클릭리스너 추가 (사이트로 이동)
    }
}
