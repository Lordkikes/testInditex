package com.inditex.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto{

    private Long productId;
    private String brandName;
    private String price;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
