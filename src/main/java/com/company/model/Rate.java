package com.company.model;

import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;

@With
@Builder
public record Rate(
        BigDecimal rateNumber,
        TimePoint timePoint,
        RateAmounts rateAmounts,
        MortgageResidual mortgageResidual,
        MortgageReference mortgageReference
) {
}
