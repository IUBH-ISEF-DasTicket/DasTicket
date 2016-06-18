package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/*
* DBConnect
* Verbindung zur MySQL Datenbank
*
* <Klassenvariablen>
* public
*/ 

public class DBConnect     
{
    public static void main(String[] args) 
    {
        String DBServer = "Localhost";
        String DBInstance = "dasticket";
        String DBUser = "Admin";
        String DBPassword = "Admin123";

        // Initialisiere Treiber
         try 
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Treiber initialisiert");
        } 
         catch (Exception ex) 
        {
         System.out.println(ex.getMessage());
        }

        // Initialisiere Verbindung zur Datenbank
        Connection conn = null;
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://" + DBServer + "/" + DBInstance +
                   "?user=" + DBUser + "&password=" + DBPassword);

        System.out.println("DB verbunden");
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        // Statement Object

            
        // Insert Statement
         try 
         {
            Statement statement = conn.createStatement();

            // Data insert
            statement.executeUpdate("INSERT INTO usergroup " + "VALUES (1, 'Admins')");

            System.out.println("Insert Data");

         } 
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
        
    }

   }
