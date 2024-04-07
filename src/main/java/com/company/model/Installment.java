package com.company.model;

import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;

@With
@Builder
public record Installment(
        BigDecimal installmentNumber,
        TimePoint timePoint,
        InstallmentAmounts installmentAmounts,
        MortgageResidual mortgageResidual,
        MortgageReference mortgageReference
) {
}
