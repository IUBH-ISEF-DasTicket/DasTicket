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
import javax.faces.model.SelectItem;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author DIANE
 */

// Managed Bean 
@ManagedBean
@SessionScoped

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
    
    Integer ID = 1;
    
    @PostConstruct
    public void Init()        
    {   
        
        //String[][] NotAttachedCourses = DBController.GetData("courses", "name", "");

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
        
        String[][] TicketRole = DBController.GetData("usergroup", "name", "where id =(select id_usergroup from user where ID=" + ID +")");
        Role = TicketRole[0][0];                             
        
        // Status              
        StatusList = new ArrayList<SelectItem>();
        StatusList.add(new SelectItem("aktiv", "aktiv"));
        StatusList.add(new SelectItem("inaktiv", "inaktiv"));
        
        String[][] TicketStatus = DBController.GetData("user", "case state when 1 then 'aktiv' else 'inaktiv' end", "where id =" + ID);
        Status = TicketStatus[0][0];
        
        AttachedCourses = DBController.GetData("courses", "name", "where id_user = " + ID);
        NotAttachedCourses = DBController.GetData("courses", "name", "where id_user != " + ID);
        
        
    }
    
    public void Save()        
    {
        Boolean Error = false;
        String Result;
        
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
        
        if (Email.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Keine Emailadresse definiert!",
			""));
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
        
        Result = DBController.UpdateData("User","username", Name,"where ID='" + ID + "'");
        Result = DBController.UpdateData("User","email", Email,"where ID='" + ID + "'");
        Result = DBController.UpdateData("User","id_usergroup", RoleID[0][0],"where ID='" + ID + "'");
        Result = DBController.UpdateData("User","state", StatusBool,"where ID='" + ID + "'");
        
        FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"STATUS: " + Result,
			""));                
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
