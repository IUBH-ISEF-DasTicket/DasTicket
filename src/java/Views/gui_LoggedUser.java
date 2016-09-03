/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Database.DBController;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.faces.model.SelectItem;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author DIANE
 */

// Managed Bean 
@ManagedBean
//@SessionScoped
@ViewScoped

public class gui_LoggedUser {

String loggedUser;
String userID;

@PostConstruct
    public void Init()        
    {                                                   
        Map<String, String> params = FacesContext.getCurrentInstance().
                   getExternalContext().getRequestParameterMap();
                        
        loggedUser = params.get("Username");
        userID = params.get("UserID");                         
    }
    
    public String getLoggedUser ()
    {
        return loggedUser;
    }
    
    public void setLoggedUser (String LoggedUser) 
    {
        this.loggedUser = LoggedUser;
    }
    
    public String getUserID ()
    {
        return userID;
    }
    
    public void setUserID (String UserID) 
    {
        this.userID = UserID;
    }
    
}
