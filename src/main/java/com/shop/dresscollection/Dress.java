package com.shop.dresscollection;

import java.time.LocalDate;

public class Dress {
    private String name;
    private String type;
    private String size;
    private String color;
    private double price;
    private String details;
    private LocalDate lastPurchaseDate;
    private int quantity;
    private String discountCode;
    private String targetCustomer;
    private boolean boosting;

    public Dress(String name, String type, String size, String color, double price, String details, LocalDate lastPurchaseDate, int quantity, String discountCode, String targetCustomer, boolean boosting) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.color = color;
        this.price = price;
        this.details = details;
        this.lastPurchaseDate = lastPurchaseDate;
        this.quantity = quantity;
        this.discountCode = discountCode;
        this.targetCustomer = targetCustomer;
        this.boosting = boosting;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getSize() { return size; }
    public String getColor() { return color; }
    public double getPrice() { return price; }
    public String getDetails() { return details; }
    public LocalDate getLastPurchaseDate() { return lastPurchaseDate; }
    public int getQuantity() { return quantity; }
    public String getDiscountCode() { return discountCode; }
    public String getTargetCustomer() { return targetCustomer; }
    public boolean isBoosting() { return boosting; }

    @Override
    public String toString() {
        return name + ";" + type + ";" + size + ";" + color + ";" + price + ";" + details + ";" + lastPurchaseDate + ";" + quantity + ";" + discountCode + ";" + targetCustomer + ";" + boosting;
    }
}