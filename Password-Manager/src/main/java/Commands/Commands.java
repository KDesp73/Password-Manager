package Commands;

import Main.Credentials;
import Main.PasswordCollection;
import Main.UserInput;
import java.sql.SQLException;

public class Commands {

        private static String[] list = {
                "help",
                "register",
                "login",
                "logout",
                "exit",
                "list",
                "add",
                "delete",
                "sort",
                "search",
                "users",
                "delthis"
        };

        public static String[] getList() {
                return list;
        }

        public static String prompt() {
                System.out.println("");
                String command;

                do {
                        System.out.print("> ");
                        command = UserInput.getString();

                        if (!checkCommand(command)) {
                                System.out.println("Incorrect command\nCheck \'help\'");
                        }
                } while (!checkCommand(command));

                return command;
        }

        private static boolean checkCommand(String command) {
                for (String list1 : list) {
                        if (command.equals(list1)) {
                                return true;
                        }
                }
                return false;
        }

        public static void matchCommands(String command) throws SQLException {

                switch (command) {
                        case "exit":
                                System.out.println("Closing Program...");
                                System.exit(0);
                        case "help":
                                Functions.help();
                                break;
                        case "register":
                                if (Credentials.getUser() != null) {
                                        System.out.println("Please logout first");
                                        matchCommands(prompt());
                                }
                                Credentials.insertCreds(Credentials.register());
                                break;
                        case "login":
                                Credentials.setEntered(Credentials.login());
                                break;
                        case "list":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(prompt());
                                }
                                Functions.print(PasswordCollection.getDBPasswords());
                                break;
                        case "add":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(prompt());
                                }
                                Functions.add();
                                break;
                        case "logout":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(prompt());
                                }
                                Credentials.logout();
                                break;
                        case "delete":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(prompt());
                                }
                               Functions.delete();
                                break;
                        case "users":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(prompt());
                                }
                                Functions.users();
                                break;
                        case "sort":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(prompt());
                                }
                                Functions.sort();
                                break;
                        case "search":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(prompt());
                                }
                                Functions.search();
                                break;
                        case "delthis":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(prompt());
                                }
                                Functions.deleteAccount();
                                break;
                        default:
                                matchCommands(prompt());
                }
                matchCommands(prompt());
        }

        
}
