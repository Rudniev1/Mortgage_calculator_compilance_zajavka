package com.company.model;

import lombok.*;

import java.math.BigDecimal;

@With
@Builder
public record InstallmentAmounts(
        BigDecimal installmentAmount,
        BigDecimal interestAmount,
        BigDecimal capitalAmount,
        Overpayment overpayment
) {
}
