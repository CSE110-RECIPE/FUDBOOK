package com.example.fudbook.objects;

/**Stores information about the user such as their username, personal bookshelf, and default food preferences.*/
public class User {
    private String name;
    private String[] bookshelf;
    private String favId;
    private String myBookId;
    private String[] include;
    private String[] exclude;

    //constructor
    public User(String name, String[] bookshelf, String favId, String myBookId, String[] include, String[] exclude) {
        this.name = name;
        this.bookshelf = bookshelf;
        this.favId = favId;
        this.myBookId = myBookId;
        this.include = include;
        this.exclude = exclude;
    }

    //setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setBookshelf(String[] bookshelf) {
        this.bookshelf = bookshelf;
    }

    public void setFavorite(String favId) {
        this.favId = favId;
    }

    public void setMyBook(String myBookId) {
        this.myBookId = myBookId;
    }

    public void setIncludePref(String[] include) {
        this.include = include;
    }

    public void setExcludePref(String[] exclude) {
        this.exclude = exclude;
    }

    //getter methods
    public String getName() {
        return name;
    }

    public String[] getBookshelf() {
        return bookshelf;
    }

    public String getFavorite() {
        return favId;
    }

    public String getMyBook() {
        return myBookId;
    }

    public String[] getIncludePref() {
        return include;
    }

    public String[] getExcludePref() {
        return exclude;
    }
}
