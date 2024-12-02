package com.example.application2.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TelegramService {

    private final WebClient webClient;
    private final String botToken = "7863554244:AAElAnmeMOXAxR8dyKGG-CD-PvWOAil6x0A"; // Remplacez par votre token Telegram
    private final String chatId = "941591722"; // Remplacez par votre Chat ID

    public TelegramService() {
        this.webClient = WebClient.create("https://api.telegram.org");
    }

    public void sendMessage(String message) {
        String url = String.format("/bot%s/sendMessage?chat_id=%s&text=%s", botToken, chatId, message);
        webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> System.out.println("Message envoyé : " + response));
    }
}
