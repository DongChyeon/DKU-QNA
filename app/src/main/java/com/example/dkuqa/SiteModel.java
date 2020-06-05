package com.example.dkuqa;

public class SiteModel {
    String title;
    String content;

    public SiteModel(String title, String content) {
        this.title = title;
        this.content = content;
    }   // 사이트 생성자

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
