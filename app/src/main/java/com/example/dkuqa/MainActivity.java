package com.example.dkuqa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    MoreInfo moreInfo;  // 카드뷰
    static RecyclerView questionList;   // 리사이클러
    QuestionAdapter adapter;    // 리사이클러 뷰홀더

    //Home home;    home은 fragment로 추가 예정

    //BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moreInfo = findViewById(R.id.moreInfo);
        questionList = findViewById(R.id.questionList);
        /*home = new Home();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();

                                return true;
                        }
                        return false;
                    }
                });     bottomNavigation 기능 구현*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        questionList.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter();

        adapter.addItem(new Question("기숙사 들어가는 법", "기숙사"));
        adapter.addItem(new Question("학식 먹는 법", "학식"));
        adapter.addItem(new Question("인문관 5층 빨리 가는 법", "생활/편리"));
        adapter.addItem(new Question("학교 편의점 위치", "생활/편리"));
        adapter.addItem(new Question("도서관 대출 기간", "도서관"));
        adapter.addItem(new Question("도서관 열람실 시간", "도서관"));
        adapter.addItem(new Question("도서관 보존서고", "도서관"));

        questionList.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnQuestionItemClickListener() {
            @Override
            public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position) {
                Question item = adapter.getItem(position);
                String titleName = item.getQuestion();
                moreInfo.setVisibility(View.VISIBLE);
                switch (titleName) {
                    case "기숙사 들어가는 법":
                        moreInfo.setImage(R.drawable.applogo);
                        moreInfo.setTitle("기숙사 들어가는 법");
                        moreInfo.setContent("출입구에서 카드를 찍고 들어간다.");
                        questionList.setVisibility(View.INVISIBLE);
                        break;
                    case "학식 먹는 법":
                        moreInfo.setImage(R.drawable.applogo);
                        moreInfo.setTitle("학식 먹는 법");
                        moreInfo.setContent("문을 열고 들어가 키오스크에서 주문");
                        questionList.setVisibility(View.INVISIBLE);
                        break;
                    case "인문관 5층 빨리 가는 법":
                        moreInfo.setImage(R.drawable.applogo);
                        moreInfo.setTitle("인문관 5층 빨리 가는 법");
                        moreInfo.setContent("대학원동 뒤쪽 통로를 이용하면 인문관 5층에 빨리 갈 수 있다.");
                        questionList.setVisibility(View.INVISIBLE);
                        break;
                    case "학교 편의점 위치":
                        moreInfo.setImage(R.drawable.applogo);
                        moreInfo.setTitle("학교 편의점 위치");
                        moreInfo.setContent("도서관 2층 cu, 웅비홀 세븐일레븐, 진리관 cu, 혜당관 cu가 있다.");
                        questionList.setVisibility(View.INVISIBLE);
                        break;
                    case "도서관 대출 기간":
                        moreInfo.setImage(R.drawable.applogo);
                        moreInfo.setTitle("도서관 대출 기간");
                        moreInfo.setContent("대출일로부터 2주 동안 빌릴 수 있고 그 후 매일 100원씩 연체료가 부가된다.(1000원이 최대)");
                        questionList.setVisibility(View.INVISIBLE);
                        break;
                    case "도서관 열람실 시간":
                        moreInfo.setImage(R.drawable.applogo);
                        moreInfo.setTitle("도서관 열람실 시간");
                        moreInfo.setContent("도서관 열람실은 보통 11시까지 열며 시험기간에는 24시간 개방한다.");
                        questionList.setVisibility(View.INVISIBLE);
                        break;
                    case "도서관 보존서고":
                        moreInfo.setImage(R.drawable.applogo);
                        moreInfo.setTitle("도서관 보존서고");
                        moreInfo.setContent("보존서고에 있는 책은 미리 예약을 해두었다가 담당 층 근로장학생한테 꺼내달라고 하면 된다.");
                        questionList.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
    }   // 후에 데이터베이스로 구현할 예정

    /*public void onTabSelected(int position) {
        switch (position) {
            case 0:
                bottomNavigationView.setSelectedItemId(R.id.home);
                break;
        }
    }   bottomNavigation 구현시 필요한 메소드 */
}
