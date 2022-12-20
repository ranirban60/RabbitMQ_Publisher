package com.example.controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.configuration.RabbitMQConfigure;
import com.example.model.CustomMessage;

@RestController
public class MessagePublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@PostMapping("/publish")
	public String publishMessage(@RequestBody CustomMessage customMessage ) {
		customMessage.setMessage(UUID.randomUUID().toString());
		customMessage.setMessageDate(new Date());
		rabbitTemplate.convertAndSend(RabbitMQConfigure.EXCHANGE,
				RabbitMQConfigure.ROUTING_KEY, customMessage);
		
		return "Message Published";
	}
}
