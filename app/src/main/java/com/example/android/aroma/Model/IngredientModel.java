package com.example.android.aroma.Model;

public class IngredientModel{

    private  String id;

    private String name;

    private String amount;

    private String unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return amount;
    }

    public void setQuantity(String quantity) {
        this.amount = quantity;
    }

    public String getMeasure() {
        return unit;
    }

    public void setMeasure(String measure) {
        this.unit = measure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IngredientModel{" +
                "name='" + name + '\'' +
                ", quantity='" + amount + '\'' +
                ", measure='" + unit + '\'' +
                '}';
    }
}
