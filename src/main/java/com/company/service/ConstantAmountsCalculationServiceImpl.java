package com.company.service;

import lombok.extern.slf4j.Slf4j;
import com.company.model.InputData;
import com.company.model.Overpayment;
import com.company.model.Installment;
import com.company.model.InstallmentAmounts;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class ConstantAmountsCalculationServiceImpl implements ConstantAmountsCalculationService {

    @Override
    public InstallmentAmounts calculate(final InputData inputData, final Overpayment overpayment) {
        BigDecimal interestPercent = inputData.getInterestPercent();
        log.debug("InterestPercent: [{}]", interestPercent);
        BigDecimal q = AmountsCalculationService.calculateQ(interestPercent);
        log.trace("Q: [{}]", q);

        BigDecimal residualAmount = inputData.getAmount();
        log.info("ResidualAmount: [{}]", residualAmount);

        BigDecimal interestAmount = AmountsCalculationService.calculateInterestAmount(residualAmount, interestPercent);
        log.info("InterestAmount: [{}]", interestAmount);
        BigDecimal rateAmount = calculateConstantRateAmount(q, interestAmount, residualAmount, inputData.getAmount(), inputData.getMonthsDuration());
        log.info("RateAmount: [{}]", rateAmount);
        BigDecimal capitalAmount = AmountsCalculationService.compareCapitalWithResidual(rateAmount.subtract(interestAmount), residualAmount);
        log.info("CapitalAmount: [{}]", capitalAmount);

        return new InstallmentAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    @Override
    public InstallmentAmounts calculate(final InputData inputData, final Overpayment overpayment, final Installment previousInstallment) {
        BigDecimal interestPercent = inputData.getInterestPercent();
        log.debug("InterestPercent: [{}]", interestPercent);
        BigDecimal q = AmountsCalculationService.calculateQ(interestPercent);
        log.trace("Q: [{}]", q);

        BigDecimal residualAmount = previousInstallment.mortgageResidual().residualAmount();
        log.info("ResidualAmount: [{}]", residualAmount);
        BigDecimal referenceAmount = previousInstallment.mortgageReference().referenceAmount();
        log.info("ReferenceAmount: [{}]", referenceAmount);
        BigDecimal referenceDuration = previousInstallment.mortgageReference().referenceDuration();
        log.info("ReferenceDuration: [{}]", referenceDuration);

        BigDecimal interestAmount = AmountsCalculationService.calculateInterestAmount(residualAmount, interestPercent);
        log.info("InterestAmount: [{}]", interestAmount);
        BigDecimal rateAmount = calculateConstantRateAmount(q, interestAmount, residualAmount, referenceAmount, referenceDuration);
        log.info("RateAmount: [{}]", rateAmount);
        BigDecimal capitalAmount = AmountsCalculationService.compareCapitalWithResidual(rateAmount.subtract(interestAmount), residualAmount);
        log.info("CapitalAmount: [{}]", capitalAmount);

        return new InstallmentAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private BigDecimal calculateConstantRateAmount(
        final BigDecimal q,
        final BigDecimal interestAmount,
        final BigDecimal residualAmount,
        final BigDecimal referenceAmount,
        final BigDecimal referenceDuration
    ) {
        BigDecimal rateAmount = referenceAmount
            .multiply(q.pow(referenceDuration.intValue()))
            .multiply(q.subtract(BigDecimal.ONE))
            .divide(q.pow(referenceDuration.intValue()).subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

        log.info("RateAmount: [{}]", rateAmount);
        return compareRateWithResidual(rateAmount, interestAmount, residualAmount);
    }

    private BigDecimal compareRateWithResidual(final BigDecimal rateAmount, final BigDecimal interestAmount, final BigDecimal residualAmount) {
        if (rateAmount.subtract(interestAmount).compareTo(residualAmount) >= 0) {
            return residualAmount.add(interestAmount);
        }
        return rateAmount;
    }

}
