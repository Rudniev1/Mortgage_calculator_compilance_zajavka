package com.company.service;

import com.company.model.InputData;
import com.company.model.Overpayment;
import com.company.model.Installment;
import com.company.model.InstallmentAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface AmountsCalculationService {

    BigDecimal YEAR = BigDecimal.valueOf(12);

    InstallmentAmounts calculate(final InputData inputData, final Overpayment overpayment);

    InstallmentAmounts calculate(final InputData inputData, final Overpayment overpayment, final Installment previousInstallment);

    static BigDecimal calculateInterestAmount(final BigDecimal residualAmount, final BigDecimal interestPercentValue) {
        return residualAmount.multiply(interestPercentValue).divide(AmountsCalculationService.YEAR, 2, RoundingMode.HALF_UP);
    }

    static BigDecimal calculateQ(final BigDecimal interestPercent) {
        return interestPercent.divide(AmountsCalculationService.YEAR, 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
    }

    static BigDecimal compareCapitalWithResidual(final BigDecimal capitalAmount, final BigDecimal residualAmount) {
        if (capitalAmount.compareTo(residualAmount) >= 0) {
            return residualAmount;
        }
        return capitalAmount;
    }

}
