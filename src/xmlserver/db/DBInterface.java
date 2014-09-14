/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlserver.db;

/**
 *
 * @author Duru Dumebi Julian
 */

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.rowset.JdbcRowSet;
import com.sun.rowset.JdbcRowSetImpl;

import java.util.List;
import java.util.Arrays;

public class DBInterface {
    
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL_BASE = "jdbc:mysql://localhost/";
    
    private final static List<String> dmls = Arrays.asList(new String[]{
        "insert", "update", "delete", "create", "alter", "grant", "drop"
    });
    
    static {
        try{
            Class.forName(DRIVER).newInstance();
        }
        catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){
            e.printStackTrace();
        }        
    }
    
    public static boolean isDML(String q){
        if (q.indexOf(" ") == -1)
            return dmls.contains(q);
        else
            return dmls.contains(q.substring(0, q.indexOf(" ")));
    }
    
    protected static JdbcRowSet getResult(String db, String query) throws SQLException{
        JdbcRowSet rowset = new JdbcRowSetImpl();
        rowset.setUsername(USERNAME);
        rowset.setPassword(PASSWORD);
        rowset.setUrl(DB_URL_BASE + db);
        rowset.setCommand(query);
        
        rowset.execute();
        
        return rowset;
    }
    
    protected static int getUpdate(String db, String query) throws SQLException{
        Connection connection = DriverManager.getConnection(DB_URL_BASE + db, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);
    }
    
    protected static boolean getExecution(String db, String query) throws SQLException{
        Connection connection = DriverManager.getConnection(DB_URL_BASE + db, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        return statement.execute(query);
    }
    
//    
//    protected static void doUpdate(String db, String sql) throws SQLException{
//        JdbcRowSet rowset = new JdbcRowSetImpl();
//        rowset.setUsername(USERNAME);
//        rowset.setPassword(PASSWORD);
//        rowset.setUrl(DB_URL_BASE + db);
//        rowset.setCommand(sql);
//        
//        rowset.execute();
//    }
    
}
