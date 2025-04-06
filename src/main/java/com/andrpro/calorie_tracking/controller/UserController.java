package com.andrpro.calorie_tracking.controller;

import com.andrpro.calorie_tracking.dto.AckDto;
import com.andrpro.calorie_tracking.dto.CalorieComplianceDto;
import com.andrpro.calorie_tracking.requests.UserRequest;
import com.andrpro.calorie_tracking.entity.User;
import com.andrpro.calorie_tracking.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "users")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users/{userId}") //получение пользователя по id
    public User getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

    @GetMapping("/users/")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping("/users/") //создание пользователя
    public void createUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
    }

    @PatchMapping("/users/{userId}")
    public User editUser(@PathVariable Long userId,
                         @RequestBody UserRequest user){
        return userService.editUser(userId, user);

    }

    @DeleteMapping("/users/{userId}")
    public AckDto deleteUser(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }

    @GetMapping("/users/{userId}/daily-calorie-norm") //ежедневная норма калорий
    public ResponseEntity<Integer> getDailyCalorieNorm(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.calculateDailyCalories(userId));
    }

    @GetMapping("/users/{userId}/{date}/calories-compliance") //убрался ли пользователь в ежедневную норму калорий
    public ResponseEntity<CalorieComplianceDto> getCaloriesCompliance(@PathVariable Long userId,
                                                                      @PathVariable LocalDate date){
        return ResponseEntity.ok(userService.getCaloriesCompliance(userId, date));
    }

}
