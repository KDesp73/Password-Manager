package Commands;

import Database.Database;
import Database.SQLMethods;
import Main.Credentials;
import Main.Main;
import Main.Passwords;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Functions {

        public static void help() {

                System.out.println("");

                System.out.print(Commands.getList()[0]);
                System.out.print("-------------------------------");
                System.out.println("Prints this list");

                System.out.print(Commands.getList()[1]);
                System.out.print("-------------------------------");
                System.out.println("Create an account");

                System.out.print(Commands.getList()[2]);
                System.out.print("-------------------------------");
                System.out.println("Enter the app with your credentials");

                System.out.print(Commands.getList()[3]);
                System.out.print("-------------------------------");
                System.out.println("Switch accounts");

                System.out.print(Commands.getList()[4]);
                System.out.print("-------------------------------");
                System.out.println("Exit the program");

                System.out.print(Commands.getList()[5]);
                System.out.print("-------------------------------");
                System.out.println("Print saved records");

                System.out.print(Commands.getList()[6]);
                System.out.print("-------------------------------");
                System.out.println("Add a record");

                System.out.print(Commands.getList()[7]);
                System.out.print("-------------------------------");
                System.out.println("Delete a record");

                System.out.print(Commands.getList()[8]);
                System.out.print("-------------------------------");
                System.out.println("Print sorted list");

                System.out.print(Commands.getList()[9]);
                System.out.print("-------------------------------");
                System.out.println("Search records");
        }

        public static void print() throws SQLException {
                System.out.println("");
                System.out.println("");

                ArrayList<String[]> s = Passwords.getDBPasswords();

                System.out.println("  Username            |               Password                |              Site");
                System.out.println("------------------------------------------------------------------------------------");

                for (int i = 0; i < s.size(); i++) {
                        System.out.println(i + 1 + ".  " + s.get(i)[0] + "               |              " + s.get(i)[1] + "              |              " + s.get(i)[2]);
                }
        }

        

        public static String[] add() throws SQLException {
                Database db = new Database();
                String[] cols = {"User_", "Username", "Password", "Site"};
                System.out.println("Add a record");
                String[] info = new String[4];
                info[0] = Credentials.getUser();
                System.out.print("Username: ");
                info[1] = UserInput.getString();
                System.out.print("Password: ");
                info[2] = UserInput.getString();
                System.out.print("Site: ");
                info[3] = UserInput.getString();
                
                if(!checkSite(info[3])){
                        System.out.println("A password for this site already exists");
                        return null;
                }

                SQLMethods.INSERT(db.getStatement(), "Passwords", cols, info);
                Passwords.refresh();
                System.out.println("\nRecord added");
                return info;
        }

        public static void delete() throws SQLException{
                Functions.print();
                
                Database db = new Database();
                
                System.out.print("Select site to delete: ");
                String site = UserInput.getString();
                
                SQLMethods.DELETE(db.getStatement(), "Passwords", "Site", site);
                Passwords.refresh();
        }
        
        private static boolean checkSite(String site) throws SQLException{
                Database db = new Database();
                return !SQLMethods.valueExists(db.getStatement(), "Passwords", "Site", site);
        }
        
        public static void users() throws SQLException{
                Database db = new Database();
                
                ArrayList<String> arr = SQLMethods.SELECT(db.getStatement(), "Login", "Username");
                
                for (int i = 0; i < arr.size(); i++) {
                        System.out.println(arr.get(i));
                }
        }
        
        public static void sort(){
                
        }
        
       
}
