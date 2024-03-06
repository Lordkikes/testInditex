package com.inditex.app.infrastructure.repositories;

import com.inditex.app.infrastructure.repositories.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdOrderByPriorityDesc(
            Long brandId, LocalDateTime startDate, LocalDateTime endDate, Long productId);
}
