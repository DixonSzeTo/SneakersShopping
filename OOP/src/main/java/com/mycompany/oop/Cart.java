package com.mycompany.oop;

import java.util.ArrayList;
import java.util.List;



public class Cart {
    private final List<Sneaker> sneakers;
  
    
    public List<CartItem> getItems() {
        List<CartItem> items = new ArrayList<>();
        items.addAll(sneakers);
        
        return items;
    }
   
    public String getItemsDetails() {
        StringBuilder details = new StringBuilder();
        for (Sneaker item : sneakers) {
            details.append(item.toString()).append("");
        }
       
        return details.toString();
    }

    public Cart() {
        this.sneakers = new ArrayList<>();
       
    }

    public void addSneaker(Sneaker sneaker) {
        sneakers.add(sneaker);
    }

   

    public List<Sneaker> getSneakers() {
        return sneakers;
    }

   

    public double getTotalCost() {
        double total = 0;
        for (Sneaker sneaker : sneakers) {
            total += sneaker.getPrice();
        }
       
        return total;
    }

    public void clear() {
        sneakers.clear();
    
    }

    public void removeSneaker(int index) {
    if (index >= 0 && index < sneakers.size()) {
        Sneaker removedSneaker = sneakers.remove(index);
        System.out.println("Removed: " + removedSneaker.toString());
    } else {
        System.out.println("## Invalid index. No sneaker removed. ##");
    }
}

  

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cart Contents:\n");
        sb.append("Sneakers:\n");
        for (Sneaker sneaker : sneakers) {
            sb.append("  ").append(sneaker.toString()).append("\n");
        }
        
        return sb.toString();
    }
}