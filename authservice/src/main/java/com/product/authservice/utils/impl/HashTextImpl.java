package com.product.authservice.utils.impl;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.product.authservice.utils.HashText;

@Service
public class HashTextImpl implements HashText {

    @Override
    public String hash(String text) {
        byte[] encodedBytes = Base64.getEncoder().encode(text.getBytes());
        return new String(encodedBytes);
    }
    
}
