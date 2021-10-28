package com.boa.foodyapi.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boa.foodyapi.models.Order;
import com.boa.foodyapi.repositories.OrderRepo;


@Service
public class OrderService {
	  @Autowired
		private OrderRepo orderRepo;
	    
	    //save
	    public Order addOrder(Order order) {
	    	return orderRepo.save(order);
	    }
	    
	    //retrieval
	    public Order getOrderById(long orderId) {
	    	return orderRepo.findById(orderId).orElse(null);
	    	}
}
