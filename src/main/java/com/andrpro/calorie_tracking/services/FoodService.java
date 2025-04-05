package com.andrpro.calorie_tracking.services;

import com.andrpro.calorie_tracking.dto.AckDto;
import com.andrpro.calorie_tracking.exception.ResourceNotFoundException;
import com.andrpro.calorie_tracking.requests.FoodRequest;
import com.andrpro.calorie_tracking.entity.NutritionalFood;
import com.andrpro.calorie_tracking.repository.NutritionalFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    @Autowired
    NutritionalFoodRepository nutritionalFoodRepository;

    public NutritionalFood getFood(Long id) {
        return nutritionalFoodRepository.findById(id).orElse(null);
    }

    public List<NutritionalFood> getAllFoods() {return nutritionalFoodRepository.findAll();}

    public void createFood(FoodRequest foodRequest) {
        NutritionalFood nutritionalFood = new NutritionalFood();
        nutritionalFood.setName(foodRequest.getName());
        nutritionalFood.setCarbohydrate(foodRequest.getCarbohydrate());
        nutritionalFood.setCalories(foodRequest.getCalories());
        nutritionalFood.setFat(foodRequest.getFat());
        nutritionalFood.setProtein(foodRequest.getProtein());

        nutritionalFoodRepository.save(nutritionalFood);
    }

    public AckDto deleteFood(Long id) {
        nutritionalFoodRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food with id doesn't exist"));

        nutritionalFoodRepository.deleteById(id);

        return AckDto.makeDefault(true);
    }


}
