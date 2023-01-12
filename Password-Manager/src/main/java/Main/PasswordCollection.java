/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Database.Database;
import Database.SQLMethods;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Konstantinos
 */
public class PasswordCollection{
        private static ArrayList<Password> passwords;

        public static ArrayList<Password> getPasswords() {
                return passwords;
        }

        public static void setPasswords(ArrayList<Password> passwords) {
                PasswordCollection.passwords = passwords;
        }
        
        public static ArrayList<Password> getDBPasswords() throws SQLException {
                Database db = new Database();
                String[] cols = {"Username", "Password", "Site"};
                ArrayList<String[]> s = SQLMethods.SELECT(db.getStatement(), "Passwords", cols, "User_", Credentials.getUser());
                ArrayList<Password> newPasswords = new ArrayList<>();
                for (int i = 0; i < s.size(); i++) {
                        newPasswords.add(new Password(s.get(i)));
                }
                
                return newPasswords;
        }
        
        public static void refresh() throws SQLException{
                setPasswords(getDBPasswords());
        }
}
