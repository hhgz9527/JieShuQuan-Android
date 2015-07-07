package com.thoughtworks.jieshuquan_android.model;

public class BookItem {

    private String bookId;
    private String bookName;
    private String bookImageHref;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookImageHref() {
        return bookImageHref;
    }

    public void setBookImageHref(String bookImageHref) {
        this.bookImageHref = bookImageHref;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
