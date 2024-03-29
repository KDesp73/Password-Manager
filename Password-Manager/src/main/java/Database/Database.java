package Database;

import java.sql.*;
import java.util.regex.Pattern;

public class Database {
        private static final String PACKAGE = "Database";
        private static final String DATABASE_NAME = "PM_Database";
        
        
        private static final String FILEPATH = System.getProperty("user.dir").replaceAll(Pattern.quote("\\"), "/") + "/src/main/java/" + PACKAGE.replaceAll(Pattern.quote("."), "/") + "/"+ DATABASE_NAME+".accdb";
        private static String url = "jdbc:ucanaccess://" + FILEPATH;
        private java.sql.Connection conn;
        private Statement statement;

        public Database() throws SQLException {
                try{
                        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                }catch(ClassNotFoundException e) {
                }


                conn = DriverManager.getConnection(url);
                statement = conn.createStatement();
        }

        public String getPath() {
                return FILEPATH;
        }

        public Statement getStatement() {
                return statement;
        }
        
        public String getName() {
                return DATABASE_NAME;
        }
}
