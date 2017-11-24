package com.example.youpiman.moodtracker.controller;

/**
 * Created by Youpiman on 22/11/2017.
 */

public class Comment {

    private String mComment;
    private int id;

    public Comment(String comment) {
        mComment = comment;
    }

    public Comment() {}

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "mComment='" + mComment + '\'' +
                ", id=" + id +
                '}';
    }
}
