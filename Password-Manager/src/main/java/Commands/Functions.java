package Commands;

import Database.Database;
import Database.SQLMethods;
import Main.Credentials;
import Main.Password;
import Main.PasswordCollection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                System.out.println("Edit an existing record");

                System.out.print(Commands.getList()[9]);
                System.out.print("-------------------------------");
                System.out.println("Print sorted list");

                System.out.print(Commands.getList()[10]);
                System.out.print("-------------------------------");
                System.out.println("Search for a password");

                System.out.print(Commands.getList()[11]);
                System.out.print("-------------------------------");
                System.out.println("Print registered users");

                System.out.print(Commands.getList()[12]);
                System.out.print("-------------------------------");
                System.out.println("Delete current account");
        }

        public static void print(ArrayList<Password> passwords) {
                System.out.println("");
                System.out.println("");

                System.out.println("  Username                        | Password                        | Site                            ");
                System.out.println("----------------------------------+---------------------------------+-------------------------------");

                for (int i = 0; i < passwords.size(); i++) {
                        System.out.println(i + 1 + ". " + passwords.get(i).getUsername() + addSpaces(passwords.get(i).getUsername(), "Username                        ") + "| " + passwords.get(i).getPassword() + addSpaces(passwords.get(i).getPassword(), "Password                         ") + "| " + passwords.get(i).getSite());
                }
        }

        private static String addSpaces(String input, String toReach) {
                int diff = toReach.length() - input.length();
                String output = "";

                for (int i = 0; i < diff - 1; i++) {
                        output = output + " ";
                }
                return output;
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

                if (!checkSite(info[3])) {
                        System.out.println("A password for this site already exists");
                        return null;
                }

                SQLMethods.INSERT(db.getStatement(), "Passwords", cols, info);
                PasswordCollection.refresh();
                System.out.println("\nRecord added");
                return info;
        }

        public static void delete() throws SQLException {
                Functions.print(PasswordCollection.getDBPasswords());

                Database db = new Database();

                System.out.print("\nSelect site to delete: ");
                String site = UserInput.getString();

                String query = "DELETE FROM Passwords WHERE Site = \'" + site + "\' AND User_ = \'" + Credentials.getUser() + "\'";
                db.getStatement().executeUpdate(query);
                System.out.println("Password deleted");
                PasswordCollection.refresh();
        }

        private static boolean checkSite(String site) throws SQLException {
                Database db = new Database();
                String query = "SELECT Site FROM Passwords WHERE Site = \'" + site + "\' AND User_ = \'" + Credentials.getUser() + "\'";
                ResultSet rs = db.getStatement().executeQuery(query);

                if (rs.next()) {
                        return false;
                }

                return true;
        }

        public static void users() throws SQLException {
                Database db = new Database();

                ArrayList<String> arr = SQLMethods.SELECT(db.getStatement(), "Login", "Username");

                for (int i = 0; i < arr.size(); i++) {
                        System.out.println(arr.get(i));
                }
        }

        public static void sort() {

                try {
                        PasswordCollection.refresh();
                } catch (SQLException ex) {
                        Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
                }

                Collections.sort(PasswordCollection.getPasswords());
                print(PasswordCollection.getPasswords());
        }

        public static void search() throws SQLException {
                System.out.print("Site to search: ");
                String site = UserInput.getString();

                try {
                        if (checkSite(site)) {
                                System.out.println("Site doesn't exist");
                                return;
                        }
                } catch (SQLException ex) {
                        Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (int i = 0; i < PasswordCollection.getDBPasswords().size(); i++) {
                        if (site.equals(PasswordCollection.getDBPasswords().get(i).getSite())) {
                                System.out.println("Username: " + PasswordCollection.getDBPasswords().get(i).getUsername());
                                System.out.println("Password: " + PasswordCollection.getDBPasswords().get(i).getPassword());
                                System.out.println("Site: " + PasswordCollection.getDBPasswords().get(i).getSite());
                                break;
                        }
                }
        }
        
        
        public static void deleteAccount(){
                System.out.println("Are you sure you want to delete this account permanently? (y/n)");
                System.out.print("> ");
                String choice = UserInput.getString();
                
                if(!choice.equals("y")) return;
                
                Statement s = null;
                try {
                        s = new Database().getStatement();
                } catch (SQLException ex) {
                        Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                        SQLMethods.DELETE(s, "Passwords", "User_", Credentials.getUser());
                } catch (SQLException ex) {
                        Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                        SQLMethods.DELETE(s, "Login", "Username", Credentials.getUser());
                } catch (SQLException ex) {
                        Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
                }
                Credentials.logout();
        }

        public static void edit(){
                ArrayList<Password> records = null;
                try {
                        records = PasswordCollection.getDBPasswords();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                
                print(records);
                System.out.println("");
                
                int choice = -1;
                do{
                        System.out.print("Select record to edit: ");
                        choice = UserInput.getInteger();
                } while (choice < 1 || choice > records.size());


                System.out.println("\nEdit info (press Enter to leave as is)\n");
                System.out.print("Username: ");
                String username = UserInput.getString();
                System.out.print("Password: ");
                String password = UserInput.getString();
                System.out.print("Site: ");
                String site = UserInput.getString();

                ArrayList<String> sites = null;
                try {
                        sites = SQLMethods.SELECT(new Database().getStatement(), "Passwords", "Site");
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                if(site != "" && site != "\n" && site != null && site != "\0" && !site.isEmpty()){
                        for(String str : sites){
                                if(str.equals(site)){
                                        System.out.println("This site already exists");
                                        return;
                                }
                        }
                } else site = records.get(choice-1).getSite();

                if(username == "" || username == "\n" || username == null || username == "\0" || username.isEmpty()) username = records.get(choice-1).getUsername(); 

                if(password == "" || password == "\n" || password == null || password == "\0" || password.isEmpty()) password = records.get(choice-1).getPassword();

                try {
                        SQLMethods.UPDATE(new Database().getStatement(), "Passwords", "Username", records.get(choice-1).getUsername(), username);
                        SQLMethods.UPDATE(new Database().getStatement(), "Passwords", "Password", records.get(choice-1).getPassword(), password);
                        SQLMethods.UPDATE(new Database().getStatement(), "Passwords", "Site", records.get(choice-1).getSite(), site);

                } catch (SQLException e) {
                        e.printStackTrace();
                }

                System.out.println("\nRecord edited successfully");
        }
}
