package com.boa.foodyapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.boa.foodyapi.models.Order;
import com.boa.foodyapi.services.OrderService;
import com.boa.foodyapi.services.PublishOrder;


@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private PublishOrder publishOrder;
	
	@PostMapping("/orders")
	public ResponseEntity<?> saveOrder(@RequestBody Order order){
		if(orderService.addOrder(order)!=null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(order);
		}
		else
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Data Error");
	}
	
	@GetMapping("/orders/{orderId}")
	public Order fetchOrderById(@PathVariable("orderId") long orderId){
		return orderService.getOrderById(orderId);
	}
	
	@GetMapping("/orders/publish/{orderId}")
	public void publish(@PathVariable("orderId") long orderId){
		   publishOrder.sendMessage(orderId);
	}
}
