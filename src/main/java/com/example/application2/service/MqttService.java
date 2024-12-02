package com.example.application2.service;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class MqttService implements MqttCallback {

    private MqttClient client;
    private String lastTemp = "No Data";
    private String lastHum = "No Data";
    private final DynamoDBService dynamoDBService; // Ajout du service DynamoDB

    public MqttService(DynamoDBService dynamoDBService) {
        this.dynamoDBService = dynamoDBService; // Injecter le service DynamoDB
        try {
            client = new MqttClient("tcp://test.mosquitto.org:1883", MqttClient.generateClientId(), new MemoryPersistence());
            client.setCallback((MqttCallback) this);
            client.connect();
            // Subscribe to both temperature and humidity topics
            client.subscribe("/Thinkitive/temp");
            client.subscribe("/Thinkitive/hum");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        if (topic.equals("/Thinkitive/temp")) {
            lastTemp = payload;
            System.out.println("Temperature received: " + lastTemp);
        } else if (topic.equals("/Thinkitive/hum")) {
            lastHum = payload;
            System.out.println("Humidity received: " + lastHum);
        }

        // Sauvegarder dans DynamoDB après avoir reçu les deux données
        if (!lastTemp.equals("No Data") && !lastHum.equals("No Data")) {
            dynamoDBService.saveSensorData(lastTemp, lastHum);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Not used in this example
    }

    public String getLastTemp() {
        return lastTemp;
    }

    public String getLastHum() {
        return lastHum;
    }
}
