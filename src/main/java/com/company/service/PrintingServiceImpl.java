package com.company.service;

import lombok.extern.slf4j.Slf4j;
import com.company.model.InputData;
import com.company.model.Overpayment;
import com.company.model.Rate;
import com.company.model.Summary;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
    public void printSchedule(final List<Rate> rates, final InputData inputData) {
        if (!inputData.isMortgagePrintPayoffsSchedule()) {
            return;
        }

        rates.stream()
                .filter(rate -> rate.rateNumber().remainder(BigDecimal.valueOf(inputData.getMortgageRateNumberToPrint())).equals(BigDecimal.ZERO))
                .forEach(rate -> {
                    loging(formatRateLine(rate));
                    if(AmountsCalculationService.YEAR.equals(rate.timePoint().month()))
                        loging(SEPARATOR.toString());
                });
        // loging(System.lineSeparator());
    }

    private static String formatRateLine(Rate rate) {
        return String.format(SCHEDULE_TABLE_FORMAT,
                RATE_LINE_KEYS.get(0), rate.rateNumber(),
                RATE_LINE_KEYS.get(1), rate.timePoint().year(),
                RATE_LINE_KEYS.get(2), rate.timePoint().month(),
                RATE_LINE_KEYS.get(3), rate.timePoint().date(),
                RATE_LINE_KEYS.get(4), rate.rateAmounts().rateAmount(),
                RATE_LINE_KEYS.get(5), rate.rateAmounts().interestAmount(),
                RATE_LINE_KEYS.get(6), rate.rateAmounts().capitalAmount(),
                RATE_LINE_KEYS.get(7), rate.rateAmounts().overpayment().amount(),
                RATE_LINE_KEYS.get(8), rate.mortgageResidual().residualAmount(),
                RATE_LINE_KEYS.get(9), rate.mortgageResidual().residualDuration()
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
