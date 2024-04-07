package com.company.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InstallmentType {
    CONSTANT("CONSTANT"),
    DECREASING("DECREASING");

    private final String value;
}
