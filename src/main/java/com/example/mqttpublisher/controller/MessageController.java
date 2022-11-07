package com.example.mqttpublisher.controller;

import com.example.mqttpublisher.controller.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@RestController
@RequestMapping("/")
@Slf4j
public class MessageController {
    private static final String TOPIC = "/jp05/test";

    @Autowired
    private final IMqttClient mqttClient;

    private final String payload;

    public MessageController(IMqttClient mqttClient) throws IOException {
        this.mqttClient = mqttClient;
        InputStream is = getClass().getResourceAsStream("/file.txt");
        this.payload = new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    @PostMapping
    public ResponseEntity<String> publishMessage(@RequestBody Message message) throws MqttException {
        String msg = !message.getPayload().equalsIgnoreCase("file")
                ? message.getPayload() : payload;
        log.info("Publishing message: {}", msg);
        mqttClient.publish(TOPIC, new MqttMessage(msg
                .getBytes(StandardCharsets.UTF_8)));
        return ResponseEntity.ok("Message published");
    }
}
