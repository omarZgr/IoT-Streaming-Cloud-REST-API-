package com.example.application2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataDto {

    private float value;
    private String time;

    @Override
    public String toString() {
        return "TemperatureReading{" +
                "value=" + value +
                ", time='" + time + '\'' +
                '}';
    }

}
