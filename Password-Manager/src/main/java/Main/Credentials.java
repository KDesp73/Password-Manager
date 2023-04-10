package Main;

import Database.Database;
import Database.SQLMethods;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Credentials {
        private static boolean entered = false;
        private static String user;

        public boolean isEntered() {
                return entered;
        }

        public static void setEntered(boolean e) {
                entered = e;
        }

        public static String getUser() {
                return user;
        }

        public static void setUser(String user) {
                Credentials.user = user;
        }
        
        
        
        
        
        public static String[] register() {
                System.out.print("Pick a username: ");
                String username = UserInput.getString();
                
                try {
                        if(SQLMethods.valueExists(new Database().getStatement(), "Login", "Username", username)){
                                System.out.println("Username already exists. Pick another one");
                                register();
                        }
                } catch (SQLException ex) {
                        Logger.getLogger(Credentials.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String passTemp;
                String password;
                do {
                        System.out.print("Pick a password: ");
                        passTemp = UserInput.getString();
                        System.out.print("Confirm password: ");
                        password = UserInput.getString();

                        System.out.println("");
                } while (!password.equals(passTemp));

                String[] s = {username, password};
                setUser(username);
                return s;
        }

        public static boolean login() throws SQLException {
                String username;
                String password;
                String[] s = new String[2];
                do {
                        System.out.print("Username: ");
                        username = UserInput.getString();
                        System.out.print("Password: ");
                        password = UserInput.getString();
                        
                        s[0] = username;
                        s[1] = password;
                        
                        if(!checkCreds(s)){
                                System.out.println("Username and/or Password are wrong");
                        }
                } while (!checkCreds(s));
                setUser(username);
                System.out.println("Current User: " + getUser());
                return checkCreds(s);
        }
        
        public static void logout(){
                System.out.println("\nLogging out...");
                setUser(null);
        }

        public static void insertCreds(String[] creds) throws SQLException {
                Database db = new Database();
                String[] cols = {"Username", "Password"};

                SQLMethods.INSERT(db.getStatement(), "Login", cols, creds);

        }

        private static ArrayList<String[]> getCreds() throws SQLException {
                Database db = new Database();
                String[] cols = {"Username", "Password"};
                ArrayList<String[]> creds = SQLMethods.SELECT(db.getStatement(), "Login", cols);
                
                return creds;
        }

        public static boolean checkCreds(String[] creds) throws SQLException {
                ArrayList<String[]> s = getCreds();
                for (int i = 0; i < s.size(); i++) {
                        if(Arrays.equals(s.get(i), creds)) return true;
                }
                return false;
        }

}
