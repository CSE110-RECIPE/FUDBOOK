package com.example.fudbook.objects;

import java.util.ArrayList;

/**Stores information about a book such as name, author, and recipes contained within the book.*/
public class Book {
    private String bookName;
    private String author;
    private boolean def;
    private ArrayList<String> recipeIds;

    public Book(String name, String author){
        bookName = name;
        this.author = author;
    }

    //contructor
    public Book(String name, String author, boolean def, ArrayList<String> ids) {
        bookName = name;
        this.author = author;
        recipeIds = ids;
    }

    //setter methods
    public void setName(String name) {
        bookName = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDefault(boolean def) {this.def = def; }

    public void setRecipes(ArrayList<String> ids) {
        recipeIds = ids;
    }

    //getter methods
    public String getName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public boolean getDefault() { return def; }

    public ArrayList<String> getRecipes() {
        return recipeIds;
    }
}
