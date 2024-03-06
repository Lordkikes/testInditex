package com.inditex.app.infrastructure.controllers;

import com.inditex.app.domain.dto.PriceDto;
import com.inditex.app.domain.ports.ServicePort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
public class PriceControllerAdapter implements PriceApi{

    @Autowired
    private ServicePort port;
    @Override
    public ResponseEntity<PriceDto> getProductPrice(Long brandId, LocalDateTime date, Long productId) {
        PriceDto price = port.getPrice(brandId, date, productId);
        return ResponseEntity.ok(price);
    }
}
