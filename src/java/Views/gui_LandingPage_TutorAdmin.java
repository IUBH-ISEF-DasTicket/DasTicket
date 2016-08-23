/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


/**
 *
 * @author Julias Laptop
 */
@ManagedBean
@SessionScoped
public class gui_LandingPage_TutorAdmin  implements Serializable
{
    // Klassenvariablen
    String Username;   
    
    // Logout
        public String Logout()        
    {
        String Result;
        // Session beenden, damit User neu übergeben werden kann
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        Result = "Login Maske (JSF)?faces-redirect=true&includeViewParams=true";
        return Result;      
    }
    
     // Getter Methoden
    public String getUsername() 
    {
	return Username;
    }
    
    // Setter Methoden

        public void setUsername(String Username) 
    {
        this.Username = Username;
    }
    
}