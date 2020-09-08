package com.danlearning.dkuqna.model;

public class SiteModel {
    String title;
    String content;
    String link;

    public SiteModel(String title, String content, String link) {
        this.title = title;
        this.content = content;
        this.link = link;
    }   // 사이트 생성자

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

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
