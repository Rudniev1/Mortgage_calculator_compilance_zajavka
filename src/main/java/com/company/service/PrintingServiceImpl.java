package com.company.service;

import lombok.extern.slf4j.Slf4j;
import com.company.model.InputData;
import com.company.model.Overpayment;
import com.company.model.Installment;
import com.company.model.Summary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class PrintingServiceImpl implements PrintingService {

    private static final Path RESULT_FILE_PATH = Paths.get("result.result");

    @Override
    public void printIntroInformation(InputData inputData) {

       String introInformation = INTRO_INFORMATION.formatted(
                inputData.getAmount(),
                inputData.getMonthsDuration(),
                inputData.getInterestToDisplay(),
                inputData.getOverpaymentStartMonth()
        );

       if(Optional.ofNullable(inputData.getOverpaymentSchema()).map(schema -> !schema.isEmpty()).orElse(false)){
           String overpaymentMessage = OVERPAYMENT_INFORMATION.formatted(
                   Overpayment.REDUCE_PERIOD.equals(inputData.getOverpaymentReduceWay())
                   ? OVERPAYMENT_REDUCE_PERIOD
                           : OVERPAYMENT_REDUCE_RATE,
                   prettyPrintOverpaymentSchema(inputData.getOverpaymentSchema())

           );
           introInformation += overpaymentMessage;
       }
         loging(introInformation);
    }

    private String prettyPrintOverpaymentSchema(Map<Integer, BigDecimal> schema) {

        return schema.entrySet().stream()
                .reduce(
                        new StringBuilder(),
                        (previous, next) -> previous.append(String.format(OVERPAYMENT_SCHEMA, next.getKey(), next.getValue())),
                        StringBuilder::append
                ).toString();
    }

    @Override
    public void printSchedule(final List<Installment> installments, final InputData inputData) {
        if (!inputData.isMortgagePrintPayoffsSchedule()) {
            return;
        }

        installments.stream()
                .filter(rate -> rate.installmentNumber().remainder(BigDecimal.valueOf(inputData.getMortgageRateNumberToPrint())).equals(BigDecimal.ZERO))
                .forEach(rate -> {
                    loging(formatRateLine(rate));
                    if(AmountsCalculationService.YEAR.equals(rate.timePoint().month()))
                        loging(SEPARATOR.toString());
                });
        // loging(System.lineSeparator());
    }

    private static String formatRateLine(Installment installment) {
        return String.format(SCHEDULE_TABLE_FORMAT,
                RATE_LINE_KEYS.get(0), installment.installmentNumber(),
                RATE_LINE_KEYS.get(1), installment.timePoint().year(),
                RATE_LINE_KEYS.get(2), installment.timePoint().month(),
                RATE_LINE_KEYS.get(3), installment.timePoint().date(),
                RATE_LINE_KEYS.get(4), installment.installmentAmounts().installmentAmount(),
                RATE_LINE_KEYS.get(5), installment.installmentAmounts().interestAmount(),
                RATE_LINE_KEYS.get(6), installment.installmentAmounts().capitalAmount(),
                RATE_LINE_KEYS.get(7), installment.installmentAmounts().overpayment().amount(),
                RATE_LINE_KEYS.get(8), installment.mortgageResidual().residualAmount(),
                RATE_LINE_KEYS.get(9), installment.mortgageResidual().residualDuration()
        );
    }

    @Override
    public void printSummary(final Summary summary) {
        loging(SUMMARY_INFORMATION.formatted(
                summary.interestSum(),
                summary.overpaymentProvisionSum().setScale(2, RoundingMode.HALF_UP),
                summary.totalLostSum().setScale(2, RoundingMode.HALF_UP),
                summary.totalCapital().setScale(2, RoundingMode.HALF_UP)
        ));
    }

    private void loging(String message) {
        System.out.println(message);
       // log.info(message);
        try {
            if(!Files.exists(RESULT_FILE_PATH))
                Files.createFile(RESULT_FILE_PATH);
            Files.writeString(RESULT_FILE_PATH, message, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing data file: " + e.getMessage());
        }
    }
}
