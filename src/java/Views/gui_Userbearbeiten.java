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
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author DIANE
 */

// Managed Bean 
@ManagedBean
//@SessionScoped
@RequestScoped

public class gui_Userbearbeiten {
    
    String Username = "Admin";
    String Name;
    String Email;
    String Role;
    String Status;
    List<SelectItem> RolesList;
    List<SelectItem> StatusList;
    String[][] NotAttachedCourses;
    String[][] AttachedCourses;
    
    private Map<String, Boolean> Attached = new HashMap<String, Boolean>();
    private Map<String, Boolean> NotAttached = new HashMap<String, Boolean>();
    
    Integer ID = 1;
    
    @PostConstruct
    public void Init()        
    {   
                
        Map<String, String> params =FacesContext.getCurrentInstance().
                   getExternalContext().getRequestParameterMap();
        String ExternalUserId = params.get("UserID");
        /*
        FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ExternalUserId = " + ExternalUserId,
			"")); */
        
        // Check if ExternalUserId is a number
        if (ExternalUserId != null)
        {
            if (ExternalUserId.matches("\\d+") == true)
            {
                ID = Integer.parseInt(ExternalUserId);
            }
        }
                
        // Username
        String[][] UserUsername = DBController.GetData("user", "username", "where id =" + ID);
        Name = UserUsername[0][0];
        
        // Email
        String[][] UserEmail = DBController.GetData("user", "email", "where id =" + ID);
        Email = UserEmail[0][0];
        
        
        // Rolle        
        RolesList = new ArrayList<SelectItem>();
        String[][] Roles =  DBController.GetData("usergroup", "name", "");
        
        for (int j = 0; j < Roles.length; ++j) 
        {
            RolesList.add(new SelectItem(Roles[j][0], Roles[j][0]));
             
        }
        
        String[][] UserRole = DBController.GetData("usergroup", "name", "where id =(select id_usergroup from user where ID=" + ID +")");
        Role = UserRole[0][0];                             
        
        // Status              
        StatusList = new ArrayList<SelectItem>();
        StatusList.add(new SelectItem("aktiv", "aktiv"));
        StatusList.add(new SelectItem("inaktiv", "inaktiv"));
        
        String[][] TicketStatus = DBController.GetData("user", "case state when 1 then 'aktiv' else 'inaktiv' end", "where id =" + ID);
        Status = TicketStatus[0][0];
        
        AttachedCourses = DBController.GetData("courses", "name", "where id_user = " + ID);        
        NotAttachedCourses = DBController.GetData("courses", "name", "where (id_user != " + ID + " OR id_user is null)");
        
        // clear HashMaps when there is no data
        if (AttachedCourses.length == 0)
        {
            Attached.clear();
        }
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
        
        for( String name: Attached.keySet() )
        {               
            if (Attached.get(name) == true)
            {                   
                CourseID = DBController.GetData ("courses", "id", "WHERE name = '" + name + "'");
                Result = DBController.UpdateDataWithInt("courses","id_user", "null" ,"where id='" + CourseID[0][0] + "'");
            }           
        }                                
        for( String name: NotAttached.keySet() )
        {            
            if (NotAttached.get(name) == true)
            {                   
                CourseID = DBController.GetData ("courses", "id", "WHERE name = '" + name + "'");
                Result = DBController.UpdateData("courses","id_user", ID+"" ,"where id='" + CourseID[0][0] + "'");
            }           
        }                  
        
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
        
        if (Error == true)
        {
            return;
        }
        
        String[][] RoleID = DBController.GetData ("usergroup", "id", "WHERE name = '" + Role + "'");
        
        String[][] OldRoleId = DBController.GetData("usergroup", "id", "where id =(select id_usergroup from user where ID=" + ID +")");
        
        if (RoleID[0][0].equals(OldRoleId[0][0]))
        {
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Dem Benutzer wurde diese Rolle bereits zugeteilt.",
			""));
        }
        
        String StatusBool;
        
        if (Status.equals("aktiv"))
        {
            StatusBool = "1";
        }
        else
        {
            StatusBool = "0";
        }
        
        Result = DBController.UpdateData("User","username", Name,"where ID='" + ID + "'");
        Result = DBController.UpdateData("User","email", Email,"where ID='" + ID + "'");
        Result = DBController.UpdateData("User","id_usergroup", RoleID[0][0],"where ID='" + ID + "'");
        Result = DBController.UpdateData("User","state", StatusBool,"where ID='" + ID + "'");
        
        FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"STATUS: " + Result,
			""));  
        
        // Refresh Lists
        AttachedCourses = DBController.GetData("courses", "name", "where id_user = " + ID);
        NotAttachedCourses = DBController.GetData("courses", "name", "where (id_user != " + ID + " OR id_user is null)");
        
        // unselect all courses
        for( String name: Attached.keySet() )
        {               
            Attached.put(name, false);           
        }                        
        
        for( String name: NotAttached.keySet() )
        {            
            NotAttached.put(name, false);                    
        }                                
    }
        
    public void emptyCoursesList (AjaxBehaviorEvent event)
    {
        System.out.println("emptyCoursesList selected Role: " + Role);        
        String newRole = Role + "";
                
        if (newRole.equals("Tutor") == false)
        {
            System.out.println("emptyCoursesList empty Lists");
            NotAttachedCourses = new String[0][0];
            AttachedCourses = new String[0][0];
        }
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
	
    public String[][] getAttachedCourses()
    {
    return AttachedCourses;
    }
        
    public Map<String, Boolean> getAttached()
    {
    return Attached;
    }    
    
    public Map<String, Boolean> getNotAttached()
    {
    return NotAttached;
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
	
    public void setStatus(String status) 
    {
        this.Status = status;
    }
	
    public void setNotAttachedCourses(String[][] notAttachedCourses) 
    {
        this.NotAttachedCourses = notAttachedCourses;
    }    
	
    public void setAttachedCourses(String[][] attachedCourses) 
    {
        this.AttachedCourses = attachedCourses;
    }
}
