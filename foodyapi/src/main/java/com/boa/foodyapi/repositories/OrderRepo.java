package com.boa.foodyapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boa.foodyapi.models.Order;

public interface OrderRepo extends JpaRepository<Order,Long>{

}
