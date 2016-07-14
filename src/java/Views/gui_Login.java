package Views;

/*
* gui_Login
* Funktionalität für Login
*
* <Klassenvariablen>
* typedUsername = Eingegebener Benutzer
* typedUserpassword = Eingegebenes Kennwort
*
* <Sichtbarkeit>
* public
*/ 

import java.io.Serializable;
import Database.DBController;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

// Managed Bean 
@ManagedBean
@RequestScoped


public class gui_Login implements Serializable


{
    String typedUsername;
    String typedUserPassword;
    
    public String CheckLogin ()
    {
        // Variablen
         String storedUserPassword;
         String storedUsername;
     
        
        // Benutzerdaten aus Datenbankabfragen
        storedUsername = DBController.GetData("user", "username", "where username= '" + typedUsername + "'");
        storedUserPassword = DBController.GetData("user", "Password", "where username= '" + typedUsername + "'");
        

        // Daten prüfen
        // Falscher Benutzername
        if (storedUsername == null)
        {
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"Falscher Benutzername oder Kennwort",
			""));
            
            return "Login Maske (JSF)";
        }
        
        // Eingabe korrekt
        if (storedUsername.equalsIgnoreCase(typedUsername) && storedUserPassword.equals(typedUserPassword) )
        {
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"Eingabe OK",
			""));
            
            return "Erfassungsmaske_JSF?faces-redirect=true&includeViewParams=true";
            
            
        } 
        // Falsche Kombination Benutzer & Kennwort
        else
        {
            
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"Falscher Benutzername oder Kennwort",
			""));
            
            return "Login Maske (JSF)";
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
