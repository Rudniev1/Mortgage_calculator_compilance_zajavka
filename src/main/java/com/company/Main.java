package com.company;

import com.company.UI.MainMenu;
import com.company.model.InputData;
import com.company.service.*;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Main {
    public static void main(String... args) throws UnsupportedEncodingException {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println("\nWitaj w kalkulatorze kredytów hipotecznych! ");

        do
         {
            MainMenu mainMenu = new MainMenu();
            mainMenu.displayMenu();
            InputData inputData = mainMenu.getInputData();
            if(inputData != null) {
                calculateCredit(inputData);
            }
        }
        while(true);
    }

    private static void calculateCredit(InputData inputData) {

        CalculatorCreator.getInstance().calculate(inputData);
    }

    private class CalculatorCreator
    {
        private static MortgageCalculationService instance;

        private CalculatorCreator(){
        }

        public static MortgageCalculationService getInstance(){
            if(Objects.isNull(instance)){
                instance = new MortgageCalculationServiceImpl(
                        new RateCalculationServiceImpl(
                                new TimePointCalculationServiceImpl(),
                                new OverpaymentCalculationServiceImpl(),
                                new AmountsCalculationServiceImpl(
                                        new ConstantAmountsCalculationServiceImpl(),
                                        new DecreasingAmountsCalculationServiceImpl()
                                ),
                                new ResidualCalculationServiceImpl(),
                                new ReferenceCalculationServiceImpl()
                        ),
                        new PrintingServiceImpl(),
                        SummaryServiceFactory.create()
                );
            }
            return instance;
        }
    }
}