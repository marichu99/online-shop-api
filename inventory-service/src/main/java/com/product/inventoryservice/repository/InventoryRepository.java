package com.product.inventoryservice.repository;


import com.product.inventoryservice.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface InventoryRepository  extends MongoRepository<Inventory, String>{
    
}
