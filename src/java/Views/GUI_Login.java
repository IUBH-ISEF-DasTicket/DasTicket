/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Database.DBController;


public class GUI_Login 

{
    public void CheckLogin (char Username, char UserPassword)
    {
        // Variablen
         char typedUsername;
         char typedUserPassword;
         char storedUserPassword;
         char storedUsername;
        
        // Eingegebene Werte abfragen
        typedUsername = Username;
        typedUserPassword = UserPassword;
        
        // Benutzerdaten aus Datenbankabfragen
        
        // storedUsername = DBController.GetData(connection, TableName, Column, Clause)GetData();
        
    }
    
    public void NewLogin ()
    {
        
    }
    
    
    
}
