package com.example.android.aroma;

public class Category {
    private String Name;
    private String Image;
    private String Id;

    public Category(){

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Category(String name, String image, String id) {
        Name = name;
        Image = image;
        Id=id;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImage(String image) {
        Image = image;
    }


}


