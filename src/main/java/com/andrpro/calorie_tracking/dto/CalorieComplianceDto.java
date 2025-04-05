package com.andrpro.calorie_tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalorieComplianceDto {
    private int dailyNorm;
    private int consumed;
    private ComplianceStatus status;
    private int difference;
}
