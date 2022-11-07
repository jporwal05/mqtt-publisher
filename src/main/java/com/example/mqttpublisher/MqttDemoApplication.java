package com.example.mqttpublisher;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class MqttDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqttDemoApplication.class, args);
	}

	@Bean
	public IMqttClient mqttClient() throws MqttException {
		String publisherId = UUID.randomUUID().toString();
		IMqttClient client = new MqttClient("tcp://test.mosquitto.org:1883", publisherId);
		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		options.setConnectionTimeout(10);
		client.connect(options);
		return client;
	}

}
