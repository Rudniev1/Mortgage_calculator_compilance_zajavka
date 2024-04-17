package com.company.model;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

@Getter
@With
@Builder
public class InputData {

    private static final BigDecimal PERCENT = new BigDecimal("100");

    LocalDate repaymentStartDate;
    BigDecimal wiborPercent;
    BigDecimal amount;
    BigDecimal monthsDuration;
    InstallmentType installmentType;
    BigDecimal marginPercent;
    BigDecimal overpaymentProvisionPercent;
    BigDecimal overpaymentProvisionMonths;
    Map<Integer, BigDecimal> overpaymentSchema;
    BigDecimal overpaymentStartMonth;
    String overpaymentReduceWay;
    boolean mortgagePrintPayoffsSchedule;
    Integer mortgageRateNumberToPrint;

    public static InputData defaultInputData() {
        return InputData.builder()
                .repaymentStartDate(LocalDate.of(2020, 12, 10))
                .wiborPercent(BigDecimal.valueOf(1.70))
                .amount(BigDecimal.valueOf(301953.46))
                .monthsDuration(BigDecimal.valueOf(180))
                .installmentType(InstallmentType.CONSTANT)
                .marginPercent(BigDecimal.valueOf(1.6))
                .overpaymentProvisionPercent(BigDecimal.valueOf(3))
                .overpaymentProvisionMonths(BigDecimal.valueOf(36))
                .overpaymentStartMonth(BigDecimal.valueOf(1))
                .overpaymentSchema(Map.of(
                        2, BigDecimal.valueOf(10000),
                        3, BigDecimal.valueOf(10000),
                        5, BigDecimal.valueOf(10000),
                        6, BigDecimal.valueOf(10000),
                        7, BigDecimal.valueOf(10000))
                )
                .overpaymentReduceWay(Overpayment.REDUCE_PERIOD)
                .mortgagePrintPayoffsSchedule(true)
                .mortgageRateNumberToPrint(1)
                .build();
    }


    public BigDecimal getWiborPercent() {
        return wiborPercent.divide(PERCENT, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal getMarginPercent() {
        return marginPercent.divide(PERCENT, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal getOverpaymentProvisionPercent() {
        return overpaymentProvisionPercent.divide(PERCENT, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal getInterestPercent() {
        return getMarginPercent().add(getWiborPercent());
    }

    public BigDecimal getInterestToDisplay() {
        return wiborPercent.add(marginPercent);
    }
}
