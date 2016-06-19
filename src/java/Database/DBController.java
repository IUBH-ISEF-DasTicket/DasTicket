package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/*
* DBController
* Controller für Datenbankaktionen
*   Connect,Insert, Update, Delete,Select,Close
*
* <Klassenvariablen>
* connction = DB Connector Objekt
* statement = SQL Statement Objekt
*
* <Sichtbarkeit>
* public
*/ 

public class DBController     

   
{
    // Klassenvariablen
    static Connection connection = null;
    static Statement statement = null;

    public static Connection InitConnection()
    {
        // Variablen für Verbindungsaufbau
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

        try 
        {
            connection = DriverManager.getConnection("jdbc:mysql://" + DBServer + "/" + DBInstance +
                   "?user=" + DBUser + "&password=" + DBPassword);

            System.out.println("DB verbunden");
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        return connection;
        
    }
    
    public static void CloseConnection(Connection connection)
    {
         try 
         {
            
            connection.close();
            System.out.println("DB Verbindung geschlossen");

         } 
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
    }
    
    public static void InsertData(Connection connection)
    {
        
         try 
         {
            
            statement = connection.createStatement();

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
         // Close Statement
         finally
         {
             CloseStatement(statement);
         }
        
         
         
    }
    
    public static void UpdateData(Connection connection)
    {
        
         // Update Statement


         try 
         {
            
            statement = connection.createStatement();

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
         finally
         {
             CloseStatement(statement);
         }
    }
    
    public static void DeleteData(Connection connection)
    {
        // Data delete
        
         try 
         {
            
            statement = connection.createStatement();

           
            statement.executeUpdate("delete from usergroup " + "where name='Test'");

            System.out.println("Delete Data");

         } 
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
         // Close Statement
         finally
         {
             CloseStatement(statement);
         }
    }
    
    public static String GetData(Connection connection)
    {
        String Result = null;
        // Data select
         try 
         {
            
            statement = connection.createStatement();
            //

            ResultSet rs = statement.executeQuery("select * from usergroup");
            while (rs.next())
                {
                    Result = rs.getString(2);
                    System.out.println("Selected Data: " + rs.getString(2));
                }
            

         } 
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
         finally
         {
             CloseStatement(statement);
         }
        return Result;
    }
    
    public static void CloseStatement(Statement statement)
    {
        try 
            {
                if (statement != null) 
                { 
                  statement.close();
                }
            }
             catch(SQLException ex)
            {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
    }
    
    // Testlauf
    public static void main(String[] args) 
    {
        // Init Connection
        connection = InitConnection();
        // Data Insert
        InsertData(connection);
        // Data Update
        UpdateData(connection);
        // Data Select
        GetData (connection);
        // Data delete
        DeleteData (connection);
        // Close Connection
        CloseConnection (connection);
        
    }

   }
