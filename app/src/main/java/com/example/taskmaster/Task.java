package com.example.taskmaster;

public class Task {

    private  int image;
    private final String title;
    private final String body;
    private final String state ;


    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getState() {
        return state;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
