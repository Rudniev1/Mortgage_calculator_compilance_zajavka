package com.company.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.company.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InstallmentCalculationServiceImpl implements InstallmentCalculationService {

    private final TimePointCalculationService timePointCalculationService;
    private final OverpaymentCalculationService overpaymentCalculationService;
    private final AmountsCalculationService amountsCalculationService;
    private final ResidualCalculationService residualCalculationService;
    private final ReferenceCalculationService referenceCalculationService;

    @Override
    public List<Installment> calculate(final InputData inputData) {
        List<Installment> installmentList = new ArrayList<>();

        BigDecimal rateNumber = BigDecimal.ONE;

        Installment zeroInstallment = calculateZeroRate(rateNumber, inputData);

        Installment previousInstallment = zeroInstallment;
        installmentList.add(zeroInstallment);

        for (BigDecimal i = rateNumber.add(BigDecimal.ONE); i.compareTo(inputData.getMonthsDuration()) <= 0; i = i.add(BigDecimal.ONE)) {
            Installment nextInstallment = calculateNextRate(i, inputData, previousInstallment);
            previousInstallment = nextInstallment;
            installmentList.add(nextInstallment);
            log.trace("Calculating next rate: [{}]", nextInstallment);
            if (BigDecimal.ZERO.equals(nextInstallment.mortgageResidual().residualAmount().setScale(0, RoundingMode.HALF_UP))) {
                break;
            }
        }

        return installmentList;
    }

    private Installment calculateZeroRate(final BigDecimal rateNumber, final InputData inputData) {
        TimePoint timePoint = timePointCalculationService.calculate(rateNumber, inputData);
        Overpayment overpayment = overpaymentCalculationService.calculate(rateNumber, inputData);
        InstallmentAmounts installmentAmounts = amountsCalculationService.calculate(inputData, overpayment);
        MortgageResidual mortgageResidual = residualCalculationService.calculate(installmentAmounts, inputData);
        MortgageReference mortgageReference = referenceCalculationService.calculate(installmentAmounts, inputData);

        return new Installment(rateNumber, timePoint, installmentAmounts, mortgageResidual, mortgageReference);
    }

    private Installment calculateNextRate(final BigDecimal rateNumber, final InputData inputData, final Installment previousInstallment) {
        TimePoint timepoint = timePointCalculationService.calculate(rateNumber, previousInstallment);
        Overpayment overpayment = overpaymentCalculationService.calculate(rateNumber, inputData);
        InstallmentAmounts installmentAmounts = amountsCalculationService.calculate(inputData, overpayment, previousInstallment);
        MortgageResidual mortgageResidual = residualCalculationService.calculate(installmentAmounts, inputData, previousInstallment);
        MortgageReference mortgageReference = referenceCalculationService.calculate(installmentAmounts, inputData, previousInstallment);

        return new Installment(rateNumber, timepoint, installmentAmounts, mortgageResidual, mortgageReference);
    }

}
