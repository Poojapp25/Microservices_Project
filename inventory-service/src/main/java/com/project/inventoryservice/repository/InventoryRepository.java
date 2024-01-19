package com.project.inventoryservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.inventoryservice.Model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	Optional<Inventory> findBySkuCode(String skucode);

	List<Inventory> findBySkuCodeIn(List<String> skuCode);
	
}
