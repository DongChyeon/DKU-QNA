package com.danlearning.dkuqna;

import android.view.View;

public interface OnQuestionItemClickListener {
    public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position);
}
