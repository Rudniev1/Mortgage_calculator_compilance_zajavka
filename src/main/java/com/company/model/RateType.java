package com.company.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RateType {
    CONSTANT("CONSTANT"),
    DECREASING("DECREASING");

    private final String value;
}
