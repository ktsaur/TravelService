package ru.kpfu.itis.entities;

import java.util.Date;

public class Article {
    private int article_id;
    private String title;
    private String content;
    private Date created_date;
    private boolean isFavourite;

    public Article(int article_id, String title, String content, Date created_date, boolean isFavourite) {
        this.article_id = article_id;
        this.title = title;
        this.content = content;
        this.created_date = created_date;
        this.isFavourite = false;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
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

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

//    public boolean isFavourite() {
//        return isFavourite;
//    }
//
//    public void setFavourite(boolean favourite) {
//        isFavourite = favourite;
//    }
}
