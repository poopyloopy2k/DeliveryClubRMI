package org.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class Menu implements Serializable {
    private static final List<Dish> menu = new ArrayList<>();
    static
    {
        menu.add(new Dish("Pizza",5.85));
        menu.add(new Dish("Sushi", 7.25));
        menu.add(new Dish("Burger", 2.43));
        menu.add(new Dish("Fries", 1.5));
        menu.add(new Dish("Coca-cola", 0.99));
    }
    public static String printMenuToClient()
    {

        StringBuilder menuStr = new StringBuilder();
        menuStr.append("Menu: \n ");
        for(Dish dish: menu) {
             menuStr.append("- ").append(dish.getName()).append(": $").append(dish.getPrice()).append("\n ");


        }
        return (menuStr.toString());


    }
    public static List<Dish> getMenu() {
        return menu;
    }
}