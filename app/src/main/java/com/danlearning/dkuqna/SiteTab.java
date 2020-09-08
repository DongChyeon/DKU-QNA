package com.danlearning.dkuqna;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.danlearning.dkuqna.model.SiteModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SiteTab extends Fragment {
    RecyclerView siteList;   // 리사이클러
    SiteAdapter adapter;    // 리사이클러 뷰홀더

    FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.site_tab, container, false);

        InitUI(rootView);

        return rootView;
    }

    private void InitUI(ViewGroup rootView) {
        firestore = FirebaseFirestore.getInstance();

        siteList = rootView.findViewById(R.id.siteList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        siteList.setLayoutManager(layoutManager);
        adapter = new SiteAdapter();

        firestore.collection("sites").orderBy("title").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }   // 에러가 발생하면 종료

                adapter.clearItems();

                for (QueryDocumentSnapshot doc : snapshot) {
                    adapter.addItem(new SiteModel(doc.getString("title"), doc.getString("content"), doc.getString("link")));
                }
                adapter.notifyDataSetChanged();
                siteList.setAdapter(adapter);
            }
        }); // 파이어스토어 sites 컬렉션에서 데이터를 불러와 리사이클러뷰에 적용

        adapter.setOnItemClickListener(new OnSiteItemClickListener() {
            @Override
            public void onItemClick(SiteAdapter.ViewHolder holder, View view, int position) {
                String siteLink = adapter.getItem(position).getLink();
                Uri uri = Uri.parse(siteLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }); // 리사이클러뷰에 클릭리스너 추가 (사이트로 이동)
    }
}
