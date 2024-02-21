package com.company.service;

import com.company.model.InputData;
import com.company.model.MortgageResidual;
import com.company.model.Rate;
import com.company.model.RateAmounts;

public interface ResidualCalculationService {

    MortgageResidual calculate(RateAmounts rateAmounts, InputData inputData);

    MortgageResidual calculate(RateAmounts rateAmounts, final InputData inputData, Rate previousRate);

}
