package com.example.fudbook.objects;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**Stores all recipe information relevant to recipe creation such as title, author, ingredients, instructions, and image.*/
public class Recipe{
    private String title;
    private String author;
    private String[] ingredients;
    private String[] instructions;
    private String image;
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
    public Recipe(String title, String author, String[] ingr, String[] instr, String image) {
        this.title = title;
        this.author = author;
        this.ingredients = ingr;
        this.instructions = instr;
        this.image = image;
        //this.recipeId = recipeId;
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
        if(instr.length < 2) {
            this.instructions = instr;
        }
        else
        {
            //combine instructions into a single string and store in array
            String str = "";
            for(int i=0; i< instr.length; i++)
            {
                str += instr[i];
                str += "\n";
            }
            this.instructions = new String[]{str};
        }
    }


    public void setImage(String image) {
        this.image = image;
    }
    /*
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }
    */
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

    public String getImage() {
        return image;
    }
    /*
    public String getRecipeId() {
        return recipeId;
    }
     */
}
