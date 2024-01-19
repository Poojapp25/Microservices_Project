package com.project.orderservice.Service;

import java.util.UUID;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.project.orderservice.Model.Order;
import com.project.orderservice.Model.OrderLineItems;
import com.project.orderservice.Repository.OrderRepository;
import com.project.orderservice.dto.InventoryResponse;
import com.project.orderservice.dto.OrderLineItemsDto;
import com.project.orderservice.dto.OrderRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	private final WebClient webClient;
	
	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
			.stream()
			.map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
			.toList();
		
		order.setOrderLineItemList(orderLineItems);
		
		List<String> skuCodes = order.getOrderLineItemList().stream().map(orderLineItem -> 								orderLineItem.getSkuCode()).toList();
		
		//Call Inventory Service and place Order only if Product is in Stock
		InventoryResponse[] inventoryResponseArray = webClient.get()
				.uri("http://localhost:8082/api/inventory", 
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
				.retrieve()
				.bodyToMono(InventoryResponse[].class)	
//Mono: datatype in reactive framework, to be able to read data from webclient, mono should be used
				.block();	
//by default webclient provides asynch. response. So to make it synch., block is used
		
		boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.isInStock());
		
		if(allProductsInStock) {
			orderRepository.save(order);
		}else {
			throw new IllegalArgumentException("Product is not in stock, please try again later.");
		}
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
}
