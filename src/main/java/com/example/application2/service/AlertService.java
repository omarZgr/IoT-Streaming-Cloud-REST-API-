package com.example.application2.service;

import com.example.application2.dto.DataDto;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private final TelegramService telegramService;

    public AlertService(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    public void checkAndNotify(DataDto data, String type) {
        float threshold = type.equals("temperature") ? 29.6f : 69.3f; // Seuils pour température et humidité
        if (data.getValue() > threshold) {
            String alertMessage = String.format("⚠️ Alerte %s : Valeur élevée détectée : %.2f à %s",
                    type, data.getValue(), data.getTime());
            telegramService.sendMessage(alertMessage);
        }
    }
}
