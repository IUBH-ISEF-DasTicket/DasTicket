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
import javax.faces.bean.ManagedProperty;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
/**
 *
 * @author DIANE
 */

// Managed Bean 
@ManagedBean
// @SessionScoped
@RequestScoped

public class gui_UserErfassen {
    
    String Username;
    String Name;
    String Email;
    String Role;
    String Status;
    String Password;
    List<SelectItem> RolesList;
    List<SelectItem> StatusList;
    String[][] NotAttachedCourses; 
    Boolean ShowCourses = true;
 
    private Map<String, Boolean> NotAttached = new HashMap<String, Boolean>();      
    
    @ManagedProperty(value="#{gui_LoggedUser.loggedUser}")
    private String loggedUser;
    
    @PostConstruct
    public void Init()        
    {                                                   
        Username = loggedUser;                
        
        // Rolle        
        RolesList = new ArrayList<SelectItem>();
        String[][] Roles =  DBController.GetData("usergroup", "name", "");
        
        for (int j = 0; j < Roles.length; ++j) 
        {
            RolesList.add(new SelectItem(Roles[j][0], Roles[j][0]));             
        }                                            
        
        // Status              
        StatusList = new ArrayList<SelectItem>();
        StatusList.add(new SelectItem("aktiv", "aktiv"));
        StatusList.add(new SelectItem("inaktiv", "inaktiv"));
                                            
        NotAttachedCourses = DBController.GetData("courses", "name", "");
        
        // clear HashMaps when there is no data        
        if (NotAttachedCourses.length == 0)
        {
            NotAttached.clear();
        }              
    }
    
    public void Save()        
    {
        Boolean Error = false;
        String Result= "";        
        String[][] CourseID;                                                                                                                                
                
        // Username überprüfen
        if (Username.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Nicht eingeloggt --> Bitte neu anmelden!",
			""));                     
        }
        
        if (Name.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Kein Username definiert!",
			""));
        }                        
        
        if (Name.length() > 30)
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Username darf nicht länger als 30 Zeichen sein!",
			""));  
        }
        
        // auf Dupletten checken
        String[][] Usernames = DBController.GetData ("user", "username", "WHERE username = '" + Name + "'");
        if (Usernames.length > 0)
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Es existiert bereits ein User mit den Usernamen '" + Name + "'!",
			""));  
        }        
        
        if (Email.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Keine Emailadresse definiert!",
			""));
        }
        
        if (Email.length() > 50)
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Emailadresse darf nicht länger als 50 Zeichen sein!",
			""));  
        }
        
        Integer AnzKurse = 0;        
        for( String name: NotAttached.keySet() )
        {            
            if (NotAttached.get(name) == true)
            {                   
                AnzKurse++;                                            
            }           
        }                 
        
        if (AnzKurse < 1 && Role.equals("Tutor") == true)
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "ACHTUNG: Mindestens ein Kurs muss zugeordnet werden!",
                    ""));  
        }  
        
        if (Error == true)
        {           
            return;
        }
        
        String[][] RoleID = DBController.GetData ("usergroup", "id", "WHERE name = '" + Role + "'");                        
        String StatusBool;
        
        if (Status.equals("aktiv"))
        {
            StatusBool = "1";
        }
        else
        {
            StatusBool = "0";
        }
        /*
        // Zufälliges 10-stelliges Passwort erzeugen
        final String allowedChars = "0123456789abcdefghijklmnopqrstuvwABCDEFGHIJKLMNOP!§$%&?*+#";
        SecureRandom random = new SecureRandom();
        StringBuilder pass = new StringBuilder(10);
        for (int i = 0; i < 10; i++) 
        {
            pass.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        
        String Passwort = pass.toString();
        */
        
        Result = DBController.InsertData("user (username, password, email, id_usergroup, state)", 
                                         "'" + Name + "','" + Password + "','" + Email + "','" + RoleID[0][0] + "','" + StatusBool + "'");
        
        FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"STATUS: " + Result,
			""));  
        
        // ID des angelegten Users auslesen
        String [][] newUserID = DBController.GetData ("user", "id", "WHERE username = '" + Name + "' AND email = '" + Email+ "' AND id_usergroup = " + RoleID[0][0] + " AND state = " + StatusBool);
        
        String ID = newUserID[0][0];
        
        for( String name: NotAttached.keySet() )
        {            
            if (NotAttached.get(name) == true)
            {                   
                CourseID = DBController.GetData ("courses", "id", "WHERE name = '" + name + "'");
                Result = DBController.UpdateData("courses","id_user", ID + "", "where id='" + CourseID[0][0] + "'");                                                
            }           
        }  
        
        // Alle Felder leeren
        Name = "";
        Email = "";
        NotAttached.clear();
        Role = "Tutor";
        Password = "";
        
        return;                           
    }
    
    public void emptyCoursesList (AjaxBehaviorEvent event)
    {                        
        String newRole = Role + "";
                
        if (newRole.equals("Tutor") == false)
        {
            NotAttachedCourses = new String[0][0];
            ShowCourses = false;
        }
        else
        {
            ShowCourses = true;
        }      
       
    }
    
        // Logout
        public String Logout()        
    {
        String Result;
        // Session beenden, damit User neu übergeben werden kann
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        Result = "Login Maske (JSF)?faces-redirect=true&includeViewParams=true";
        return Result;      
    }
    
    public String getName()
    {
    return Name;
    }
    
    public String getUsername()
    {
    return Username;
    }
	
    public String getEmail()
    {
    return Email;
    }
	
    public String getRole()
    {
    return Role;
    }
	
    public String getStatus()
    {
    return Status;
    }
    
    public String getPassword()
    {
    return Password;
    }
		
    public List<SelectItem> getRolesList()
    {
    return RolesList;
    }
	
    public List<SelectItem> getStatusList()
    {
    return StatusList;
    }
    
    public String[][] getNotAttachedCourses()
    {
    return NotAttachedCourses;
    }   
    
    public Map<String, Boolean> getNotAttached()
    {
    return NotAttached;
    }  
    
    public String getLoggedUser ()
    {
        return this.loggedUser;
    }
    
    public Boolean getShowCourses()
    {
        return this.ShowCourses;
    }
    
    public void setName(String name) 
    {
        this.Name = name;
    }
    
    public void setUsername(String username) 
    {
        this.Username = username;
    }
	
    public void setEmail(String email) 
    {
        this.Email = email;
    }
	
    public void setRole(String role) 
    {
        this.Role = role;
    }
    
    public void setPassword(String password) 
    {
        this.Password = password;
    }
   	
    public void setStatus(String status) 
    {
        this.Status = status;
    }
	
    public void setNotAttachedCourses(String[][] notAttachedCourses) 
    {
        this.NotAttachedCourses = notAttachedCourses;
    }   
    
    public void setLoggedUser(String loggedUser) 
    {
	this.loggedUser = loggedUser;
    }
    
    public void setShowCourses (Boolean showCourses)
    {
        this.ShowCourses = showCourses;
    }
    
}
