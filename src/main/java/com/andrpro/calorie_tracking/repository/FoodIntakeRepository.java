package com.andrpro.calorie_tracking.repository;

import com.andrpro.calorie_tracking.entity.FoodIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FoodIntakeRepository extends JpaRepository<FoodIntake,Long> {
    @Query("""
        SELECT SUM(fi.food.calories * fi.quantity / 100) 
        FROM FoodIntakeItem fi
        JOIN fi.foodIntake intake
        WHERE intake.users.id = :userId 
        AND intake.date = :date
        """)
    Integer findTotalCaloriesByUserAndDate(@Param("userId") Long userId,
                                           @Param("date") LocalDate date);


    @Query("SELECT fi FROM FoodIntake fi " +
            "JOIN FETCH fi.items " +
            "JOIN FETCH fi.items.food " +
            "WHERE fi.users.id = :userId AND fi.date = :date")
    List<FoodIntake> findByUsersIdAndDateWithItems(@Param("userId") Long userId,
                                                   @Param("date") LocalDate date);


}
