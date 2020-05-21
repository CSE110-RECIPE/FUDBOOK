package com.example.fudbook.objects;

/**Stores information about a book such as name, author, and recipes contained within the book.*/
public class Book {
    private String bookName;
    private String author;
    private String[] recipeIds;

    public Book(String name, String author){
        bookName = name;
        this.author = author;
    }

    //contructor
    public Book(String name, String author, String[] ids) {
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

    public void setRecipes(String[] ids) {
        recipeIds = ids;
    }

    //getter methods
    public String getName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getRecipes() {
        return recipeIds;
    }
}
