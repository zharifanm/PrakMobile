package com.example.shoppinglist;

public class ShoppingItem {
    private String name;
    private String category;
    private boolean isPurchased;

    public ShoppingItem(String name, String category, boolean isPurchased) {
        this.name = name;
        this.category = category;
        this.isPurchased = isPurchased;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}