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
public class Passwords {
        private static ArrayList<String[]> passwords;

        public static ArrayList<String[]> getPasswords() {
                return passwords;
        }

        public static void setPasswords(ArrayList<String[]> passwords) {
                Passwords.passwords = passwords;
        }
        
        public static ArrayList<String[]> getDBPasswords() throws SQLException {
                Database db = new Database();
                String[] cols = {"Username", "Password", "Site"};
                ArrayList<String[]> s = SQLMethods.SELECT(db.getStatement(), "Passwords", cols, "User_", Credentials.getUser());

                return s;
        }
        
        public static void refresh() throws SQLException{
                setPasswords(getDBPasswords());
        }
        
        
}
