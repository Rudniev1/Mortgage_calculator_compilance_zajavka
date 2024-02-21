package com.company.model;

import lombok.*;

import java.math.BigDecimal;

@With
@Builder
public record RateAmounts(
        BigDecimal rateAmount,
        BigDecimal interestAmount,
        BigDecimal capitalAmount,
        Overpayment overpayment
) {
}
