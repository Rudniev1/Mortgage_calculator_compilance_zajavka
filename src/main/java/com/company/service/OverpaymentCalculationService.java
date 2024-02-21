package com.company.service;

import com.company.model.InputData;
import com.company.model.Overpayment;

import java.math.BigDecimal;

public interface OverpaymentCalculationService {

    Overpayment calculate(final BigDecimal rateNumber, final InputData inputData);
}
