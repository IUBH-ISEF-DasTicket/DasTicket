
package Views;

import java.util.List;
import Database.DBController;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
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
    List<SelectItem> Courses;
    List<SelectItem> CategoryList;
    List<SelectItem> PriorityList;
    
    // Prioritäten laden
    
    
    // Kategorie laden
    public List<SelectItem> CategoryList()        
    {
        List<SelectItem> CategoryList = new ArrayList<>();
        CategoryList.add(new SelectItem("Question", "Question"));
        CategoryList.add(new SelectItem("Incident", "Incident"));
        CategoryList.add(new SelectItem("Problem", "Problem"));
        return CategoryList;      
    }
    
    @PostConstruct
    public void Init()        
    {
        PriorityList = new ArrayList<SelectItem>();
        PriorityList.add(new SelectItem("Gering", "Gering"));
        PriorityList.add(new SelectItem("Mittel", "Mittel"));
        PriorityList.add(new SelectItem("Hoch", "Hoch"));
        
        CategoryList = new ArrayList<SelectItem>();
        CategoryList.add(new SelectItem("Question", "Question"));
        CategoryList.add(new SelectItem("Incident", "Incident"));
        CategoryList.add(new SelectItem("Problem", "Problem"));
             
    }
    
    // Kurse laden
        public String GetCourses()        
    {
        String CoursesList;
        CoursesList = "Foobar";
        return CoursesList;      
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
        Result = "Foobar";
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
    public List<SelectItem> getCourses()
    {
    return Courses;
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
    
    // Setter Methoden
    public void setCourses()
    {
    this.Courses = Courses;
    }
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
  
}
