package com.company.service;

import com.company.model.InputData;
import com.company.model.Installment;
import com.company.model.TimePoint;

import java.math.BigDecimal;

public interface TimePointCalculationService {

    TimePoint calculate(final BigDecimal rateNumber, final InputData inputData);

    TimePoint calculate(BigDecimal rateNumber, Installment previousInstallment);

}
