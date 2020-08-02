package com.danlearning.dkuqna;

public class QuestionModel {
    String title;
    String category;

    public QuestionModel(String title, String category) {
        this.title = title;
        this.category = category;
    }   // 질문 생성자

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }
}
