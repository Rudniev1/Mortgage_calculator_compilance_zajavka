package service;

import com.company.service.ConstantAmountsCalculationService;
import com.company.service.ConstantAmountsCalculationServiceImpl;
import fixtures.TestDataFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.company.model.InputData;
import com.company.model.Rate;
import com.company.model.RateAmounts;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class ConstantAmountsCalculationServiceImplTest {

    @InjectMocks
    private ConstantAmountsCalculationService constantAmountsCalculationService = new ConstantAmountsCalculationServiceImpl();

    @Test
    @DisplayName("Calculate rate amounts for first rate")
    void shouldCalculateFirstRateAmountsCorrectly() {
        // given
        InputData inputData = TestDataFixtures.someInputData();
        RateAmounts expected = TestDataFixtures.someRateAmounts();

        // when
        RateAmounts result = constantAmountsCalculationService.calculate(inputData, null);

        // then
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Calculate rate amounts for other rates")
    void shouldCalculateOtherRateAmountsCorrectly() {
        // given
        InputData inputData = TestDataFixtures.someInputData();
        Rate rate = TestDataFixtures.someRate();
        RateAmounts expected = TestDataFixtures.someRateAmounts()
            .withRateAmount(new BigDecimal("3303.45"))
            .withInterestAmount(new BigDecimal("2483.87"))
            .withCapitalAmount(new BigDecimal("819.58"));

        // when
        RateAmounts result = constantAmountsCalculationService.calculate(inputData, null, rate);

        // then
        Assertions.assertEquals(expected, result);
    }
}