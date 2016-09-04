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
import java.util.Map;
import java.util.TreeMap;
import javax.faces.model.SelectItem;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;

/*
* gui_TicketUebersicht
* Funktionalität für Ticketsuche und Übersicht
*
* <Klassenvariablen>
    
*
* <Sichtbarkeit>
* public
*/ 

// Managed Bean 
@ManagedBean
@SessionScoped

public class gui_UserUebersicht 
{
    
    String Username;
    String Name;
    String Role;
    String Status;
    String Course;
    String State;
    
    List<SelectItem> RoleList;
    List<SelectItem> CourseList;
    List<SelectItem> UserStateList;
    
    String SortOrder;
    String TotalResults;
    
    String [][] ListOfUsers;
    
    // Initialisieren und laden der Select Einträge
    @PostConstruct
    public void Init()        
    { 
        String[][] Result = DBController.GetData("user", 
        "id, username, (select name from usergroup where id = id_usergroup), case state when 1 then 'aktiv' else 'inaktiv' end", "");
        
        ListOfUsers = Result;
        
        UserStateList = new ArrayList<SelectItem>();
        UserStateList.add(new SelectItem("---", "---"));
        UserStateList.add(new SelectItem("aktiv", "aktiv"));
        UserStateList.add(new SelectItem("inaktiv", "inaktiv"));
        
        
        RoleList = new ArrayList<SelectItem>();
        RoleList.add(new SelectItem("---", "---"));
        String[][] Roles =  DBController.GetData("usergroup", "name", "");        
        for (int i = 0; i < Roles.length; ++i) 
        {
            RoleList.add(new SelectItem(Roles[i][0], Roles[i][0]));
             
        }
        
        CourseList = new ArrayList<SelectItem>();
        CourseList.add(new SelectItem("---", "---"));
        String[][] Courses =  DBController.GetData("courses", "name", "");
        
        for (int i = 0; i < Courses.length; ++i) 
        {
            CourseList.add(new SelectItem(Courses[i][0], Courses[i][0]));             
        }            
        
    }
    
    public String Reset()
    {
        // Alle Kriterien zurücksetzen    
        Name = "";
        Role = "---";
        Status = "---";
        Course = "---";
        State = "---";
        
        return null;
    }    
    
    public String[][] getListOfUsers() 
    {
	return ListOfUsers;
    }
    
    public void setListOfUsers( String[][] listOfUsers) 
    {
	this.ListOfUsers = listOfUsers;
    }
    
    public String getName() 
    {
	return Name;
    }
    
    public void setName( String name) 
    {
	this.Name = name;
    }    
    
    public Collection getUserStateList()
    {
    return UserStateList;
    }
    
    public String getState()
    {
        return State;
    }
    
    public String getUsername()
    {
        return Username;
    }
    
    public void setState(String state) 
    {
        this.State = state;
    }
    
    public String getRole() 
    {
	return Role;
    }
    
    public void setRole( String role) 
    {
	this.Role = role;
    }
    
    public List<SelectItem> getRoleList()
    {
    return RoleList;
    }
    
    
    public String getCourse() 
    {
	return Course;
    }
    
    public void setCourse( String course) 
    {
	this.Course = course;
    }
    
    public List<SelectItem> getCourseList()
    {
    return CourseList;
    }
    
    public String getSortOrder() 
    {
	return SortOrder;
    }
    
    public void setSortOrder(String SortOrder) 
    {
        this.SortOrder = SortOrder;
    }
    
    public String getTotalResults() 
    {
	return TotalResults;
    }
    
    public void setTotalResults(String totalResults) 
    {
        this.TotalResults = totalResults;
    }
    
    public void setUsername(String username) 
    {
        this.Username = username;
    }
  
    public String Search()
            
    {                                  
        String query = "";
        
        if (Name.isEmpty() == false)
        {
            String username;
            String usernameWithJokers = Name.replace('*','%');            

            query += "username like '" + usernameWithJokers + "' AND ";
        }
        
        if (Role.isEmpty() == false &&
            Role.equals("---") == false)
        {
            query += "id_usergroup = (select id from usergroup where name = '" + Role + "') AND ";
        }
        
        if (State.isEmpty() == false &&
            State.equals("---") == false)
        {
            String StateClause = "";

            if (State.equalsIgnoreCase("aktiv"))
            {
                StateClause = "true";
            }

            if (State.equalsIgnoreCase("inaktiv"))
            {
                StateClause = "false";
            }

            query += "state = " + StateClause + " AND ";
        }
        
        if (Course.isEmpty() == false &&
            Course.equals("---") == false)
        {
            query += "id = (select id_user from courses where name = '" + Course + "') AND ";
        }
        
        // Letztes AND wegschneiden
        if (query.isEmpty() == false)
        {
            query = query.substring(0, query.length() - 4);
        }              
        
        switch (SortOrder) 
        {
            case "ID":          SortOrder = "id";
                                break;
            case "Username":    SortOrder = "username";
                                break;
            case "Rolle":       SortOrder = "id_usergroup";
                                break;
            case "Status":      SortOrder = "state";
                                break;
            default:            SortOrder = "id";
                                break;
        }
        
        if (query.isEmpty() == false)
        {
            query = "WHERE " + query + " ORDER BY " + SortOrder;         
        }
        else
        {
            query = " ORDER BY " + SortOrder; 
        }            
               
        String[][] Result = DBController.GetData("user", "id, username, (select name from usergroup where id = id_usergroup), case state when 1 then 'aktiv' else 'inaktiv' end", query);
        ListOfUsers = Result;
        
    return null;

    }
    
    public String editUser()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();

        String UserID  = params.get("UserID");        
        return "User_bearbeiten.xhtml?id=" + UserID;             
         
    }
}