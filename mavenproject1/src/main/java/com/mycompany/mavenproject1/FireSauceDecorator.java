/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author admin
 */
public class FireSauceDecorator extends StewDecorator {
    
    private static final int addPrice = 10;
    private static final String addName = "огненный соус";
    
    public FireSauceDecorator(Dish wrappedItem) {
        super(wrappedItem);
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " +" + addName;
    }
    
    @Override
    public int getPrice() {
        return super.getPrice() + addPrice;
    }
}
