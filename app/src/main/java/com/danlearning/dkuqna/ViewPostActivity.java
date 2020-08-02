package com.danlearning.dkuqna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPostActivity extends AppCompatActivity {

    ImageView image;
    TextView title;
    TextView content;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        image = findViewById(R.id.image);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String Qtitle = intent.getStringExtra("title");
        String Qcontent = intent.getStringExtra("content");

        title.setText(Qtitle);
        content.setText(Qcontent);
        image.setImageResource(R.drawable.applogo);
    }
}
