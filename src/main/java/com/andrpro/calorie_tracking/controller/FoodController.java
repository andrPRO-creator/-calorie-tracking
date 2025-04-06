package com.andrpro.calorie_tracking.controller;

import com.andrpro.calorie_tracking.dto.AckDto;
import com.andrpro.calorie_tracking.requests.FoodRequest;
import com.andrpro.calorie_tracking.entity.NutritionalFood;
import com.andrpro.calorie_tracking.services.FoodService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "foods")
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

    /*
    * получение списка всех продуктов
    */
    @GetMapping("/foods/")
    public List<NutritionalFood> getAllFoods(){return foodService.getAllFoods();}

    /*
     * добавление продукта
     */
    @PostMapping("/foods/")
    public void createFood(@RequestBody FoodRequest foodRequest){
        foodService.createFood(foodRequest);
    }

    @DeleteMapping("/foods/{id}")
    public AckDto deleteFood(@PathVariable Long id){
        return foodService.deleteFood(id);
    }

}
