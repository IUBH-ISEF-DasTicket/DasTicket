/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;
import Database.DBController;


public class GUI_Login 

{
    
     public static void main(String[] args) 
    {
        DBController.InsertData("user", "'1','Admin','Admin123','admin@admin.de','1'");
        CheckLogin("admin","Admin123");
    }
     
    public static String CheckLogin (String Username, String UserPassword)
    {
        // Variablen
         String typedUsername;
         String typedUserPassword;
         String storedUserPassword;
         String storedUsername;
         String result;
        
        // Eingegebene Werte abfragen
        typedUsername = Username;
        typedUserPassword = UserPassword;
        
        // Benutzerdaten aus Datenbankabfragen
        storedUsername = DBController.GetData("user", "username", "where username= '" + typedUsername + "'");
        storedUserPassword = DBController.GetData("user", "Password", "where username= '" + typedUsername + "'");
        
        // Daten prüfen
        if (storedUsername.equalsIgnoreCase(typedUsername) && storedUserPassword.equals(typedUserPassword) )
        {
            result = "Passt!";
            System.out.println(result);
            
        } 
        else
        {
            result = "Benutzername oder Kennwort falsch!";
            System.out.println();
        }
       return (result);
    }
    
    public void NewLogin ()
    {
        
    }
    
    
    
}
