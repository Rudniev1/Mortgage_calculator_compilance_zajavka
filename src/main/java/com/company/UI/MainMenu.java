package com.company.UI;

import com.company.model.InputData;
import com.company.service.InputDataRepository;
import lombok.Getter;

import java.util.Scanner;

public class MainMenu {
    private final CreateInputData createInputData;
    @Getter
    private InputData inputData;
    private InputDataRepository inputDataRepository;
    Scanner sc;

    public MainMenu(InputDataRepository inputDataRepository) {
         createInputData = new CreateInputData();
         sc = new Scanner(System.in);
         this.inputDataRepository = inputDataRepository;
    }

    public void displayMenu() {
        System.out.println();
        System.out.println("Wybierz operację która Cię interesuje: ");
        System.out.println("1. Wylicz kredyt.");
        System.out.println("2. Wylicz kredyt z pliku csv.");
        System.out.println("3. Wyjście z aplikacji. ");

        chooseMenuOptions(sc.next());
    }

    private void chooseMenuOptions(String menuOptions){

        switch (menuOptions) {
            case "1" -> this.inputData = createInputData.createData();
            case "2" -> this.inputData = createInputData.readDataFromFile(inputDataRepository);
            case "3" -> System.exit(0);
            default -> System.out.println("Nieprawidłowa wartość. Wybierz ponownie!");
        }
    }

}
