package com.example.dkuqa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class SiteTab extends Fragment {
    RecyclerView siteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.site_tab, container, false);

        InitUI(rootView);

        return rootView;
    }

    private void InitUI(ViewGroup rootView) {
        siteList = rootView.findViewById(R.id.siteList);
    }

}
