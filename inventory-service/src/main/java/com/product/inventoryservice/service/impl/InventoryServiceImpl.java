package com.product.inventoryservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.product.inventoryservice.entity.Inventory;
import com.product.inventoryservice.exception.NotEnoughQuantityException;
import com.product.inventoryservice.model.InventoryRequest;
import com.product.inventoryservice.model.InventoryResponse;
import com.product.inventoryservice.repository.InventoryRepository;
import com.product.inventoryservice.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryResponse createInventory(InventoryRequest inventoryCreateDto) {
        var savedObj = inventoryRepository.save(mapToInventory(inventoryCreateDto));
        return mapToInventoryResponse(savedObj);

    }

    private Inventory mapToInventory(InventoryRequest source) {
        Inventory target = new Inventory();
        BeanUtils.copyProperties(source, target);
        return target;

    }

    private InventoryResponse mapToInventoryResponse(Inventory source) {
        InventoryResponse target = new InventoryResponse();
        BeanUtils.copyProperties(source, target);
        return target;

    }

    @Override
    public Boolean checkInventory(List<String> productCodes, List<Integer> productQuantities) {
        Map<String, Integer> unavailableItems = new HashMap<>();

        for (int i = 0; i < productCodes.size(); i++) {
            String productCode = productCodes.get(i);
            Integer productQuantity = productQuantities.get(i);
            Inventory inventory = inventoryRepository.findByProductCode(productCode).orElse(null);
            if (inventory != null) {
                // check if enough
                var dbInventory = inventory.getQuantity();
                if (productQuantity > dbInventory) {
                    unavailableItems.put(productCode, productQuantity - dbInventory);
                }
            } else {
                unavailableItems.put(productCode, productQuantity);
            }
        }
        if (unavailableItems.isEmpty()) {
            return true;
        } else {
            throw new NotEnoughQuantityException("Not Enough Quantity in Stock", unavailableItems);
        }

    }
}
