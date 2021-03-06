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
         String[][] storedUserPassword;
         String[][] storedUsername;
         String[][] storedUserGroup;
         String[][] storedUserState;
     
        
        // Benutzerdaten aus Datenbankabfragen
        storedUsername = DBController.GetData("user", "username", "where username= '" + typedUsername + "'");
        storedUserPassword = DBController.GetData("user", "Password", "where username= '" + typedUsername + "'");
        storedUserState = DBController.GetData("user", "state", "where username= '" + typedUsername + "'");
        

        // Daten prüfen
        // Falscher Benutzername
        if (storedUsername.length == 0)
        {
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"Falscher Benutzername oder Kennwort",
			""));
            
            return "Login Maske (JSF)";
        }
        
        // User inaktiv (funktioniert nicht....)
        
        if (storedUserState[0][0].equalsIgnoreCase("0"))
        {
             FacesContext.getCurrentInstance().addMessage(
             null,new FacesMessage(FacesMessage.SEVERITY_WARN,
	 		"Ihr Nutzername wurde deaktiviert",
	 		""));
             
             return "Login Maske (JSF)";
        }
                
        // Eingabe korrekt
        if (storedUsername[0][0].equalsIgnoreCase(typedUsername) && storedUserPassword[0][0].equals(typedUserPassword) )
        {
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"Eingabe OK",
			""));
            
            storedUserGroup = DBController.GetData("user", "id_usergroup", "where username= '" + typedUsername + "'");
            
            if (storedUserGroup[0][0].equalsIgnoreCase("1"))
            {
                return "Landing Page_Tutor?faces-redirect=true&includeViewParams=true";                
            } 
            if (storedUserGroup[0][0].equalsIgnoreCase("2"))
            {
                return "Landing Page?faces-redirect=true&includeViewParams=true";
            }
              
            if (storedUserGroup[0][0].equalsIgnoreCase("3"))
            {
                return "Landing Page_Admin?faces-redirect=true&includeViewParams=true";                
            } 
            
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
       
    return "";   
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
