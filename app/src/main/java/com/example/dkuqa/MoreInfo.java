package com.example.dkuqa;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MoreInfo extends LinearLayout {
    ImageView image;
    TextView title;
    TextView content;
    Button backButton;

    public MoreInfo(Context context) {
        super(context);
        init(context);
    }

    public MoreInfo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.more_info, this, true);

        image = findViewById(R.id.image);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setVisibility(View.INVISIBLE);
                HomeTab.questionList.setVisibility(View.VISIBLE);     // 카드뷰와 리사이클뷰를 번갈아 보이게하고 안보이게 하며 작동
            }
        });
    }

    public void setImage(int resId) {
        image.setImageResource(resId);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setContent(String content) {
        this.content.setText(content);
    }
}