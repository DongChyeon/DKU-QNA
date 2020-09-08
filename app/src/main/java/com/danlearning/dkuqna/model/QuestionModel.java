package com.danlearning.dkuqna.model;

public class QuestionModel {
    String title;
    String category;
    String content;

    public QuestionModel(String title, String category, String content) {
        this.title = title;
        this.category = category;
        this.content = content;
    }   // 질문 생성자

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
