
package Views;

import Database.DBController;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.annotation.PostConstruct;

/*
* gui_TicketErfassen
* Funktionalität für Ticket erfassen
*
* <Klassenvariablen>
    String Priority = Ticketpriorität
    String Category = Kategorie
    String Course = Ausgewählter Kurs
    String Title = Titel des Ticket
    String Comment = Notizen
* <Sichtbarkeit>
* public
*/ 

// Managed Bean 
@ManagedBean
@SessionScoped


public class gui_TicketErfassen 

{
    // Klassenvariablen
    String Priority;
    String Category;
    String Course;
    String Title;
    String Comment;
    String Username;
    List<SelectItem> CourseList;
    List<SelectItem> CategoryList;
    List<SelectItem> PriorityList;


    @PostConstruct
    public void Init()        
    {
        
        String Result;
        Integer i;
           
        // Prioritäten laden
        PriorityList = new ArrayList<SelectItem>();
        i = 1;
        Result = "Start";
        while (Result != null ) 
        {   
          Result = DBController.GetData("priority", "name", "where id= '" + i + "'");
          PriorityList.add(new SelectItem(Result, Result));
          i++;
        }
      
        // Kategorie laden
        CategoryList = new ArrayList<SelectItem>();
        i = 1;
        Result = "Start";
        while (Result != null ) 
        {   
          Result = DBController.GetData("category", "name", "where id= '" + i + "'");
          CategoryList.add(new SelectItem(Result, Result));
          i++;
        }
        
        // Kurse laden
        CourseList = new ArrayList<SelectItem>();
        i = 1;
        Result = "Start";
        while (Result != null ) 
        {   
          Result = DBController.GetData("courses", "name", "where id= '" + i + "'");
          CourseList.add(new SelectItem(Result, Result));
        i++;
        }
   
    }
   
        
    // Logout
        public String Logout()        
    {
        String Result;
        Result = "Login Maske (JSF)";
        return Result;      
    }
        
    // Speichern
    public String Save()        
    {
        String Result;
        String Status;
        Integer ID_Cat;
        Integer ID_Cou;
        Integer ID_Pri;
        Integer ID_User;
        Integer ID_Admin;
        Integer ID_Sta;
        
        //Auslesen der ID's
        ID_Cat = Integer.parseInt(DBController.GetData ("CATEGORY", "id", "WHERE name='" + Category + "'"));
        ID_Cou = Integer.parseInt(DBController.GetData ("COURSES", "id", "WHERE name='" + Course + "'"));
        ID_Sta = Integer.parseInt(DBController.GetData ("STATE", "id", "WHERE name='New'"));
        ID_Pri = Integer.parseInt(DBController.GetData ("PRIORITY", "id", "WHERE name='" + Priority + "'"));
        ID_Admin = Integer.parseInt(DBController.GetData ("Courses", "id_user", "WHERE id = " + ID_Cou));
        
        // Wartung wenn kein zugewiesener Tutor am Kurs 
        if (ID_Admin == 1 && Course.equalsIgnoreCase("#Allgemein") == false)
        {
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Kein zugewiesener Tutor am Kurs " + Course,
			""));
            
        }
        
        // TODO: Userverwaltung
        ID_User = 1;
     
        // Speichern der Werte
        Status = DBController.InsertData("Ticket (id_priority, title, description, id_category, id_courses, id_user, id_user2, id_state)", 
                                         "'" + ID_Pri + "','" + Title + "','" + Comment + "','" + ID_Cat + "','" + ID_Cou + "','" + ID_User + "','" + ID_Admin + "','" + ID_Sta + "'");
        
        FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"STATUS: " + Status,
			""));
                
        Result = "Erfassungsmaske_JSF";
        return Result;    
    }
    // Abbrechen
        public String Cancel()        
    {      
        String Result;
        Result = "Erfassungsmaske_JSF";
        return Result;      
    }
    
    // Getter Methoden
    public List<SelectItem> getCourseList()
    {
    return CourseList;
    }
    public List<SelectItem> getPriorityList()
    {
    return PriorityList;
    }
    public List<SelectItem> getCategoryList()
    {
    return CategoryList;
    }
     public String getPriority() 
    {
	return Priority;
    }
    public String getCategory() 
    {
	return Category;
    }
    public String getCourse() 
    {
	return Course;
    }
    public String getTitle() 
    {
	return Title;
    }
    public String getComment() 
    {
	return Comment;
    }
    public String getUsername() 
    {
	return Username;
    }
    
    // Setter Methoden
    public void setPriority(String Priority) 
    {
        this.Priority = Priority;
    }
    public void setCategory(String Category) 
    {
        this.Category = Category;
    }
    public void setCourse(String Course) 
    {
        this.Course = Course;
    }
    public void setTitle(String Title) 
    {
        this.Title = Title;
    }
    public void setComment(String Comment) 
    {
        this.Comment = Comment;
    }
        public void setUsername(String Username) 
    {
        this.Username = Username;
    }
  
}
