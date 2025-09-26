package com.mycompany.oop;

import java.util.*;

public class SneakerManager {
    private final List<Sneaker> availableSneakers;

    public SneakerManager() {
        this.availableSneakers = new ArrayList<>();
        initializeSneakers(); // Initialize with sample data
    }

    private void initializeSneakers() {
        availableSneakers.add(new Sneaker("Nike", "Air Max 97", 170));
        availableSneakers.add(new Sneaker("Nike", "Air Jordan 1", 180));
        availableSneakers.add(new Sneaker("Nike", "Air Force 1", 150));
        availableSneakers.add(new Sneaker("Adidas", "Yeezy Boost 350", 220));
        availableSneakers.add(new Sneaker("Adidas", "Superstar", 90));
        availableSneakers.add(new Sneaker("Adidas", "Falcon Galaxy", 180));
        availableSneakers.add(new Sneaker("Converse", "Chuck Taylor All-Star", 60));
        availableSneakers.add(new Sneaker("Converse", "Run Star Hike", 190));
        availableSneakers.add(new Sneaker("Converse", "One Star Pro", 200));
        availableSneakers.add(new Sneaker("Puma", "RS-X", 120));
        availableSneakers.add(new Sneaker("Puma", "Fenty Earth Tone", 160));
        availableSneakers.add(new Sneaker("Puma", "One-Piece Unisex", 150));
    }

    public List<Sneaker> getSneakersByBrand(String brand) {
        List<Sneaker> result = new ArrayList<>();
        for (Sneaker sneaker : availableSneakers) {
            if (sneaker.getBrand().equalsIgnoreCase(brand)) {
                result.add(sneaker);
            }
        }
        return result;
    }
}