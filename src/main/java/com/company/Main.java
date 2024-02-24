package com.company;

import com.company.UI.MainMenu;
import com.company.model.InputData;
import com.company.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.company.configuration.CalculatorConfiguration;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String... args) {

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

}