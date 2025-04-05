package com.andrpro.calorie_tracking.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodIntakeRequest {
    private Long nutritionalFoodId;
    private int gram;
}
