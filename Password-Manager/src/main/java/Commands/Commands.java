package Commands;

import Main.Credentials;
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
                "users"
        };

        public static String[] getList() {
                return list;
        }

        public static String promt() {
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
                                        matchCommands(promt());
                                }
                                Credentials.insertCreds(Credentials.register());
                                break;
                        case "login":
                                Credentials.setEntered(Credentials.login());
                                break;
                        case "list":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(promt());
                                }
                                Functions.print();
                                break;
                        case "add":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(promt());
                                }
                                Functions.add();
                                break;
                        case "logout":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(promt());
                                }
                                Credentials.logout();
                                break;
                        case "delete":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(promt());
                                }
                               Functions.delete();
                                break;
                        case "users":
                                if (Credentials.getUser() == null) {
                                        System.out.println("Please login first");
                                        matchCommands(promt());
                                }
                                Functions.users();
                                break;
                        default:
                                matchCommands(promt());
                }
                matchCommands(promt());
        }

        
}
