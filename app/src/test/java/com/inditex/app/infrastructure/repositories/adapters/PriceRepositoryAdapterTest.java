package com.inditex.app.infrastructure.repositories.adapters;

import com.inditex.app.infrastructure.repositories.PriceRepository;
import com.inditex.app.infrastructure.repositories.entities.Brand;
import com.inditex.app.infrastructure.repositories.entities.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryAdapterTest {

    @Mock
    private PriceRepository mockRepository;

    @InjectMocks
    private PriceRepositoryAdapter priceRepositoryAdapterUnderTest;

    private Price price1;
    private Price price2;

    @BeforeEach
    public void setUp() {
        LocalDateTime startDate1 = LocalDateTime.of(2023, 4, 1, 0, 0);
        LocalDateTime endDate1 = LocalDateTime.of(2023, 4, 30, 23, 59, 59);
        LocalDateTime startDate2 = LocalDateTime.of(2023, 5, 1, 0, 0);
        LocalDateTime endDate2 = LocalDateTime.of(2023, 5, 31, 23, 59, 59);
        priceRepositoryAdapterUnderTest = new PriceRepositoryAdapter(mockRepository);

        price1 = Price.builder()
                .endDate(endDate1)
                .price(new BigDecimal(25))
                .startDate(startDate1)
                .brand(new Brand(1L,"ZARA"))
                .productId(35455L)
                .currency("EUR")
                .id(1L)
                .priceList(2)
                .priority(0)
                .build();
        price2 = Price.builder()
                .endDate(endDate2)
                .price(new BigDecimal(35))
                .startDate(startDate2)
                .brand(new Brand(1L,"ZARA"))
                .productId(35455L)
                .currency("EUR")
                .id(1L)
                .priceList(2)
                .priority(0)
                .build();
    }

    @Test
    void testGetPrice() {
        // Setup
        final List<Price> expectedResult = List.of(Price.builder().build());
        when(mockRepository.findByBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdOrderByPriorityDesc(
                0L, LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0), 0L))
                .thenReturn(List.of(Price.builder().build()));

        // Run the test
        final List<Price> result = priceRepositoryAdapterUnderTest.getPrice(0L, LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetPrice_PriceRepositoryReturnsNoItems() {
        // Setup
        when(mockRepository.findByBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdOrderByPriorityDesc(
                0L, LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2020, 1, 1, 0, 0, 0), 0L))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<Price> result = priceRepositoryAdapterUnderTest.getPrice(0L, LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetPriceWithExistingPrice() {
        List<Price> expectedPrices = new ArrayList<>();
        expectedPrices.add(price1);
        expectedPrices.add(price2);

        when(mockRepository.findByBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdOrderByPriorityDesc(
                1L, LocalDateTime.of(2023, 4, 15, 0, 0), LocalDateTime.of(2023, 4, 15, 0, 0), 1L))
                .thenReturn(expectedPrices);

        List<Price> actualPrices = priceRepositoryAdapterUnderTest.getPrice(1L, LocalDateTime.of(2023, 4, 15, 0, 0), 1L);

        assertEquals(expectedPrices, actualPrices);
    }
}
