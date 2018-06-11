package com.example.android.aroma.Model;

import android.graphics.Bitmap;

import com.example.android.aroma.Category;
import com.example.android.aroma.R;
import com.example.android.aroma.Steps;

import org.json.JSONArray;

import java.util.ArrayList;

public class Recipe {


    String title;
    String vegetarian;
    String vegan;
    String dairyFree;
    String glutenFree;
    String sourceUrl;
    String sourceName;
    String description;
    String readyInMinutes;
    String servings;
    ArrayList<String> categories;
    ArrayList<IngredientModel> ingredients;
    ArrayList<Steps> instructions;
    String filePath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(String vegetarian) {
        this.vegetarian = vegetarian;
    }

    public String getVegan() {
        return vegan;
    }

    public void setVegan(String vegan) {
        this.vegan = vegan;
    }

    public String getDairyFree() {
        return dairyFree;
    }

    public void setDairyFree(String dairyFree) {
        this.dairyFree = dairyFree;
    }

    public String getGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(String glutenFree) {
        this.glutenFree = glutenFree;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(String readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<IngredientModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Steps> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<Steps> instructions) {
        this.instructions = instructions;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", vegetarian='" + vegetarian + '\'' +
                ", vegan='" + vegan + '\'' +
                ", dairyFree='" + dairyFree + '\'' +
                ", glutenFree='" + glutenFree + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", description='" + description + '\'' +
                ", readyInMinutes='" + readyInMinutes + '\'' +
                ", servings='" + servings + '\'' +
                ", categories=" + categories +
                ", ingredients=" + ingredients +
                ", instructions=" + instructions +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
