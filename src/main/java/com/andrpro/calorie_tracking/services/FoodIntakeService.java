package com.andrpro.calorie_tracking.services;

import com.andrpro.calorie_tracking.dto.FoodIntakeDto;
import com.andrpro.calorie_tracking.dto.FoodItemDto;
import com.andrpro.calorie_tracking.dto.NutritionalFoodDto;
import com.andrpro.calorie_tracking.entity.*;
import com.andrpro.calorie_tracking.exception.BadRequestException;
import com.andrpro.calorie_tracking.exception.ResourceNotFoundException;
import com.andrpro.calorie_tracking.repository.NutritionalFoodRepository;
import com.andrpro.calorie_tracking.repository.UserRepository;
import com.andrpro.calorie_tracking.repository.FoodIntakeRepository;
import com.andrpro.calorie_tracking.requests.FoodIntakeRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodIntakeService {
    @Autowired
    FoodIntakeRepository foodIntakeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NutritionalFoodRepository nutritionalFoodRepository;

    @Transactional
    public List<FoodIntakeDto> getDailyFoodIntake(Long userId, LocalDate date) {
        return foodIntakeRepository.findByUsersIdAndDateWithItems(userId, date)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new BadRequestException("No meals found for date: " + date))
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FoodIntakeDto convertToDto(FoodIntake intake) {
        FoodIntakeDto dto = new FoodIntakeDto();
        dto.setId(intake.getId());
        dto.setMeal(intake.getMeal());
        dto.setDate(intake.getDate());
        dto.setItems(intake.getItems().stream()
                .map(this::convertToFoodItemDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private FoodItemDto convertToFoodItemDto(FoodIntakeItem item) {
        FoodItemDto dto = new FoodItemDto();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());

        NutritionalFood food = item.getFood();
        NutritionalFoodDto foodDto = new NutritionalFoodDto();
        foodDto.setId(food.getId());
        foodDto.setName(food.getName());
        foodDto.setCalories(food.getCalories());
        foodDto.setProtein(food.getProtein());
        foodDto.setFat(food.getFat());
        foodDto.setCarbohydrate(food.getCarbohydrate());

        dto.setFood(foodDto);
        return dto;
    }



    public int getCaloriesPerDay(Long userId, LocalDate date) {
        return foodIntakeRepository
                .findTotalCaloriesByUserAndDate(userId, date)
                .orElseThrow(() -> new BadRequestException(
                        String.format("No calories found for the current query")));
    }

    @Transactional
    public void createFoodIntakes(Long userId, Meal meal, List<FoodIntakeRequest> foods) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        FoodIntake foodIntake = new FoodIntake();
        foodIntake.setUsers(user);
        foodIntake.setDate(LocalDate.now());
        foodIntake.setMeal(meal);

        List<FoodIntakeItem> items = new ArrayList<>();
        for (FoodIntakeRequest foodRequest : foods) {
            NutritionalFood food = nutritionalFoodRepository.findById(foodRequest.getNutritionalFoodId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Food not found with id: " + foodRequest.getNutritionalFoodId()));

            FoodIntakeItem item = new FoodIntakeItem();
            item.setFood(food);
            item.setQuantity(foodRequest.getGram());
            item.setFoodIntake(foodIntake);
            items.add(item);
        }

        foodIntake.setItems(items);
        foodIntakeRepository.save(foodIntake);
    }

    @Transactional
    public void deleteFoodIntakes(Long userId, LocalDate date, Meal meal) {
        FoodIntake intake = foodIntakeRepository.findByUserIdDateAndMeal(userId, date, meal)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Food intake not found for user %d on %s (%s)",
                                userId, date, meal)));
        foodIntakeRepository.delete(intake);
    }
}
