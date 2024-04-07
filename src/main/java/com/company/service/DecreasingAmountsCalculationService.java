package com.company.service;

import com.company.model.InputData;
import com.company.model.Overpayment;
import com.company.model.Installment;
import com.company.model.InstallmentAmounts;

public interface DecreasingAmountsCalculationService {

    InstallmentAmounts calculate(InputData inputData, Overpayment overpayment);

    InstallmentAmounts calculate(InputData inputData, Overpayment overpayment, Installment previousInstallment);
}
