package com.example.dkuqa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Home extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInsstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home, container, false);

        initUI(rootView);

        return rootView;
    }
    private void initUI(ViewGroup rootView) {
    }
}