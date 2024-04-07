package service;

import com.company.service.ConstantAmountsCalculationService;
import com.company.service.ConstantAmountsCalculationServiceImpl;
import fixtures.TestDataFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.company.model.InputData;
import com.company.model.Installment;
import com.company.model.InstallmentAmounts;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class ConstantAmountsCalculationServiceImplTest {

    @InjectMocks
    private ConstantAmountsCalculationService constantAmountsCalculationService = new ConstantAmountsCalculationServiceImpl();

    @Test
    @DisplayName("Calculate installment amounts for first rate")
    void shouldCalculateFirstInstallmentAmountsCorrectly() {
        // given
        InputData inputData = TestDataFixtures.someInputData();
        InstallmentAmounts expected = TestDataFixtures.someRateAmounts();

        // when
        InstallmentAmounts result = constantAmountsCalculationService.calculate(inputData, null);

        // then
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Calculate installment amounts for other rates")
    void shouldCalculateOtherInstallmentAmountsCorrectly() {
        // given
        InputData inputData = TestDataFixtures.someInputData();
        Installment installment = TestDataFixtures.someInstallment();
        InstallmentAmounts expected = TestDataFixtures.someRateAmounts()
            .withInstallmentAmount(new BigDecimal("3303.45"))
            .withInterestAmount(new BigDecimal("2483.87"))
            .withCapitalAmount(new BigDecimal("819.58"));

        // when
        InstallmentAmounts result = constantAmountsCalculationService.calculate(inputData, null, installment);

        // then
        Assertions.assertEquals(expected, result);
    }
}