package com.seoultech.stock24.Entity;

public class MyPost {
    private int id;
    private String title;
    private int hit;

    public MyPost() {}

    public MyPost(int id, String title, int hit) {
        this.id = id;
        this.title = title;
        this.hit = hit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }
}
