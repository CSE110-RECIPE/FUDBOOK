package com.example.fudbook.objects;

/**Stores all recipe information relevant to recipe creation such as title, author, ingredients, instructions, and image.*/
public class Recipe {
    private String title;
    private String author;
    private String[] ingredients;
    private String[] instructions;
    private String[] tags;
    private String image;

    //constructor
    public Recipe(String title, String author, String[] ingr, String[] instr, String[] tags, String image) {
        this.title = title;
        this.author = author;
        ingredients = ingr;
        instructions = instr;
        this.tags = tags;
        this.image = image;
    }

    //setter methods
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIngr(String[] ingr) {
        this.ingredients = ingr;
    }

    public void setInstr(String[] instr) {
        this.instructions = instr;
    }

    public void setImage(String[] tags) {
        this.tags = tags;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //getter methods
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getIngr() {
        return ingredients;
    }

    public String[] getInstr() {
        return instructions;
    }

    public String[] getTags() {
        return tags;
    }

    public String getImage() {
        return image;
    }
}
