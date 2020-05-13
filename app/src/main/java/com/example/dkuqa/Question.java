package com.example.dkuqa;

public class Question {
    String question;
    String category;

    public Question(String question, String category) {
        this.question = question;
        this.category = category;
    }   // 질문 생성자

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }
}
