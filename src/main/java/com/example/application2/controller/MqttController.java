package com.example.application2.controller;

import com.example.application2.dto.DataDto;
import com.example.application2.service.FluxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "dashboard", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public class MqttController {

    private final FluxService fluxService;

    @GetMapping(value = "/temperature", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DataDto> getTemperature() {
        return fluxService.temperature();
    }

    @GetMapping(value = "/humidity", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DataDto> getHumidity() {
        return fluxService.humidity();
    }




    @GetMapping(value = "/view/temperature", produces = MediaType.TEXT_HTML_VALUE)
    public String getTemperaturePage() {
        return "temperature"; // This resolves to `temperature.html` in `src/main/resources/templates`
    }

    @GetMapping(value = "/view/humidity", produces = MediaType.TEXT_HTML_VALUE)
    public String getHumidityPage() {
        return "humidity"; // This resolves to `temperature.html` in `src/main/resources/templates`
    }
}
