package Donnees;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connexion
{
    private Connection cnx;
    private Statement st;
    
    public Connexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            cnx = DriverManager.getConnection("jdbc:mysql://localhost/ds_jee", "root", "");
            st = cnx.createStatement();
        } catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
        }
    }
    
    public ResultSet selectQuery(String sql) {
        try {
            return st.executeQuery(sql);
        } catch(SQLException e) {
            return null;
        }
    }
    public void updateQuery(String sql) throws SQLException {
            cnx.setAutoCommit(false);
            st.executeUpdate(sql);
            cnx.setAutoCommit(true);
    }
    
    public void closeConnection() {
        try {
            st.close();
            cnx.close();
        } catch(SQLException e) {
        }
    }
}
