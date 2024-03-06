package com.inditex.app.application;

import com.inditex.app.domain.dto.PriceDto;
import com.inditex.app.domain.exceptions.EntityNotFoundException;
import com.inditex.app.domain.ports.RepositoryPort;
import com.inditex.app.infrastructure.repositories.entities.Brand;
import com.inditex.app.infrastructure.repositories.entities.Price;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceUseCaseTest {

    @Mock
    private RepositoryPort mockPort;

    @InjectMocks
    private PriceUseCase priceUseCaseUnderTest;

    private Price price;

    @BeforeEach
    public void setUp() {
        LocalDateTime startDate = LocalDateTime.of(2023, 4, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 4, 30, 23, 59, 59);
        priceUseCaseUnderTest = new PriceUseCase(mockPort);
        MockitoAnnotations.openMocks(this);

        price = Price.builder()
                .endDate(endDate)
                .price(new BigDecimal(25))
                .startDate(startDate)
                .brand(new Brand(1L,"ZARA"))
                .productId(35455L)
                .currency("EUR")
                .id(1L)
                .priceList(2)
                .priority(0)
                .build();
    }

    @Test
    void testGetPriceWhenNoPriceExists() {
        // Arrange
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.now();
        Long productId = 123L;
        when(mockPort.getPrice(brandId, date, productId)).thenReturn(new ArrayList<>());

        // Act and Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> priceUseCaseUnderTest.getPrice(brandId, date, productId));
        assertEquals("la entidad no se encuentra en base de datos, productid:" + productId +
                " brandId:" + brandId + " date:" + date.format(DateTimeFormatter.ISO_DATE_TIME), exception.getMessage());
    }

    @Test
    void testGetPriceWithExistingPrice() {
        List<Price> prices = new ArrayList<>();
        prices.add(price);

        when(mockPort.getPrice(1L, LocalDateTime.of(2023, 4, 15, 0, 0), 1L)).thenReturn(prices);

        PriceDto expectedPriceDto = PriceDto.builder()
                .brandName(price.getBrand().getBrandName())
                .productId(price.getProductId())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price( new DecimalFormat("0.00#").format(price.getPrice())+" "+price.getCurrency())
                .priceList(price.getPriceList())
                .build();

        PriceDto actualPriceDto = priceUseCaseUnderTest.getPrice(1L, LocalDateTime.of(2023, 4, 15, 0, 0), 1L);

        assertEquals(expectedPriceDto, actualPriceDto);
    }

    @Test
    void testGetPriceWithNonExistingPrice() {
        List<Price> prices = new ArrayList<>();

        when(mockPort.getPrice(1L, LocalDateTime.of(2023, 5, 1, 0, 0), 1L)).thenReturn(prices);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDate = LocalDateTime.of(2023, 5, 1, 0, 0).format(formatter);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> priceUseCaseUnderTest.getPrice(1L, LocalDateTime.of(2023, 5, 1, 0, 0), 1L));

        assertEquals("la entidad no se encuentra en base de datos, productid:1 brandId:1 date:" + formattedDate,
                exception.getMessage());
    }
}

