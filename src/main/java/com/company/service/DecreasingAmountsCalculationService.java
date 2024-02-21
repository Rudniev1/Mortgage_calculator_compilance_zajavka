package com.company.service;

import com.company.model.InputData;
import com.company.model.Overpayment;
import com.company.model.Rate;
import com.company.model.RateAmounts;

public interface DecreasingAmountsCalculationService {

    RateAmounts calculate(InputData inputData, Overpayment overpayment);

    RateAmounts calculate(InputData inputData, Overpayment overpayment, Rate previousRate);
}
