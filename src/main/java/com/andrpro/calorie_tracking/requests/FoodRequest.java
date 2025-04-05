package com.andrpro.calorie_tracking.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodRequest {
    private String name;
    private int calories;
    private float protein;
    private float fat;
    private float carbohydrate;
}
