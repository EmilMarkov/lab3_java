package controller;

import model.Logging;
import model.Model;
import model.transports.Transport;
import view.View;

import java.io.IOException;
import java.util.Scanner;

public class Controller {
    // <Fields>
    Model model;
    View view;
    // </Fields>


    // <Constructor>
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    // </Constructor>


    // <Input methods>
    public static void clear() {
        try
        {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
    public static String inputString(String msg) {
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            if (sc.hasNext())
            {
                clear();
                return sc.next();
            }
        }
    }
    public static int inputInt(String msg) {
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            if (sc.hasNextInt())
            {
                clear();
                return sc.nextInt();
            }
        }
    }
    public static int inputInt(String msg, int start, int end) {
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            if (sc.hasNextInt())
            {
                int temp = sc.nextInt();
                if (temp >= start && temp <= end) {
                    return temp;
                }
            }
        }
    }
    public static double inputDouble(String msg) {
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            if (sc.hasNextDouble())
            {
                clear();
                return sc.nextDouble();
            }
        }
    }
    public static boolean inputBoolean(String msg) {
        while (true)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            if (sc.hasNextBoolean())
            {
                clear();
                return sc.nextBoolean();
            }
        }
    }
    // </Input methods>


    // <Other methods>
    public void addTransport(Transport.Types type) {
        this.model.getTransport().add();

        try {
            Logging.log(this, "Add transport");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // </Other methods>
}