package view;

import controller.Controller;
import model.Model;
import model.transports.Transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class View {
    public enum Menus {
        auth,
        developer,
        user
    }

    private static List<String> menu_auth = new ArrayList<String>(Arrays.asList(
            "1) Login",
            "2) Registration"
    ));

    private static List<String> menu_developer = new ArrayList<String>(Arrays.asList(
            "1) Print transport info",
            "2) Add new transport",
            "3) Debug",
            "4) Autotests",
            "5) Exit"
    ));

    private static List<String> menu_user = new ArrayList<String>(Arrays.asList(
            "1) Print transport info",
            "2) Add new transport",
            "3) Exit"
    ));

    private static List<String> transport_list = new ArrayList<String>(Arrays.asList(
            "1) All",
            "2) Car",
            "3) Buses",
            "4) Motocycles",
            "5) Trucks"
    ));

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    //text
    public static void send_text(String message) {
        System.out.println(message);
    }

    //info
    public static void send_info(String message) {
        System.out.println(ANSI_GREEN + "INFO" + " : " + message + ANSI_RESET);
    }

    //error
    public static void send_error(String message) {
        System.out.println(ANSI_RED + "ERROR" + " : " + message + ANSI_RESET);
    }

    //debug
    public static void send_debug(String message) {
        System.out.println(ANSI_BLUE + "DEBUG" + " : " + message + ANSI_RESET);
    }

    //warning
    public static void send_warning(String message) {
        System.out.println(ANSI_YELLOW + "WARNING" + " : " + message + ANSI_RESET);
    }

    public static int menu(Menus menu_type) {
        int user_choice = 0;

        switch (menu_type) {
            case auth:
                for (String item: menu_auth)
                    System.out.println(item);
                user_choice = Controller.inputInt(">> ", 1, 2);
                break;
            case developer:
                for (String item: menu_developer)
                    System.out.println(item);
                user_choice = Controller.inputInt(">> ", 1, 5);
                break;
            case user:
                for (String item: menu_user)
                    System.out.println(item);
                user_choice = Controller.inputInt(">> ", 1, 3);
                break;
        }

        return user_choice;
    }

    public void print_info(Model model) {
        String text = "";
        for (String item: transport_list)
            text += item + "\n";
        int user_choice = Controller.inputInt(text, 1, 5);

        switch (user_choice) {
            case 1:
                model.getTransport().print_info(Transport.Types.All);
                break;
            case 2:
                model.getTransport().print_info(Transport.Types.Car);
                break;
            case 3:
                model.getTransport().print_info(Transport.Types.Bus);
                break;
            case 4:
                model.getTransport().print_info(Transport.Types.Motocycle);
                break;
            case 5:
                model.getTransport().print_info(Transport.Types.Truck);
                break;
        }
    }
}
