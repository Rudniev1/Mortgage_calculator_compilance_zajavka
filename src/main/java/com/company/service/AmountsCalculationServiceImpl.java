package com.company.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.company.model.InputData;
import com.company.model.Overpayment;
import com.company.model.Installment;
import com.company.model.InstallmentAmounts;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AmountsCalculationServiceImpl implements AmountsCalculationService {

    private final ConstantAmountsCalculationService constantAmountsCalculationService;

    private final DecreasingAmountsCalculationService decreasingAmountsCalculationService;

    @Override
    public InstallmentAmounts calculate(final InputData inputData, final Overpayment overpayment) {
        return switch (inputData.getInstallmentType()) {
            case CONSTANT -> constantAmountsCalculationService.calculate(inputData, overpayment);
            case DECREASING -> decreasingAmountsCalculationService.calculate(inputData, overpayment);
        };
    }

    @Override
    public InstallmentAmounts calculate(final InputData inputData, final Overpayment overpayment, final Installment previousInstallment) {
        return switch (inputData.getInstallmentType()) {
            case CONSTANT -> constantAmountsCalculationService.calculate(inputData, overpayment, previousInstallment);
            case DECREASING -> decreasingAmountsCalculationService.calculate(inputData, overpayment, previousInstallment);
        };
    }


}
