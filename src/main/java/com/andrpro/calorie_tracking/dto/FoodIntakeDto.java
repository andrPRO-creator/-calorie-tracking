package com.andrpro.calorie_tracking.dto;

import com.andrpro.calorie_tracking.entity.Meal;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FoodIntakeDto {
    private Long id;
    private Meal meal;
    private LocalDate date;
    private List<FoodItemDto> items;
}
