package service;

import com.company.model.InputData;
import com.company.model.Installment;
import com.company.model.InstallmentAmounts;
import com.company.model.Overpayment;
import com.company.service.*;
import fixtures.TestDataFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class InstallmentCalculationServiceImplTest {

  @InjectMocks
    private InstallmentCalculationServiceImpl installmentCalculationService;

  @Mock
  private TimePointCalculationService timePointCalculationService;
  @Mock
  private OverpaymentCalculationService overpaymentCalculationService;
  @Mock
  private AmountsCalculationService amountsCalculationService;
  @Mock
  private ResidualCalculationService residualCalculationService;
  @Mock
  private ReferenceCalculationService referenceCalculationService;

  @Test
    void thatInstallmentsAreCalculatedCorrectly(){
      //given
      final var inputData = TestDataFixtures.someInputData();
      final var expected = IntStream.rangeClosed(1,180).boxed().map(this::createInstallment).toList();

      when(timePointCalculationService.calculate(any(BigDecimal.class),any(InputData.class))).thenReturn(TestDataFixtures.someTimePoint());
      when(timePointCalculationService.calculate(any(BigDecimal.class),any(Installment.class))).thenReturn(TestDataFixtures.someTimePoint());

      when(amountsCalculationService.calculate(any(InputData.class),any(Overpayment.class))).thenReturn(TestDataFixtures.someInstallmentAmounts());
      when(amountsCalculationService.calculate(any(InputData.class),any(Overpayment.class),any(Installment.class))).thenReturn(TestDataFixtures.someInstallmentAmounts());

      when(residualCalculationService.calculate(any(InstallmentAmounts.class),any(InputData.class))).thenReturn(TestDataFixtures.someMortgageResidual());
      when(residualCalculationService.calculate(any(InstallmentAmounts.class),any(InputData.class),any(Installment.class))).thenReturn(TestDataFixtures.someMortgageResidual());

      when(referenceCalculationService.calculate(any(InstallmentAmounts.class),any(InputData.class))).thenReturn(TestDataFixtures.someMortgageReference());
      when(referenceCalculationService.calculate(any(InstallmentAmounts.class),any(InputData.class),any(Installment.class))).thenReturn(TestDataFixtures.someMortgageReference());

      when(overpaymentCalculationService.calculate(any(BigDecimal.class),any(InputData.class))).thenReturn(TestDataFixtures.someOverpayment());

      //when
      final var result = installmentCalculationService.calculate(inputData);

      //then
      Assertions.assertEquals(expected, result);
  }

  private Installment createInstallment(final Integer index){
      return TestDataFixtures.someInstallment()
              .withInstallmentNumber(BigDecimal.valueOf(index));
  }
}