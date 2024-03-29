package com.thoughtworks.jieshuquan.service.model;

/**
 * Created by leihuang on 7/17/15.
 */
public class BookComment {
    private String bookId;
    private String userName;
    private String userIconUrl;
    private String commentTime;
    private String commentContent;

    public BookComment(String bookId, String userName, String userIconUrl, String commentTime, String commentContent) {
        this.bookId = bookId;
        this.userName = userName;
        this.userIconUrl = userIconUrl;
        this.commentTime = commentTime;
        this.commentContent = commentContent;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
