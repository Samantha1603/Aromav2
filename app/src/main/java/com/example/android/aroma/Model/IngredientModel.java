package com.example.android.aroma.Model;

public class IngredientModel{

    private String name;

    private String quantity;

    private String measure;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        return "IngredientModel{" +
                "name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", measure='" + measure + '\'' +
                '}';
    }
}
