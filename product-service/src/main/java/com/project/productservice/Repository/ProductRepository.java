package com.project.productservice.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.productservice.Model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}