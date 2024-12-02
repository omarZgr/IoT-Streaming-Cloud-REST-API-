package com.example.application2.service;


import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

@Service
public class DynamoDBService {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDBService() {
        this.dynamoDbClient = DynamoDbClient.builder()
                .region(Region.of("eu-west-1")) // Spécifie la région ici
                .build();
    }

    public void saveSensorData(String temperature, String humidity) {
        // Créer un élément pour la table
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(java.util.UUID.randomUUID().toString()).build());
        item.put("temperature", AttributeValue.builder().s(temperature).build());
        item.put("humidity", AttributeValue.builder().s(humidity).build());

        // Requête pour insérer l'élément dans DynamoDB
        PutItemRequest request = PutItemRequest.builder()
                .tableName("SensorData")  // Utiliser le nom de la table que tu as créée
                .item(item)
                .build();

        // Envoyer la requête
        dynamoDbClient.putItem(request);
        System.out.println("Data saved in DynamoDB: Temperature=" + temperature + ", Humidity=" + humidity);
    }
}

