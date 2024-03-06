package com.inditex.app.infrastructure.repositories.adapters;

import com.inditex.app.domain.ports.RepositoryPort;
import com.inditex.app.infrastructure.repositories.PriceRepository;
import com.inditex.app.infrastructure.repositories.entities.Price;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Repository
public class PriceRepositoryAdapter implements RepositoryPort {

    PriceRepository repository;
    public List<Price> getPrice(Long brandId, LocalDateTime date, Long productId){
        return repository.findByBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdOrderByPriorityDesc(brandId,date,date,productId);
    }
}
