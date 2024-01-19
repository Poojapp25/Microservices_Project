package com.project.orderservice.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {
	private long id;
	private String skuCode;
	private BigDecimal price;
	private Integer quantity;
}
