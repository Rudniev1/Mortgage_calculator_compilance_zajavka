package com.company.service;

import com.company.model.InputData;
import com.company.model.Installment;
import com.company.model.Summary;

import java.util.List;

public interface PrintingService {

    String SCHEDULE_TABLE_FORMAT =
        "%-4s %3s " +
        "%-4s %3s " +
        "%-7s %3s " +
        "%-7s %3s " +
        "%-4s %10s " +
        "%-7s %10s " +
        "%-7s %10s " +
        "%-7s %10s " +
        "%-8s %10s " +
        "%-8s %10s%n ";

    String INTRO_INFORMATION = """
            KWOTA KREDYTU: %s ZŁ
            OKRES KREDYTOWANIA: %s MIESIECY
            ODSETKI: %s%%
            MIESIAC ROZPOCZECIA NADPLAT: %s MIESIĄC:
            """;

    String OVERPAYMENT_INFORMATION = """
            %s
            SCHEMAT DOKONYWANIA NADPŁAT:
            %s
            """;

    String SUMMARY_INFORMATION = """
            SUMA ODSETEK: %s ZŁ
            PROWIZJA ZA NADPLATY: %s ZŁ
            SUMA STRAT: %s ZŁ
            SUMA KAPITAŁU: %s ZŁ
            """;

    List<String> RATE_LINE_KEYS = List.of(
    "NR: ",
    "ROK: ",
    "MIESIĄC: ",
    "DATA: ",
    "RATA: ",
    "ODSETKI: ",
    "KAPITAŁ: ",
    "NADPŁATA: ",
    "PKWT: ",
    "PMSC: "
    );

    String OVERPAYMENT_REDUCE_RATE = "NADPŁATA, ZMNIEJSZENIE RATY";
    String OVERPAYMENT_REDUCE_PERIOD = "NADPŁATA, SKROCENIE OKRESU";

    String OVERPAYMENT_SCHEMA = "MIESIĄC: %s, KWOTA: %s ZŁ%n";

    StringBuilder SEPARATOR = new StringBuilder(createSeparator('-', 180));

    @SuppressWarnings("SameParameterValue")
    private static String createSeparator(char sign, int length) {
        return String.valueOf(sign).repeat(Math.max(0, length)) + System.lineSeparator();
    }

    void printIntroInformation(InputData inputData);

    void printSchedule(List<Installment> installments, final InputData inputData);

    void printSummary(Summary summaryNoOverpayment);
}
