package com.company.service;

import com.company.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ReferenceCalculationServiceImpl implements ReferenceCalculationService {

    @Override
    public MortgageReference calculate(InstallmentAmounts installmentAmounts, InputData inputData) {
        if (BigDecimal.ZERO.equals(inputData.getAmount())) {
            return new MortgageReference(BigDecimal.ZERO, BigDecimal.ZERO);
        }

        return new MortgageReference(inputData.getAmount(), inputData.getMonthsDuration());
    }

    @Override
    public MortgageReference calculate(InstallmentAmounts installmentAmounts, final InputData inputData, Installment previousInstallment) {
        if (BigDecimal.ZERO.equals(previousInstallment.mortgageResidual().residualAmount())) {
            return new MortgageReference(BigDecimal.ZERO, BigDecimal.ZERO);
        }

        return switch (inputData.getOverpaymentReduceWay()) {
            case Overpayment.REDUCE_RATE ->
                    reduceRateMortgageReference(installmentAmounts, previousInstallment.mortgageResidual());
            case Overpayment.REDUCE_PERIOD ->
                    new MortgageReference(inputData.getAmount(), inputData.getMonthsDuration());
            default -> throw new MortgageException("Case not handled");
        };

    }

    private MortgageReference reduceRateMortgageReference(final InstallmentAmounts installmentAmounts, final MortgageResidual previousResidual) {
        if (installmentAmounts.overpayment().amount().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal residualAmount = calculateResidualAmount(previousResidual.residualAmount(), installmentAmounts);
            return new MortgageReference(residualAmount, previousResidual.residualDuration().subtract(BigDecimal.ONE));
        }
        return new MortgageReference(previousResidual.residualAmount(), previousResidual.residualDuration());
    }

    private BigDecimal calculateResidualAmount(final BigDecimal residualAmount, final InstallmentAmounts installmentAmounts) {
        return residualAmount
            .subtract(installmentAmounts.capitalAmount())
            .subtract(installmentAmounts.overpayment().amount())
            .max(BigDecimal.ZERO);
    }

}
