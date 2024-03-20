package com.product.inventoryservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.inventoryservice.model.GenericResponse;
import com.product.inventoryservice.model.InventoryRequest;
import com.product.inventoryservice.model.InventoryResponse;
import com.product.inventoryservice.service.InventoryService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("api/inventory")
@RestController
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("create")
    public GenericResponse<InventoryResponse> create(@RequestBody InventoryRequest inventoryCreateDto) {
        return GenericResponse.<InventoryResponse>builder()
                .data(inventoryService.createInventory(inventoryCreateDto))
                .success(true)
                .message("Inventory saved successfully")
                .build();
    }

}