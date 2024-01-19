package com.project.orderservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.orderservice.Model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
