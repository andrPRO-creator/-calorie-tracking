package com.andrpro.calorie_tracking.controller;

import com.andrpro.calorie_tracking.dto.AckDto;
import com.andrpro.calorie_tracking.requests.FoodRequest;
import com.andrpro.calorie_tracking.entity.NutritionalFood;
import com.andrpro.calorie_tracking.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodController {
    @Autowired
    FoodService foodService;

    public FoodController(FoodService foodService){
        this.foodService = foodService;
    }

    @GetMapping("/foods/{id}") //получение продукта по id
    public NutritionalFood getFood(@PathVariable Long id){
        return foodService.getFood(id);
    }

    @GetMapping("/foods") //получение списка всех продуктов
    public List<NutritionalFood> getAllFoods(){return foodService.getAllFoods();}

    @PostMapping("/foods/") //добавление продукта
    public void createFood(@RequestBody FoodRequest foodRequest){
        foodService.createFood(foodRequest);
    }

    @DeleteMapping("/foods/{id}")
    public AckDto deleteFood(@PathVariable Long id){
        return foodService.deleteFood(id);
    }

}
