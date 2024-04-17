package com.company.service;

import com.company.model.InputData;
import com.company.model.MortgageReference;
import com.company.model.Installment;
import com.company.model.InstallmentAmounts;

public interface ReferenceCalculationService {

    MortgageReference calculate(InstallmentAmounts installmentAmounts, InputData inputData);

    MortgageReference calculate(InstallmentAmounts installmentAmounts, final InputData inputData, Installment previousInstallment);

}
