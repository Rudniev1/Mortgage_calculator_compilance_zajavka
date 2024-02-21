package com.company.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.company.model.InputData;
import com.company.model.Overpayment;
import com.company.model.Rate;
import com.company.model.RateAmounts;

import static com.company.model.RateType.CONSTANT;
import static com.company.model.RateType.DECREASING;

@Slf4j
@RequiredArgsConstructor
public class AmountsCalculationServiceImpl implements AmountsCalculationService {

    private final ConstantAmountsCalculationService constantAmountsCalculationService;

    private final DecreasingAmountsCalculationService decreasingAmountsCalculationService;

    @Override
    public RateAmounts calculate(final InputData inputData, final Overpayment overpayment) {
        return switch (inputData.getRateType()) {
            case CONSTANT -> constantAmountsCalculationService.calculate(inputData, overpayment);
            case DECREASING -> decreasingAmountsCalculationService.calculate(inputData, overpayment);
        };
    }

    @Override
    public RateAmounts calculate(final InputData inputData, final Overpayment overpayment, final Rate previousRate) {
        return switch (inputData.getRateType()) {
            case CONSTANT -> constantAmountsCalculationService.calculate(inputData, overpayment, previousRate);
            case DECREASING -> decreasingAmountsCalculationService.calculate(inputData, overpayment, previousRate);
        };
    }


}
