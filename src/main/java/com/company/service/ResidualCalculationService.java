package com.company.service;

import com.company.model.InputData;
import com.company.model.MortgageResidual;
import com.company.model.Installment;
import com.company.model.InstallmentAmounts;

public interface ResidualCalculationService {

    MortgageResidual calculate(InstallmentAmounts installmentAmounts, InputData inputData);

    MortgageResidual calculate(InstallmentAmounts installmentAmounts, final InputData inputData, Installment previousInstallment);

}
