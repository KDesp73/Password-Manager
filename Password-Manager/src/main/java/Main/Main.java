package Main;

import Commands.Functions;
import Commands.Commands;
import Database.Database;
import Database.SQLMethods;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
        public static void main(String[] args) throws SQLException {
                PasswordCollection.refresh();
                Commands.matchCommands(Commands.promt());

        }
}
