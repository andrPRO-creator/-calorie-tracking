package com.andrpro.calorie_tracking.dto;

import lombok.Data;

@Data
public class FoodItemDto {
    private Long id;
    private float quantity;
    private NutritionalFoodDto food;
}
