package com.company.service;

import com.company.model.Rate;

import java.math.BigDecimal;

@FunctionalInterface
public interface Function {

    BigDecimal calculate(Rate rate);
}
