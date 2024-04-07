package com.company.service;

import com.company.model.InputData;
import com.company.model.Installment;

import java.util.List;

public interface InstallmentCalculationService {

    List<Installment> calculate(InputData inputData);
}
