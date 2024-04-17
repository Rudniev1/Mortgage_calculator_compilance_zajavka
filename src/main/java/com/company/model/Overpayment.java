package com.company.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Overpayment(
        BigDecimal amount,
        BigDecimal provisionAmount
) {

    public static final String REDUCE_RATE = "REDUCE_RATE";

    public static final String REDUCE_PERIOD = "REDUCE_PERIOD";

}
