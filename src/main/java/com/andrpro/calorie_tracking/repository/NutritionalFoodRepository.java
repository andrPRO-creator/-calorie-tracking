package com.andrpro.calorie_tracking.repository;

import com.andrpro.calorie_tracking.entity.NutritionalFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionalFoodRepository extends JpaRepository<NutritionalFood,Long> {
}
