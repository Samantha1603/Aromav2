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

    public static HashMap<String,Integer> createCategoryHashMapList()
    {
        HashMap<String,Integer> categoryMap=new HashMap<>();
        categoryMap.put("vegetarian",1);
        categoryMap.put("vegan",2);
        categoryMap.put("gluten free",3);
        categoryMap.put("dairy free",4);
        categoryMap.put("main course",5);
        categoryMap.put("side dish",6);
        categoryMap.put("dessert",7);
        categoryMap.put("appetizer",8);
        categoryMap.put("salad",9);
        categoryMap.put("bread",10);
        categoryMap.put("breakfast",11);
        categoryMap.put("soup",12);
        categoryMap.put("beverage",13);
        categoryMap.put("sauce",14);
        categoryMap.put("drink",15);
        categoryMap.put("african",16);
        categoryMap.put("chinese",17);
        categoryMap.put("japanese",18);
        categoryMap.put("korean",19);
        categoryMap.put("thai",20);
        categoryMap.put("indian",21);
        categoryMap.put("vietnamese",22);
        categoryMap.put("british",23);
        categoryMap.put("irish",24);
        categoryMap.put("french",25);
        categoryMap.put("italian",26);
        categoryMap.put("mexican",27);
        categoryMap.put("spanish",28);
        categoryMap.put("middle eastern",29);
        categoryMap.put("jewish",30);
        categoryMap.put("american",31);
        categoryMap.put("cajun",32);
        categoryMap.put("southern",33);
        categoryMap.put("greek",34);
        categoryMap.put("german",35);
        categoryMap.put("nordic",36);
        categoryMap.put("eastern european",37);
        categoryMap.put("caribbean",38);
        categoryMap.put("latin american",39);

        return categoryMap;
    }
}
