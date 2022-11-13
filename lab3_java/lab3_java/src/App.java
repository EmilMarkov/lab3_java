import controller.Controller;
import model.Model;
import model.transports.Transport;
import view.View;

import java.util.Properties;

public class App {
    public static Model model = new Model();
    public static View view = new View();
    public static Controller controller = new Controller(model, view);
    public static Properties prop;

    public static void main(String[] args) {
        boolean isRunning = false; // true - app is running, false - app is stopped

        App app = new App();
        app.run(model, view, controller, prop, isRunning);
    }

    public void run(Model model, View view, Controller controller, Properties prop, boolean isRunning) {
        isRunning = true;

        while (isRunning) {
            if (model.getGroup().equals(Model.Groups.developer.toString())) {
                 int function = View.menu(View.Menus.developer);

                 switch (function) {
                     case 1:
                         view.print_info(model);
                         break;
                     case 2:
                         controller.addTransport(Transport.Types.Car);
                         break;
                     case 3:
                         break;
                     case 4:
                         break;
                     case 5:
                         System.exit(0);
                         break;
                 }
            }
            else if (model.getGroup().equals(Model.Groups.user.toString())) {
                int function = View.menu(View.Menus.user);

                switch (function) {
                    case 1:
                        view.print_info(model);
                        break;
                    case 2:
                        controller.addTransport(Transport.Types.Car);
                        break;
                    case 3:
                        System.exit(0);
                        break;
                }
            }
        }
    }
}