
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
    String NewComment = Neue Notizen
    String Username = Aktueller Benutzername
    String Status = Status des Ticket
    Integer ReportedTime = Berechnete Zeit am Ticket
    List CourseList = Kursliste aus DB
    List CategoryList = Kategorielist aus DB
    List PriorityList = Prioritätenliste aus DB
    List StatusList = Statusliste aus DB
    Collection WorknoteDateList = Dautmeinträge der Worknotes
    Collection WorknoteNoteList = Notizeinträge der Worknotes
*
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
    String NewComment;
    //String Username = "Admin";
    String Status;
    String ReportedTime;
    List<SelectItem> CourseList;
    List<SelectItem> CategoryList;
    List<SelectItem> PriorityList;
    List<SelectItem> StatusList;
    Collection WorknoteDateList;
    /*Collection*/ String [][] WorknoteNoteList;
    
    // Test!!!
    Integer ID = 366;
    String Username = "Admin";
    
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
        
        StatusList = new ArrayList<SelectItem>();
        StatusList.add(new SelectItem("---", "---"));
        String[][] status =  DBController.GetData("state", "name", "");
        
        for (int j = 0; j < status.length; ++j) 
        {
            StatusList.add(new SelectItem(status[j][0], status[j][0]));
             
        }
        
        String[][] worknote =  DBController.GetData("worknotes", "creationDate, notes", "where id_ticket = " + ID);
        WorknoteNoteList = worknote;

        // Werte setzen
        // Kurs setzen
        String[][] TicketCourse = DBController.GetData("courses", "name", "where id =(select id_courses from ticket where ID=" + ID +")");
        Course = TicketCourse[0][0];
         // Status setzen
        String[][] TicketStatus = DBController.GetData("state", "name", "where id =(select id_state from ticket where ID=" + ID +")");
        Status = TicketStatus[0][0];
        // Kategorie setzen
        String[][] TicketCategory = DBController.GetData("category", "name", "where id =(select id_category from ticket where ID=" + ID +")");
        Category = TicketCategory[0][0];
        // Priorität setzen
        String[][] TicketPriority = DBController.GetData("priority", "name", "where id =(select id_priority from ticket where ID=" + ID +")");
        Priority = TicketPriority[0][0];
        // Beschreibung setzen
        String[][] TicketComment = DBController.GetData("ticket", "description", "where id =" + ID);
        Comment = TicketComment[0][0];
        // Titel setzen
        String[][] TicketTitle = DBController.GetData("ticket", "title", "where id =" + ID);
        Title = TicketTitle[0][0];
        // Gebuchte Zeit setzen
        String[][] TicketReportedTime = DBController.GetData("ReportedTime", "ReportedTime", "where id_ticket =" + ID);
        ReportedTime = TicketReportedTime[0][0];
                
    }
    
        // Speichern
    public String Save()        
    {
        String Result = "";
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
       // Reset();     
        return null;    
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
        String[][] ID_Sta = DBController.GetData ("STATE", "id", "WHERE name='" + Status + "'");
        String[][] ID_Pri = DBController.GetData ("PRIORITY", "id", "WHERE name='" + Priority + "'");
        String[][] ID_Admin = DBController.GetData ("COURSES", "id_user", "WHERE id = " + ID_Cou[0][0]);
        
        
        // Warnung wenn kein zugewiesener Tutor am Kurs 
        if (ID_Admin[0][0].equals("1") && Course.equalsIgnoreCase("#Allgemein") == false)
        {
            FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Kein zugewiesener Tutor am Kurs " + Course,
			""));
        }
        
        // Speichern der Werte
        // Kategorie
        DBController.UpdateData("Ticket","ID_Category", String.valueOf(ID_Cat[0][0]),"where ID='" + ID + "'");
        // Priorität
        DBController.UpdateData("Ticket","ID_Priority", String.valueOf(ID_Pri[0][0]),"where ID='" + ID + "'");
         // Kurs
        DBController.UpdateData("Ticket","ID_Courses", String.valueOf(ID_Cou[0][0]),"where ID='" + ID + "'");
         // Status
        DBController.UpdateData("Ticket","ID_State", String.valueOf(ID_Sta[0][0]),"where ID='" + ID + "'");
        // Titel
        DBController.UpdateData("Ticket","Title", Title,"where ID='" + ID + "'");
        // Kommentar 
        DBController.UpdateData("Ticket","description", Comment ,"where ID='" + ID + "'");
       // ReportedTime
        DBController.UpdateData("ReportedTime","ReportedTime", ReportedTime ,"where ID_ticket='" + ID + "'"); 
       
        // Worknote
        if (NewComment.isEmpty() == false)
        {
            Result = DBController.InsertData("Worknotes (internal,notes,creationDate,id_Ticket)", "false,'" + NewComment + "','" + LocalDateTime.now() + "'," + ID);
        }
        
        // Status ausgeben
        FacesContext.getCurrentInstance().addMessage(
            null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"STATUS: " + Result,
			""));
        
        // Worknotes neu laden
        String[][] worknote =  DBController.GetData("worknotes", "creationDate, notes", "where id_ticket = " + ID);
        WorknoteNoteList = worknote;
        // Kommentar leeren
        NewComment = "";
        
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
    public Collection getWorknoteDateList()
    {
    return WorknoteDateList;
    }
    public String[][] getWorknoteNoteList()
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
    public String getNewComment() 
    {
	return NewComment;
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
    public void setNewComment(String NewComment) 
    {
        this.NewComment = NewComment;
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
