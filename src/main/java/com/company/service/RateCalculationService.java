package com.company.service;

import com.company.model.InputData;
import com.company.model.Rate;

import java.util.List;

public interface RateCalculationService {

    List<Rate> calculate(InputData inputData);
}
