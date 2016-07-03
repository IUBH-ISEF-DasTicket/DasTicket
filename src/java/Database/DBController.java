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
        // Fehlerbehandlung
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
        // Verbindung schließeb
            connection.close();
            System.out.println("DB Verbindung geschlossen");

         } 
         // Fehlerbehandlung
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
    }
    
    public static void InsertData(String TableName, String Values)
    {
        // Connection aufbauen
        connection = InitConnection();
        
        // Variable für SQL Statement
        String SQLStatement;
        
        try 
         {
            // Statement vorbereiten
            statement = connection.createStatement();
            SQLStatement = "INSERT INTO " + TableName + " VALUES (" + Values + ")";
            
            // Statement ausführen
            statement.executeUpdate(SQLStatement);
            System.out.println("Insert Data");
            
         }
        // Fehlerbehandlung
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
         // Statement & Connection schließen
         finally
         {
             CloseStatement(statement);
             CloseConnection(connection);
         }
        
         
         
    }
    
    public static void UpdateData(String TableName, String Column, String Value, String Clause)
    {
        // Connection aufbauen
        connection = InitConnection(); 
        
        // Vatriable für SQL Statement
        String SQLStatement;

         try 
         {
            // SQL Statement vorbereiten
            SQLStatement = "Update " + TableName + " Set " + Column + "=" + "'" +  Value + "' " + Clause;
            statement = connection.createStatement();

            //  SQL Statement ausführen
            statement.executeUpdate(SQLStatement);
            System.out.println("Update Data");
            
         } 
         // Fehlerbehandlung
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
         // Statement & Connection schließen
         finally
         {
             CloseStatement(statement);
             CloseConnection(connection);
         }
        
    }
    
    public static void DeleteData(String TableName, String Clause)
    {
        // Connection aufbauen
        connection = InitConnection();
        
        // Vatriable für SQL Statement
        String SQLStatement;
        
         try 
         {
             // SQL Statement vorbereiten
            SQLStatement = "delete from " + TableName + " " + Clause;
            statement = connection.createStatement();           
            
            // Statement ausführen
            statement.executeUpdate(SQLStatement);
            System.out.println("Delete Data");

         } 
         // Fehlerbehandlung
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
         // Statement & Verbindung schließen
         finally
         {
             CloseStatement(statement);
             CloseConnection(connection);
         }
    }
    
    public static String GetData(String TableName, String Column, String Clause)
    {
        // Connection aufbauen
        connection = InitConnection();
        
        // Variablen für Statement
        String Result = null;
        String SQLStatement;
        
        try 
         {
            // SQL Statement vorbereiten
            SQLStatement = "select " + Column + " from " + TableName + " " + Clause;
            statement = connection.createStatement();
            
            // SQL Statement ausführen und in ResultSet schreiben
            ResultSet rs = statement.executeQuery(SQLStatement);
            
           
            // ResultSet auswerten
            while (rs.next())
                {
                    
                    Result = rs.getString(1);
                    System.out.println("Selected Data: " + Result);
                }
            
         } 
         // Fehlerbehandlung
         catch (SQLException ex) 
         {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
         }
         // Statement & Connection schließen
         finally
         {
             CloseStatement(statement);
             CloseConnection(connection);
         }
        
        // Ergebnis ausgeben
        
        return Result;
    }
    
    public static void CloseStatement(Statement statement)
    {
        // Statement schließen wenn nicht leer
        try 
            {
                if (statement != null) 
                { 
                  statement.close();
                }
            }
            // Fehlerbehandlung 
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

        // Data Insert
        InsertData("usergroup", "'1','Admins'");
        // Data Update
        UpdateData("usergroup", "name", "User", "WHERE name = 'Admins'");
        // Data Select
        GetData ("Usergroup", "name", "WHERE id=1");
        // Data delete
        //DeleteData ("usergroup", "");

    }

   }
