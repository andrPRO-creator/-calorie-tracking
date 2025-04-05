package com.andrpro.calorie_tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AckDto {
    private Boolean answer;

    public static AckDto makeDefault(Boolean answer){
        return builder()
                .answer(answer)
                .build();
    }
}
