
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
    String Username = Aktueller Benutzername
    Integer ReportedTime = Aktueller Benutzername
    Date Date = Datum
    List CourseList = Kursliste aus DB
    List CategoryList = Kategorielist aus DB
    List PriorityList = Prioritätenliste aus DB
* <Sichtbarkeit>
* public
*/ 

// Managed Bean 
@ManagedBean
@SessionScoped

public class gui_Ticketbearbeiten 
{
    String Priority;
    String Category;
    String Course;
    String Title;
    String Comment;
    String Username;
    String Status;
    String ReportedTime;
    List<SelectItem> CourseList;
    List<SelectItem> CategoryList;
    List<SelectItem> PriorityList;
    List<SelectItem> StatusList;
    List<SelectItem> WorknoteDateList;
    List<SelectItem> WorknoteNoteList;
    
    
     // Initialisieren und laden der Select Einträge
    @PostConstruct
    public void Init()        
    {
        // Test!!!
        Integer ID = 31;
        Username = "Admin";
        
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
        
        // Status laden
        StatusList = new ArrayList<SelectItem>();
        i = 1;
        Result = "Start";
        while (Result != null ) 
        {   
          Result = DBController.GetData("state", "name", "where id= '" + i + "'");
          StatusList.add(new SelectItem(Result, Result));
        i++;
        }
        
        // Worknotes Date laden
        WorknoteDateList = new ArrayList<SelectItem>();
        i = 1;
        Result = "Start";
        while (Result != null ) 
        {   
          Result = DBController.GetData("worknotes", "name", "where id= '" + i + "'");
          StatusList.add(new SelectItem(Result, Result));
        i++;
        }
        
        // Worknotes Note laden
        WorknoteNoteList = new ArrayList<SelectItem>();
        i = 1;
        Result = "Start";
        while (Result != null ) 
        {   
          Result = DBController.GetData("worknotes", "name", "where id= '" + i + "'");
          StatusList.add(new SelectItem(Result, Result));
        i++;
        }
        
        // Werte setzen
        // Kurs setzen
        Course = DBController.GetData("courses", "name", "where id =(select id_courses from ticket where ID=" + ID +")");
         // Status setzen
        Status = DBController.GetData("state", "name", "where id =(select id_state from ticket where ID=" + ID +")");
         // Kategorie setzen
        Category = DBController.GetData("category", "name", "where id =(select id_category from ticket where ID=" + ID +")");
         // Priorität setzen
        Priority = DBController.GetData("priority", "name", "where id =(select id_priority from ticket where ID=" + ID +")");
         // Beschreibung setzen
        Comment = DBController.GetData("ticket", "description", "where id =" + ID);
        // Titel setzen
        Title = DBController.GetData("ticket", "title", "where id =" + ID);
        // Gebuchte Zeit setzen
        ReportedTime = DBController.GetData("ReportedTime", "ReportedTime", "where id_ticket =" + ID);
        
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
    public List<SelectItem> getStatusList()
    {
    return StatusList;
    }
    public List<SelectItem> getWorknoteDateList()
    {
    return WorknoteDateList;
    }
    public List<SelectItem> getWorknoteNoteList()
    {
    return WorknoteNoteList;
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
    public String getStatus() 
    {
	return Status;
    }
    public String getReportedTime() 
    {
	return ReportedTime;
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
    public void setStatus(String Status) 
    {
        this.Status = Status;
    }
    public void setReportedTime(String ReportedTime) 
    {
        this.ReportedTime = ReportedTime;
    }
}
