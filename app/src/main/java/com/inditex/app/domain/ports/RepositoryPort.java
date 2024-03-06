package com.inditex.app.domain.ports;

import com.inditex.app.infrastructure.repositories.entities.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositoryPort {
    List<Price> getPrice(Long brandId, LocalDateTime date, Long productId);
}
