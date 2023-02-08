package Main;

import Commands.Commands;
import java.sql.SQLException;

public class Main {
        public static void main(String[] args) throws SQLException {
                PasswordCollection.refresh();
                Commands.matchCommands(Commands.promt());
                
        }
}
