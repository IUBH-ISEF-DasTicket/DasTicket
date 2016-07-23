
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
    //String Username = "Admin";
    String Status;
    String ReportedTime;
    List<SelectItem> CourseList;
    List<SelectItem> CategoryList;
    List<SelectItem> PriorityList;
    List<SelectItem> StatusList;
    List<SelectItem> WorknoteDateList;
    List<SelectItem> WorknoteNoteList;
    
    // Test!!!
    Integer ID = 18;
    String Username = "Admin";
    
     // Initialisieren und laden der Select Einträge
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
          WorknoteDateList.add(new SelectItem(Result, Result));
        i++;
        }
        
        // Worknotes Note laden
        WorknoteNoteList = new ArrayList<SelectItem>();
        i = 1;
        Result = "Start";
        while (Result != null ) 
        {   
          Result = DBController.GetData("worknotes", "name", "where id= '" + i + "'");
          WorknoteNoteList.add(new SelectItem(Result, Result));
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
    
        // Speichern
    public String Save()        
    {
        String Result;
        Boolean Error;
        Integer ID_Cat;
        Integer ID_Cou;
        Integer ID_Pri;
        Integer ID_Admin;
        Integer ID_Sta;
        
        Error = false;
                
        // Username überprüfen
        if (Username.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Nicht eingeloggt --> Bitte neu anmelden!",
			""));
       // Reset();     
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
        // Status überprüfen
        if (Status.isEmpty())
        {
            Error = true;
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Kein Status definiert!",
			""));
        }
        
        // Im Fehlerfall Meldungen anzeigen und Seite aktualisieren
        if (Error)
        {
            return null;   
        }
        
        //Auslesen der ID's
        ID_Cat = Integer.parseInt(DBController.GetData ("CATEGORY", "id", "WHERE name='" + Category + "'"));
        ID_Cou = Integer.parseInt(DBController.GetData ("COURSES", "id", "WHERE name='" + Course + "'"));
        ID_Sta = Integer.parseInt(DBController.GetData ("STATE", "id", "WHERE name='" + Status + "'"));
        ID_Pri = Integer.parseInt(DBController.GetData ("PRIORITY", "id", "WHERE name='" + Priority + "'"));
        ID_Admin = Integer.parseInt(DBController.GetData ("COURSES", "id_user", "WHERE id ='" + ID_Cou + "'"));
        
        // Warnung wenn kein zugewiesener Tutor am Kurs 
        if (ID_Admin == 1 && Course.equalsIgnoreCase("#Allgemein") == false)
        {
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Kein zugewiesener Tutor am Kurs " + Course,
			""));
        }
        
        // Speichern der Werte
        // Kategorie
        Result = DBController.UpdateData("Ticket","ID_Category", String.valueOf(ID_Cat),"where ID='" + ID + "'");
        // Priorität
        Result = DBController.UpdateData("Ticket","ID_Priority", String.valueOf(ID_Pri),"where ID='" + ID + "'");
         // Kurs
        Result = DBController.UpdateData("Ticket","ID_Courses", String.valueOf(ID_Cou),"where ID='" + ID + "'");
         // Status
        Result = DBController.UpdateData("Ticket","ID_State", String.valueOf(ID_Sta),"where ID='" + ID + "'");
        // Titel
        Result = DBController.UpdateData("Ticket","Title", Title,"where ID='" + ID + "'");
        // Kommentar 
        Result = DBController.UpdateData("Ticket","description", Comment ,"where ID='" + ID + "'");
       // ReportedTime
        Result = DBController.UpdateData("ReportedTime","ReportedTime", ReportedTime ,"where ID_ticket='" + ID + "'"); 
       
        // Status ausgeben
        FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"STATUS: " + Result,
			""));
        return null;
    
    
    }
    
        // Abbrechen
        public String Cancel()        
    {   
        return null;
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
