package com.company.model;

import lombok.Builder;

import java.math.BigDecimal;


@Builder
public record MortgageReference(
        BigDecimal referenceAmount,
        BigDecimal referenceDuration
) {
}
