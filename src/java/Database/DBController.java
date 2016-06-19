package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/*
* DBController
* Verbindung zur MySQL Datenbank
*
* <Klassenvariablen>
* public
*/ 

public class DBController     

        
{
        
    public static Connection InitConnection()
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
        
        return conn;
        
    }
    
    public void CloseConnection()
    {
        
    }
    
    public void UpdateData()
    {
        
    }
    
    public void InsertData()
    {
        
    }
    
    public void DeleteData()
    {
        
    }
    
    public String GetData()
    {
        String Result = null;
        return Result;
    }
    
    
    
    public static void main(String[] args) 
    {

        Connection conn = InitConnection();
        
        Statement statement = null;
        // Insert Statement
         try 
         {
            
            statement = conn.createStatement();

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
        
         // Update Statement


         try 
         {
            
            statement = conn.createStatement();

            // Data insert
            statement.executeUpdate("Update usergroup " + "set name='Test'");

            System.out.println("Update Data");

         } 
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
         
        
         // Data select
         try 
         {
            
            statement = conn.createStatement();
            //

            ResultSet rs = statement.executeQuery("select * from usergroup");
            while (rs.next())
                {

                    System.out.println("Selected Data: " + rs.getString(2));
                }
            

         } 
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
         
         // Data delete

         try 
         {
            
            statement = conn.createStatement();

           
            statement.executeUpdate("delete from usergroup " + "where name='Test'");

            System.out.println("Delete Data");

         } 
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
        
         // Close
         
         try 
         {
            
            conn.close();
            System.out.println("DB Verbindung geschlossen");

         } 
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
         
    }

   }
