package com.mycompany.oop;

public class Sneaker implements CartItem {
    private final String brand;
    private final String model;
    private final double price;
    private int quantity; 

    public Sneaker(String brand, String model, double price) {
        this.brand = brand;
        this.model = model;
        this.price = price;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return brand + " " + model + " - RM" + price;
    }
}