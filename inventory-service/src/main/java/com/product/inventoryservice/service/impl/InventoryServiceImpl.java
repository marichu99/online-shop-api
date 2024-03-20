package com.product.inventoryservice.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.product.inventoryservice.entity.Inventory;
import com.product.inventoryservice.model.InventoryRequest;
import com.product.inventoryservice.model.InventoryResponse;
import com.product.inventoryservice.repository.InventoryRepository;
import com.product.inventoryservice.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository){
        this.inventoryRepository=inventoryRepository;
    }

    @Override
    public InventoryResponse createInventory(InventoryRequest inventoryCreateDto) {
       var savedObj =  inventoryRepository.save(mapToInventory(inventoryCreateDto));
       return mapToInventoryResponse(savedObj);

    }
    private Inventory mapToInventory(InventoryRequest source){
        Inventory target = new Inventory();
        BeanUtils.copyProperties(source, target);
        return target;

    }
    private InventoryResponse mapToInventoryResponse(Inventory source){
        InventoryResponse target = new InventoryResponse();
        BeanUtils.copyProperties(source, target);
        return target;

    }

}