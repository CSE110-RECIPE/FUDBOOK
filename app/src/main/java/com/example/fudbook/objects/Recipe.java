package com.example.fudbook.objects;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**Stores all recipe information relevant to recipe creation such as title, author, ingredients, instructions, and image.*/
public class Recipe{
    private String title;
    private String author;
    private ArrayList<String> ingredients;
    private ArrayList<String> instructions;
    private String image;
    private ArrayList<String> tags;
    //private String recipeId;   //add this in when we figure out what to put in Id

    // default constructor
    public Recipe(){
        this.title = "";
        this.author = "";
        this.ingredients = null;
        this.instructions = null;
        this.image = "";
        //this.recipeId = "";
    }

    //constructor
    public Recipe(String title, String author,
                  ArrayList<String> ingr,
                  ArrayList<String> instr,
                  String image,
                  ArrayList<String>tags) {
        this.title = title;
        this.author = author;
        this.ingredients = ingr;
        this.instructions = instr;
        this.image = image;
        this.tags = tags;
    }

    //setter methods
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIngr(ArrayList<String> ingr) {
        this.ingredients = ingr;
    }

    public void setInstr(ArrayList<String> instr) {
        if(instr.size() < 2) {
            this.instructions = instr;
        }
        else
        {
            //combine instructions into a single string and store in array
            String str = "";
            for(int i=0; i< instr.size(); i++)
            {
                str += instr.get(i);
                str += "\n";
            }
            this.instructions = new ArrayList<String>();
            instructions.add(str);
        }
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

    public ArrayList<String> getIngr() {
        return ingredients;
    }

    public ArrayList<String> getInstr() {
        return instructions;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<String> getTags() { return tags; }

}
