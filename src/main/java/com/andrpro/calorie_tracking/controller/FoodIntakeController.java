package com.andrpro.calorie_tracking.controller;

import com.andrpro.calorie_tracking.dto.FoodIntakeDto;
import com.andrpro.calorie_tracking.requests.FoodIntakeRequest;
import com.andrpro.calorie_tracking.entity.FoodIntake;
import com.andrpro.calorie_tracking.entity.Meal;
import com.andrpro.calorie_tracking.services.FoodIntakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class FoodIntakeController {
    @Autowired
    FoodIntakeService foodIntakeService;

    public FoodIntakeController(FoodIntakeService foodIntakeService){
        this.foodIntakeService = foodIntakeService;
    }

    @GetMapping("/foodintakes/daily/{userId}/{date}") //получение приёмов пищи пользователя за день
    public List<FoodIntakeDto> getFoodIntake(@PathVariable Long userId,
                                             @PathVariable LocalDate date){
        return foodIntakeService.getDailyFoodIntake(userId, date);
    }

    @PostMapping("/foodintakes/{userId}/{meal}") //добавление приёма пищи
    public ResponseEntity<?> createFoodIntakes(
            @PathVariable Long userId,
            @PathVariable Meal meal,
            @RequestBody List<FoodIntakeRequest> foods) {

        foodIntakeService.createFoodIntakes(userId, meal, foods);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/foodintakes/{user_id}/{date}") //получение калорий пользователя за день
    public int getCaloriesPerDay(@PathVariable Long user_id, @PathVariable LocalDate date){
        return foodIntakeService.getCaloriesPerDay(user_id,date);
    }
}
