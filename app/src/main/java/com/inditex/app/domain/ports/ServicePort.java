package com.inditex.app.domain.ports;

import com.inditex.app.domain.dto.PriceDto;

import java.time.LocalDateTime;

public interface ServicePort {
    PriceDto getPrice(Long brandId, LocalDateTime date, Long productId);
}
