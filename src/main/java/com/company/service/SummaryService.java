package com.company.service;

import com.company.model.Rate;
import com.company.model.Summary;

import java.util.List;

@FunctionalInterface
public interface SummaryService {

    Summary calculateSummary(List<Rate> rates);
}
