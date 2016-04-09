package controller;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OracleJDBC {
    
    public static final String dbDriver = "oracle.jdbc.driver.OracleDriver";
    public static final String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static final String dbUsername = "samnang";
    public static final String dbPassword = "suon";
    
    public static void main(String[] argv) {
        try {
            System.out.println("-------- Oracle JDBC Connection Testing ------");
            try {
                Class.forName(dbDriver);
            } catch (ClassNotFoundException e) {
                System.out.println("Where is your Oracle JDBC Driver ?");
                e.printStackTrace();
                return;
            }
            System.out.println("Oracle JDBC Driver Registered!");
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                return;
            }
            if (connection != null) {
                System.out.println("You made it, take control of your database now !");
            } else {
                System.out.println("Failed to make connection!");
            }
            // création d'une connexion           
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            
            System.out.println((char)10 + "----- Oracle JDBC Insert Statement -----");
            String insertString = "INSERT INTO Chanson(id, titre, rhythme, type) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertString);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Sror-em Paula");
            preparedStatement.setString(3, "Roamvong");
            preparedStatement.setString(4, "HF");
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new song was inserted successfully!");
            }
            
            System.out.println((char)10 + "----- Oracle JDBC Select Statement -----");
            String sql = "SELECT * FROM Chanson";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);                        
            while (result.next()){
                int id = result.getInt("id");
                String titre = result.getString("titre");
                String rhythme = result.getString("rhythme");
                System.out.println(id + " :: " + titre + " :: " + rhythme);
            }
            
            System.out.println((char)10 + "----- Oracle JDBC Update Statement -----");
            String updateString = "UPDATE Chanson SET rhythme = ? WHERE id = ?";
            PreparedStatement updatePreparedStatement = conn.prepareStatement(updateString);
            updatePreparedStatement.setString(1, "Saravan");
            updatePreparedStatement.setInt(2, 1);
            int rowsUpdate = updatePreparedStatement.executeUpdate();
            if (rowsUpdate > 0) {
                System.out.println("A song was updated successfully!");
            }
            
            System.out.println((char)10 + "----- Oracle JDBC Select Statement -----");
            String selectSql = "SELECT * FROM Chanson WHERE rhythme = ?";
            PreparedStatement selectPreparedStatement = conn.prepareStatement(selectSql);
            selectPreparedStatement.setString(1, "Saravan");
            ResultSet selectResult = selectPreparedStatement.executeQuery();               
            while (selectResult.next()){
                int id = selectResult.getInt("id");
                String titre = selectResult.getString("titre");
                String rhythme = selectResult.getString("rhythme");
                System.out.println(id + " :: " + titre + " :: " + rhythme);
            }
            
            System.out.println((char)10 + "----- Oracle JDBC Delete Statement -----");
            String deleteString = "DELETE FROM Chanson WHERE id = ?";
            PreparedStatement deletePreparedStatement = conn.prepareStatement(deleteString);
            deletePreparedStatement.setInt(1, 1);
            int rowsDeleted = deletePreparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A song was deleted successfully!");
            }                   
            // Fermeture de la connexion
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
}