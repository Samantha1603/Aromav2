package com.example.android.aroma;

public class Food  {

    private String Name,Image,MenuID, Servings, Time,Description,Directions;

    public Food() {
    }

    public Food(String name, String image, String menuID, String servings, String time, String description, String directions) {
        Name = name;
        Image = image;
        MenuID = menuID;
        Servings = servings;
        Time = time;
        Description = description;
        Directions = directions;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getServings() {
        return Servings;
    }

    public void setServings(String servings) {
        Servings = servings;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDirections() {
        return Directions;
    }

    public void setDirections(String directions) {
        Directions = directions;
    }
}
