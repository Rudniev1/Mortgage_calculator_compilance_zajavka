package com.company.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.company.model.InputData;
import com.company.model.Installment;
import com.company.model.Summary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MortgageCalculationServiceImpl implements MortgageCalculationService {

    private final InstallmentCalculationService installmentCalculationService;

    private final PrintingService printingService;

    private final SummaryService summaryService;

    @Override
    public void calculate(InputData inputData) {
        printingService.printIntroInformation(inputData);

        List<Installment> installments = installmentCalculationService.calculate(inputData);
        installments.forEach(element -> log.debug("Rate: [{}]", element));
        Summary summary = summaryService.calculateSummary(installments);

        printingService.printSummary(summary);
        printingService.printSchedule(installments, inputData);
    }

}
