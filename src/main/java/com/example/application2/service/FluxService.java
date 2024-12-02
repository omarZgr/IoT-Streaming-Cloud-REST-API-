package com.example.application2.service;

import com.example.application2.dto.DataDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class FluxService {

    private final AlertService alertService;

    public FluxService(AlertService alertService) {
        this.alertService = alertService;
    }

    public Flux<DataDto> temperature() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> {
                    float value = 20 + new Random().nextFloat() * 10; // Simule une température réaliste
                    String time = LocalDateTime.now().format(formatter);
                    return new DataDto(value, time);
                })
                .doOnNext(data -> alertService.checkAndNotify(data, "temperature"));
    }

    public Flux<DataDto> humidity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> {
                    float value = 30 + new Random().nextFloat() * 40; // Simule une humidité réaliste (30%-70%)
                    String time = LocalDateTime.now().format(formatter);
                    return new DataDto(value, time);
                })
                .doOnNext(data -> alertService.checkAndNotify(data, "humidity"));
    }
}
