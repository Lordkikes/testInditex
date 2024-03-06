package com.inditex.app.application;

import com.inditex.app.domain.dto.PriceDto;
import com.inditex.app.domain.exceptions.EntityNotFoundException;
import com.inditex.app.domain.ports.RepositoryPort;
import com.inditex.app.domain.ports.ServicePort;
import com.inditex.app.infrastructure.repositories.entities.Price;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Service
public class PriceUseCase implements ServicePort {

    private RepositoryPort port;

    public PriceDto getPrice(Long brandId, LocalDateTime date, Long productId) {
        PriceDto priceDto;
        List<Price> prices = port.getPrice(brandId, date, productId);
        if(prices.isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String formattedDate = date.format(formatter);
            throw new EntityNotFoundException("la entidad no se encuentra en base de datos, productid:" + productId + " brandId:" + brandId + " date:" + formattedDate );
        }else{
            Price price=prices.get(0);
            priceDto=PriceDto.builder()
                    .endDate(price.getEndDate())
                    .price( new DecimalFormat("0.00#").format(price.getPrice())+" "+price.getCurrency())
                    .startDate(price.getStartDate())
                    .brandName(price.getBrand().getBrandName())
                    .productId(price.getProductId())
                    .priceList(price.getPriceList())
                    .build();
        }
        return priceDto;


    }

}
