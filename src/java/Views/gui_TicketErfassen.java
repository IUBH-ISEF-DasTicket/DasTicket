
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
import java.time.LocalDateTime;

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
    String Comment = Aktueller Benutzername
    List CourseList = Kursliste aus DB
    List CategoryList = Kategorielist aus DB
    List PriorityList = Prioritätenliste aus DB
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


    // Initialisieren und laden der Select Einträge
    @PostConstruct
    public void Init()        
    {                        
        PriorityList = new ArrayList<SelectItem>();
        PriorityList.add(new SelectItem("---", "---"));
        String[][] Priorities =  DBController.GetData("priority", "name", "");
        
        for (int j = 0; j < Priorities.length; ++j) 
        {
            PriorityList.add(new SelectItem(Priorities[j][0], Priorities[j][0]));
             
        }
        
        CourseList = new ArrayList<SelectItem>();
        CourseList.add(new SelectItem("---", "---"));
        String[][] Courses =  DBController.GetData("courses", "name", "");
        
        for (int j = 0; j < Courses.length; ++j) 
        {
            CourseList.add(new SelectItem(Courses[j][0], Courses[j][0]));
             
        }
        
        CategoryList = new ArrayList<SelectItem>();
        CategoryList.add(new SelectItem("---", "---"));
        String[][] Categories =  DBController.GetData("category", "name", "");
        
        for (int j = 0; j < Categories.length; ++j) 
        {
            CategoryList.add(new SelectItem(Categories[j][0], Categories[j][0]));
             
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
        
    // Speichern
    public String Save()        
    {
        String Status;
        Boolean Error;      
        
        Error = false;
                
        // Username überprüfen
        if (Username.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Nicht eingeloggt --> Bitte neu anmelden!",
			""));
        Reset();     
        return null;    
        }
        
        // Priorität überprüfen
        if (Priority.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Keine Priorität ausgewählt!",
			""));    
        }
        
        // Kurs überprüfen
        if (Course.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Kein Kurs ausgewählt!",
			""));
        }
        
        // Kategorie überprüfen
        if (Category.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Keine Kategorie ausgewählt!",
			""));
        }
        
        // Titel überprüfen
        if (Title.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Kein Titel definiert!",
			""));
        }
        
        // Beschreibung überprüfen
        if (Comment.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Keine Beschreibung definiert!",
			""));
        }
        
        // Im Fehlerfall Meldungen anzeigen und Seite aktualisieren
        if (Error)
        {
            return null;   
        }
        
        //Auslesen der ID's        
        String[][] ID_Cat = DBController.GetData ("CATEGORY", "id", "WHERE name='" + Category + "'");
        String[][] ID_Cou = DBController.GetData ("COURSES", "id", "WHERE name='" + Course + "'");
        String[][] ID_Sta = DBController.GetData ("STATE", "id", "WHERE name='New'");
        String[][] ID_Pri = DBController.GetData ("PRIORITY", "id", "WHERE name='" + Priority + "'");
        String[][] ID_Admin = DBController.GetData ("COURSES", "id_user", "WHERE id = " + ID_Cou[0][0]);
        String[][] ID_User = DBController.GetData ("USER", "id", "WHERE username = '" + Username + "'");
        
        
        // Warnung wenn kein zugewiesener Tutor am Kurs 
        if (ID_Admin[0][0].equals("1") && Course.equalsIgnoreCase("#Allgemein") == false)
        {
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Kein zugewiesener Tutor am Kurs " + Course,
			""));
        }
        
        
        
        // Speichern der Werte
        Status = DBController.InsertData("Ticket (id_priority, title, description, id_category, id_courses, id_user, id_user2, id_state, creationDate)", 
                                         "'" + ID_Pri[0][0] + "','" + Title + "','" + Comment + "','" + ID_Cat[0][0] + "','" + ID_Cou[0][0] + "','" + ID_User[0][0] + "','" + ID_Admin[0][0] + "','" + ID_Sta[0][0] + "','" + LocalDateTime.now() + "'");
         // Status ausgeben
        FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"STATUS: " + Status,
			""));
        
        // ID_Ticket auslesen
        String[][] ID_Tic = DBController.GetData ("Ticket", "id", "WHERE title='" + Title + "'" + "and description='" + Comment + "'" + "and ID_State ='" + ID_Sta[0][0] + "'");

        // Reported Time setzen
        DBController.InsertData("ReportedTime (ReportedTime,id_Ticket)", "0," + ID_Tic[0][0]);
        
        return null;    
    }
    
    // Abbrechen
        public String Cancel()        
    {   
        Reset();
        return null;
    }
    
    // Standardwerte wiederherstellen
        public void Reset()
        {
            Course = null;
            Priority = null;
            Category = null;
            Title = null;
            Comment = null;
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
