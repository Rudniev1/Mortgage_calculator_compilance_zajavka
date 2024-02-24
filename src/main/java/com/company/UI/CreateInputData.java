package com.company.UI;

import com.company.model.InputData;
import com.company.model.Overpayment;
import com.company.model.RateType;
import com.company.service.InputDataRepository;

import java.math.BigDecimal;
import java.util.*;

public class CreateInputData {

    BigDecimal amount;
    BigDecimal duration;
    private final Scanner sc = new Scanner(System.in);
    public static final String wrongChooseInfo = "Nieprawidłowa wartość. Wybierz ponownie!\n";


    public InputData createData() {
        InputData inputData;
        try {

            System.out.println("Jaka ma być kwota kredytu?");
            amount = sc.nextBigDecimal();
            System.out.println("Jaki okres trwania kredytu w miesiącach?");
            duration = sc.nextBigDecimal();
            System.out.println("Jak wysokie oprocentowanie WIBOR? (%)");
            BigDecimal wiborPrecent = sc.nextBigDecimal();
            System.out.println("Jak wysokie oprocentowanie marży banku? (%)");
            BigDecimal marginPrecent = sc.nextBigDecimal();

            inputData = InputData.defaultInputData()
                    .withAmount(amount)
                    .withMonthsDuration(duration)
                    .withWiborPercent(wiborPrecent)
                    .withMarginPercent(marginPrecent)
                    .withRateType(chooseRateType())
                    .withOverpaymentSchema(chooseIfOverpayment(amount, duration));
            if (!inputData.getOverpaymentSchema().equals(Collections.EMPTY_MAP))
                inputData.withOverpaymentReduceWay(chooseReduceWay());

        } catch (InputMismatchException e) {
            System.out.println("Nieprawidłowa wartość spróbuj wprowadzić dane jeszcze raz!");
            return null;
        }
        return inputData;
    }

    private RateType chooseRateType() {

        do {
            System.out.println("Raty malejące czy stałe? 1 - stałe, 2 - malejące:");
            int rateTypeOption = sc.nextInt();

            switch (rateTypeOption) {
                case 1 -> {
                    return RateType.CONSTANT;
                }
                case 2 -> {
                    return RateType.DECREASING;
                }
                default -> System.out.println(wrongChooseInfo);
            }
        }
        while (true);
    }

    private Map<Integer, BigDecimal> chooseIfOverpayment(BigDecimal amount, BigDecimal duration) {

        Map<Integer, BigDecimal> schema = new HashMap<>();
        int countOfOverpayments;
        int whenOverPayment;
        BigDecimal valueOverpayment;

        do {
            System.out.println("Czy chcesz wykonywać nadpłaty kredytu? 1 - tak, 2 - nie");
            int readOption = sc.nextInt();

            switch (readOption) {
                case 1 -> {
                    do {
                        System.out.println("Ile nadpłat chcesz wykonać? Jedna nadpłata na każdy miesiąc");
                        countOfOverpayments = sc.nextInt();

                        if (BigDecimal.valueOf(countOfOverpayments).compareTo(duration) > 0) {
                            System.out.println("Za duża liczba nadpłat, podaj mniejszą wartość");
                        } else break;
                    }
                    while (true);

                    for (int i = 0; i < countOfOverpayments; i++) {
                        do {
                            System.out.println("Nadpłata nr: " + (i + 1) + ". W której racie chcesz nadpłacić?");
                            whenOverPayment = sc.nextInt();
                            System.out.println("Jaka kwota nadpłaty");
                            valueOverpayment = sc.nextBigDecimal();

                            if (BigDecimal.valueOf(whenOverPayment).compareTo(duration) > 0 || whenOverPayment <= 0) {
                                System.out.println("Ten kredyt nie ma aż tylu rat spłaty, podaj inny numer raty");
                            } else if (valueOverpayment.compareTo(amount) > 0) {
                                System.out.println("Kwota nadpłaty przekracza kwotę całego kredytu, podaj mniejszą kwotę");
                            } else if (valueOverpayment.compareTo(BigDecimal.ZERO) < 0) {
                                System.out.println("Kwota mniejsza od zera, podaj wyższą kwotę");
                            } else break;
                        }
                        while (true);

                        schema.put(whenOverPayment, valueOverpayment);
                    }
                    return schema;
                }
                case 2 -> {
                    return new HashMap<>();
                }
                default -> System.out.println(wrongChooseInfo);
            }
        }
        while (true);
    }

    private String chooseReduceWay() {

        do {
            System.out.println("Czy przy nadpłacie kredytu chcesz zredukować długość kredytowania czy pomniejszyć raty?");
            System.out.println(" 1 - redukcja okresu kredytowania, 2 - redukcja wysokości rat");
            int reduceWay = sc.nextInt();

            switch (reduceWay) {
                case 1 -> {
                    return Overpayment.REDUCE_PERIOD;
                }
                case 2 -> {
                    return Overpayment.REDUCE_RATE;
                }
                default -> System.out.println(wrongChooseInfo);
            }
        }
        while (true);
    }

    public InputData readDataFromFile(InputDataRepository inputDataRepository){
    final var inputData = inputDataRepository.read();
        return Optional.of(inputData).get().orElse(null);
    }
}
