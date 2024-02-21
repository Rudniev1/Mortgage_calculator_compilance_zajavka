package com.company.UI;

import com.company.model.InputData;
import lombok.Getter;

import java.util.Scanner;

public class MainMenu {
    private final CreateInputData createInputData;
    @Getter
    private InputData inputData;
    Scanner sc;

    public MainMenu() {
         createInputData = new CreateInputData();
         sc = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println();
        System.out.println("Wybierz operację która Cię interesuje: ");
        System.out.println("1. Wylicz kredyt.");
        System.out.println("2. Wyjście z aplikacji. ");

        chooseMenuOptions(sc.next());
    }

    private void chooseMenuOptions(String menuOptions){

        switch (menuOptions) {
            case "1" -> this.inputData = createInputData.createData();
            case "2" -> System.exit(0);
            default -> System.out.println("Nieprawidłowa wartość. Wybierz ponownie!");
        }
    }

}
