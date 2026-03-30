/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author admin
 */
public abstract class StewDecorator implements Decorator{
    protected Decorator wrappedItem;
    
    public StewDecorator(Decorator wrappedItem) {
        this.wrappedItem = wrappedItem;
    }
    
    @Override
    public String getDescription() {
        return wrappedItem.getDescription();
    }
    
    @Override
    public int getPrice() {
        return wrappedItem.getPrice();
    }
}
