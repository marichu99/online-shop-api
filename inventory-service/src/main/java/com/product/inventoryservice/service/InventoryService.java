package com.product.inventoryservice.service;

import java.util.List;

import com.product.inventoryservice.model.InventoryRequest;
import com.product.inventoryservice.model.InventoryResponse;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest inventoryCreateDto);

    Boolean checkInventory(List<String> productCodes, List<Integer> productQuantities);

}