package com.andrpro.calorie_tracking.dto;

import lombok.Data;

@Data
public class NutritionalFoodDto {
    private Long id;
    private String name;
    private int calories;
    private float protein;
    private float fat;
    private float carbohydrate;
}
