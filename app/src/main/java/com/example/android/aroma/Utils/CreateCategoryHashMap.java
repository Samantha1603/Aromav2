package com.example.android.aroma.Utils;

import java.util.HashMap;

public class CreateCategoryHashMap
{
    public static String[] createCategoryList()
    {
            String[] categoryList={"vegetarian","vegan","gluten free","dairy free","main course",
                    "side dish","dessert","appetizer","salad","bread","breakfast","soup","beverage",
                    "sauce","drink","african","chinese","japanese","korean","thai","indian","vietnamese",
                    "british","irish","french","italian","mexican","spanish","middle eastern","jewish",
                    "american","cajun","southern","greek","german","nordic","eastern european","caribbean",
                    "latin american"};

            return categoryList;
    }

    public static HashMap<Integer,String> createCategoryHashMapList()
    {
        HashMap<Integer,String> categoryMap=new HashMap<>();
        categoryMap.put(1,"vegetarian");
        categoryMap.put(2,"vegan");
        categoryMap.put(3,"gluten free");
        categoryMap.put(4,"dairy free");
        categoryMap.put(5,"main course");
        categoryMap.put(6,"side dish");
        categoryMap.put(7,"dessert");
        categoryMap.put(8,"appetizer");
        categoryMap.put(9,"salad");
        categoryMap.put(10,"bread");
        categoryMap.put(11,"breakfast");
        categoryMap.put(12,"soup");
        categoryMap.put(13,"beverage");
        categoryMap.put(14,"sauce");
        categoryMap.put(15,"drink");
        categoryMap.put(16,"african");
        categoryMap.put(17,"chinese");
        categoryMap.put(18,"japanese");
        categoryMap.put(19,"korean");
        categoryMap.put(20,"thai");
        categoryMap.put(21,"indian");
        categoryMap.put(22,"vietnamese");
        categoryMap.put(23,"british");
        categoryMap.put(24,"irish");
        categoryMap.put(25,"french");
        categoryMap.put(26,"italian");
        categoryMap.put(27,"mexican");
        categoryMap.put(28,"spanish");
        categoryMap.put(29,"middle eastern");
        categoryMap.put(30,"jewish");
        categoryMap.put(31,"american");
        categoryMap.put(32,"cajun");
        categoryMap.put(33,"southern");
        categoryMap.put(34,"greek");
        categoryMap.put(35,"german");
        categoryMap.put(36,"nordic");
        categoryMap.put(37,"eastern european");
        categoryMap.put(38,"caribbean");
        categoryMap.put(39,"latin american");

        return categoryMap;
    }
}
