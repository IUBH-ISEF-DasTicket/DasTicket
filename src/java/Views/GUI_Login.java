/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Views;

import java.io.Serializable;
import Database.DBController;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.faces.application.FacesMessage;


@ManagedBean
@SessionScoped


public class gUI_Login implements Serializable


{
    private static final long serialVersionUID = 1094801825228386363L;
    String typedUsername;
    String typedUserPassword;
    
    /* public static void main(String[] args) 
    {
        DBController.InsertData("user", "'1','Admin','Admin123','admin@admin.de','1'");
        // CheckLogin("admin","Admin123");
    }
     */
    public String CheckLogin ()
    {
        // Variablen
         String storedUserPassword;
         String storedUsername;
      
        
        // Eingegebene Werte abfragen
        // typedUsername = Username;
        // typedUserPassword = UserPassword;
        
        // Benutzerdaten aus Datenbankabfragen
        storedUsername = DBController.GetData("user", "username", "where username= '" + typedUsername + "'");
        storedUserPassword = DBController.GetData("user", "Password", "where username= '" + typedUsername + "'");
        

        // Daten prüfen
        if (storedUsername.equalsIgnoreCase(typedUsername) && storedUserPassword.equals(typedUserPassword) )
        {
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"Eingabe OK",
			"Alles in Ordnung"));
            
            return "Erfassungsmaske_JSF";
            
            
        } 
        else
        {
            
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"Falscher Benutzername oder Kennwort",
			"Bitte Benutzername und Kennwort prüfen"));
            
            return "login";
        }
       
        
    }
    
   
    public String getTypedUsername() 
    {
	return typedUsername;
    }
    public String getTypedUserPassword() 
    {
	return typedUserPassword;
    }
    public void setTypedUsername(String typedUsername) 
    {
        this.typedUsername = typedUsername;
    }
    public void setTypedUserPassword(String typedUserPassword) 
    {
        this.typedUserPassword = typedUserPassword;
    }  
    
}
