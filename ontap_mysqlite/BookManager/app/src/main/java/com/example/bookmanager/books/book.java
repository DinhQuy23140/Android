package com.example.bookmanager.books;

public class book extends author {

    private String bookID, bookName, bookTag, bookPrice;

    public book(String bookID, String bookName, String bookTag, String bookPrice) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookTag = bookTag;
        this.bookPrice = bookPrice;
    }

    public book(String authorId, String authorName, String bookID, String bookName, String bookTag, String bookPrice) {
        super(authorId, authorName);
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookTag = bookTag;
        this.bookPrice = bookPrice;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookTag() {
        return bookTag;
    }

    public void setBookTag(String bookTag) {
        this.bookTag = bookTag;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }
}
