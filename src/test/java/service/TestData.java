package service;

import com.company.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestData {

    static Rate someRate() {
        return Rate.builder()
            .timePoint(someTimePoint())
            .mortgageResidual(someMortgageResidual())
            .mortgageReference(someMortgageReference())
            .build();
    }

    private static MortgageReference someMortgageReference() {
        return MortgageReference.builder()
            .referenceAmount(new BigDecimal("235243.12"))
            .referenceDuration(BigDecimal.valueOf(83))
            .build();
    }

    private static MortgageResidual someMortgageResidual() {
        return MortgageResidual.builder()
            .residualAmount(new BigDecimal("662364.12"))
            .residualDuration(BigDecimal.valueOf(16))
            .build();
    }

    static InputData someInputData() {
        return InputData.builder()
            .repaymentStartDate(LocalDate.of(2010, 5, 10))
            .wiborPercent(BigDecimal.valueOf(2.70))
            .amount(BigDecimal.valueOf(198267.46))
            .monthsDuration(BigDecimal.valueOf(180))
            .rateType(RateType.CONSTANT)
            .marginPercent(BigDecimal.valueOf(1.8))
            .overpaymentProvisionPercent(BigDecimal.valueOf(3))
            .overpaymentProvisionMonths(BigDecimal.valueOf(36))
            .overpaymentStartMonth(BigDecimal.valueOf(1))
            .overpaymentReduceWay(Overpayment.REDUCE_PERIOD)
            .mortgagePrintPayoffsSchedule(true)
            .mortgageRateNumberToPrint(1)
            .build();
    }

    static TimePoint someTimePoint() {
        return TimePoint.builder()
            .year(BigDecimal.valueOf(1))
            .month(BigDecimal.valueOf(1))
            .date(LocalDate.of(2010, 5, 10))
            .build();
    }

    static RateAmounts someRateAmounts() {
        return RateAmounts.builder()
            .rateAmount(new BigDecimal("1516.73"))
            .capitalAmount(new BigDecimal("773.23"))
            .interestAmount(new BigDecimal("743.50"))
            .build();
    }
}
