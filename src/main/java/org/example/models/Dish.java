package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Dish implements Serializable {
    private String name;
    private double price;
}

