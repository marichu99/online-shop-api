package com.product.inventoryservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.product.inventoryservice.model.GenericResponse;
import com.product.inventoryservice.model.InventoryRequest;
import com.product.inventoryservice.model.InventoryResponse;
import com.product.inventoryservice.service.InventoryService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequestMapping("api/inventory")
@RestController
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GenericResponse<InventoryResponse> create(@RequestBody InventoryRequest inventoryCreateDto) {
        return GenericResponse.<InventoryResponse>builder()
                .data(inventoryService.createInventory(inventoryCreateDto))
                .success(true)
                .message("Inventory saved successfully")
                .build();
    }

    @GetMapping("check")
    @ResponseStatus(code = HttpStatus.OK)
    public GenericResponse<Boolean> checkInventory(@RequestParam(name = "productCodes") List<String> productCodes,@RequestParam("productQuantities") List<Integer> productQuantities){
        log.info("productCodes," ,productCodes);
        log.info("productQuantities",productQuantities);

        return GenericResponse.<Boolean>builder()
                .data(inventoryService.checkInventory(productCodes,productQuantities))
                .success(true)
                .message("Inventory Exists")
                .build();
    }



}