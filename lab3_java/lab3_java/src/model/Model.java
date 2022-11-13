package model;

import controller.Controller;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.transports.Transport;
import view.View;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Model {
    public enum Groups {
        developer ("developer"),
        user ("user");

        private final String name;

        private Groups(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }

    // <Fields>
    private Transport transport = new Transport();
    final private static String usersFile = "users.ini";
    final private static String configFile = "config.ini";
    final private static String transportsXMLFile = "transports.xml";
    final private static String logFile = "log.txt";
    private static String login;
    private static String password;
    private static String group;
    private static boolean debug;
    private static boolean autotest;
    private Map<String, String> database = new HashMap<String, String>();
    private static Properties usersProp = new Properties();
    private static Properties configProp = new Properties();
    // </Fields>


    // <Constructors>
    public Model() {
        try {
            FileWriter fw = new FileWriter("app.log", true);

            fw.append("\n\t\t[NEW SESSION]\n\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Filling in Properties
        try {
            getConfigFile(usersFile, usersProp);
            getConfigFile(configFile, configProp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Filling in users
        loadUsers();
        loadCurrentUser();

        // Auth
        boolean isLogin = false;

        if (!loadUserConfig()) {
            int function = View.menu(View.Menus.auth);
            while (!isLogin) {
                if (function == 1) {
                    if (!login()) {
                        if (Controller.inputInt("1) Продолжить попытки ввода(по умолчанию)\n2) Регистрация\n>>> ") == 2) {
                            registration();
                            isLogin = true;
                        }
                    }
                    else {
                        isLogin = true;
                    }
                }
                else {
                    registration();
                    isLogin = true;
                }
            }
        }

        // Filling in Transport class
        transport = fromXmlToObject();
        if (transport == null) {
            transport = new Transport();
        }

        try {
            Logging.log(this, "Model inited");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // </Constructors>


    // <Getters>
    public Transport getTransport() { return transport; }
    public void setTransport(Transport transport) { this.transport = transport; }
    public static String getLogin() { return login; }
    public static String getPassword() { return password; }
    public Properties getProp() { return usersProp; }
    public String getGroup() { return group; }
    // </Getters>


    // <Authentication>
    public boolean login() {
        boolean isLogin = false;
        String loginTmp = "";
        String passwordTmp = "";

        while (true) {
            System.out.println(database);
            loginTmp = Controller.inputString("Auth:\n\tLogin: ");
            passwordTmp = Controller.inputString("\tPassword: ");

            for (String loginValue: database.keySet())
            {
                if (loginTmp.equals(loginValue))
                {
                    if (passwordTmp.equals(database.get(loginValue)))
                    {
                        login = loginTmp;
                        password = passwordTmp;
                        group = database.get(login+"__group");
                        isLogin = true;
                        saveConfigFile(configFile, configProp);
                    }
                }
            }

            if (!isLogin)
            {
                boolean answer = Controller.inputBoolean("User not found!\nDo you want to reg? true|false: ");
                if (answer) {
                    registration();
                    isLogin = true;
                }
            }
            else {
                break;
            }
        }

        try {
            Logging.log(this, "Login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return isLogin;
    }
    public void registration() {
        boolean loginFlag = false;
        boolean passwordFlag = false;

        while(!loginFlag && !passwordFlag)
        {
            String login_field = Controller.inputString("Enter login: ");
            String password_field = Controller.inputString("Enter password: ");
            String group_field = Controller.inputString("Enter group: ");

            if (!Groups.developer.equalsName(group_field) && !Groups.user.equalsName(group_field)) {
                System.out.println("Wrong group value!");
                continue;
            }

            if (!database.keySet().contains(login_field))
            {
                usersProp.put(login_field, password_field);
                usersProp.put(login_field+"__group", group_field);
                configProp.put(login_field, password_field);
                configProp.put(login_field+"__group", group_field);
                login = login_field;
                password = password_field;
                group = group_field;
                loginFlag = true;
                passwordFlag = true;
                saveConfigFile(usersFile, usersProp);
                saveConfigFile(configFile, configProp);
                loadUsers();
            }
            else {
                System.out.println("User already exist! Try again...");
            }
        }

        try {
            Logging.log(this, "Registration");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadUsers() {
        PrintWriter writer = new PrintWriter(System.out);

        try {
            for (final String name: usersProp.stringPropertyNames())
                database.put(name, usersProp.getProperty(name));

            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Logging.log(this, "Load users:\n\t\tProp: " + database.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadCurrentUser() {
        PrintWriter writer = new PrintWriter(System.out);

        try {
            for (final String name: configProp.stringPropertyNames()) {
                if (!name.contains("__group")) {
                    login = name;
                    password = configProp.getProperty(login);
                    group = configProp.getProperty(login+"__group");
                }
            }

            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Logging.log(this, "Load users:\n\t\tProp: " + database.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveUsers() {
        for (String loginValue: database.keySet())
        {
            usersProp.put(loginValue, database.get(loginValue));
        }
        saveConfigFile(usersFile, usersProp);

        try {
            Logging.log(this, "Save users");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean loadUserConfig() {
        PrintWriter writer = new PrintWriter(System.out);

        boolean isLoad = true;

        try {
            for (final String name: configProp.stringPropertyNames()) {
                if (!name.contains("__group"))
                {
                    login = name;
                    password = configProp.getProperty(login);
                    group = configProp.getProperty(name+"__group");
                }
            }

            writer.flush();

            if (configProp.isEmpty()) {
                isLoad = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Logging.log(this, "Load user config:\n\t\tProp: " + database.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return isLoad;
    }
    // </Authentication>


    // <Data>
    public Transport fromXmlToObject() {
        try {
            // creating a JAXBContext object - an entry point for JAXB
            JAXBContext context = JAXBContext.newInstance(Transport.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            if (new File(transportsXMLFile).length() <= 60) {
                return new Transport();
            }
            else {
                return (Transport)unmarshaller.unmarshal(new File(transportsXMLFile));
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        try {
            Logging.log(this, "From xml to object");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    public void fromObjectToXML() {
        try {
            JAXBContext context = JAXBContext.newInstance(Transport.class);
            Marshaller marshaller = context.createMarshaller();
            // setting a flag for readable XML output in JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // marshalling object in file
            marshaller.marshal(this.transport, new File(transportsXMLFile));
            File file = new File("transports.xml");
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        try {
            Logging.log(this, "From object to xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getConfigFile(String fileName, Properties prop) throws Exception {
        InputStream fs;
        try {
            File f = new File("././resources/"+ fileName);

            if (f.exists()) {
                fs = new FileInputStream(f);
            } else {
                fs = Model.class.getResourceAsStream(fileName);
            }

            prop.load(fs);
            View.send_info("Конфигурация загружена");
            assert fs != null;
            fs.close();
        } catch (FileNotFoundException e) {
            View.send_error("Конфигурационный файл не найден. Ошибка - " + e.getMessage());
        } catch (IOException e) {
            View.send_error("Конфигурационный файл не читается. Ошибка - " + e.getMessage());
        }

        try {
            Logging.log(this, "Get config file:\n\t\tFile: " + fileName + "\n\t\tProp: " + prop.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveConfigFile(String fileName, Properties prop) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("././resources/"+fileName);
            prop.store(out, null);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Logging.log(this, "Save config file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // </Data>


    // <Other methods>
    public void runDebug() {
        debug = true;

        try {
            Logging.log(this, "Debug");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void runAutotest() {
        autotest = true;

        try {
            Logging.log(this, "Autotests");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValue(Object object, String fieldName) {
        Class clazz = object.getClass();
        try {
            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch(Exception ex) {
            return null;  //нет такого поля
        }
    }
    // </Other methods>
}