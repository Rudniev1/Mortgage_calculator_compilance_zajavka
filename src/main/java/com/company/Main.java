package com.company;

import com.company.UI.MainMenu;
import com.company.model.InputData;
import com.company.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.company.configuration.CalculatorConfiguration;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Main {
    public static void main(String... args) throws UnsupportedEncodingException {

        ApplicationContext context = new AnnotationConfigApplicationContext(CalculatorConfiguration.class);
        InputDataRepository inputDataRepository = context.getBean(InputDataRepository.class);
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println("\nWitaj w kalkulatorze kredytów hipotecznych! ");

        do
         {
            MainMenu mainMenu = new MainMenu(inputDataRepository);
            mainMenu.displayMenu();
            InputData inputData = mainMenu.getInputData();
            if(inputData != null) {
                MortgageCalculationService mortgageCalculationService = context.getBean(MortgageCalculationService.class);
                mortgageCalculationService.calculate(inputData);
            }
        }
        while(true);
    }

//    private static void calculateCredit(InputData inputData) {
//
//        MortgageCalculationService mortgageCalculationService = context.getBean(MortgageCalculationService.class);
//        mortgageCalculationService.calculate(updatedInputData);
//    }

//    private class CalculatorCreator
//    {
//        private static MortgageCalculationService instance;
//
//        private CalculatorCreator(){
//        }
//
//        public static MortgageCalculationService getInstance(){
//            if(Objects.isNull(instance)){
//                instance = new MortgageCalculationServiceImpl(
//                        new RateCalculationServiceImpl(
//                                new TimePointCalculationServiceImpl(),
//                                new OverpaymentCalculationServiceImpl(),
//                                new AmountsCalculationServiceImpl(
//                                        new ConstantAmountsCalculationServiceImpl(),
//                                        new DecreasingAmountsCalculationServiceImpl()
//                                ),
//                                new ResidualCalculationServiceImpl(),
//                                new ReferenceCalculationServiceImpl()
//                        ),
//                        new PrintingServiceImpl(),
//                        SummaryServiceFactory.create()
//                );
//            }
//            return instance;
//        }
//    }
}