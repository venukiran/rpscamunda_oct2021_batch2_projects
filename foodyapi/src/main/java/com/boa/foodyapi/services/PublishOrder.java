package com.boa.foodyapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.boa.foodyapi.models.Order;

@Service
public class PublishOrder {
	@Autowired
	private OrderService orderService;
	private boolean status;
	// 1. General topic with string payload

	@Value(value = "${order.topic.name}")
	private String orderTopicName;

	
	
	@Autowired
	private KafkaTemplate<String, Order> kafkaTemplate;

	public void sendMessage(long orderId) {

		Order order = orderService.getOrderById(orderId);

		if (order != null) {
			ListenableFuture<SendResult<String, Order>> future = this.kafkaTemplate.send(orderTopicName, order);

			future.addCallback(new ListenableFutureCallback<SendResult<String, Order>>() {
				@Override
				public void onSuccess(SendResult<String, Order> result) {
					System.out.println("Sent message: " + order.getOrderId() + " with offset: "
							+ result.getRecordMetadata().offset());
					System.out.println("Sent message: " + order.getOrderId() + " with offset: "
							+ result.getRecordMetadata().offset());
					// status=true;
				}

				@Override
				public void onFailure(Throwable ex) {
					System.out.println("Unable to send product Data : " + order.getOrderId() + ex);
					// status=false;
				}
			});

		}
	}
	
	 @KafkaListener(topics = "${order.topic.name}", groupId = "${order.topic.group.id}")
	  public void listener(String message) {
		  System.out.println("Received message = {}"+ message);
	  }
}
