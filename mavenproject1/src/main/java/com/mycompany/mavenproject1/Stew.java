/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author admin
 */
public class Stew implements Decorator{
    private static final int defaultPrice = 50;
    private static final String defaultDescription = "Нордское рагу";
    
    @Override
    public String getDescription() {
        return defaultDescription;
    }
    
    @Override
    public int getPrice() {
        return defaultPrice;
    }
}
