
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
import javax.faces.model.SelectItem;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;

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
//@SessionScoped
@RequestScoped

public class gui_Ticketbearbeiten 
{
    String Priority;
    String Category;
    String Course;
    String Title;
    String Comment;
    String NewComment;
    String Username;
    String Status;
    String ReportedTime;
    String Usergroup;
    String Tutor;
    Integer ID;
    List<SelectItem> CourseList;
    List<SelectItem> CategoryList;
    List<SelectItem> PriorityList;
    List<SelectItem> StatusList;
    List<SelectItem> TutorList;
    Collection WorknoteDateList;
    /*Collection*/ String [][] WorknoteNoteList;
    
    
     // Initialisieren und laden der Select Einträge
    @PostConstruct
    public void Init()        
    {
        // Parameter laden
        Map<String, String> params =FacesContext.getCurrentInstance().
                   getExternalContext().getRequestParameterMap();
        
        String ExternalTicketId = params.get("TicketID");
        String UsernameTemp = params.get("UsernameTemp");
        
        if (UsernameTemp != null)
        {
                Username = UsernameTemp;
        }
        
        // Check if ExternalUserId is a number
        if (ExternalTicketId != null)
        {
            if (ExternalTicketId.matches("\\d+") == true)
            {
                ID = Integer.parseInt(ExternalTicketId);
            }
        }
        
        // Prioritäten
        PriorityList = new ArrayList<SelectItem>();
        //PriorityList.add(new SelectItem("", ""));
        String[][] Priorities =  DBController.GetData("priority", "name", "");
        
        for (int j = 0; j < Priorities.length; ++j) 
        {
            PriorityList.add(new SelectItem(Priorities[j][0], Priorities[j][0]));
             
        }
        
        // Kurse
        CourseList = new ArrayList<SelectItem>();
        //CourseList.add(new SelectItem("", ""));
        String[][] Courses =  DBController.GetData("courses", "name", "");
        
        for (int j = 0; j < Courses.length; ++j) 
        {
            CourseList.add(new SelectItem(Courses[j][0], Courses[j][0]));
             
        }
        
        // Kategorien
        CategoryList = new ArrayList<SelectItem>();
        //CategoryList.add(new SelectItem("", ""));
        String[][] Categories =  DBController.GetData("category", "name", "");
        
        for (int j = 0; j < Categories.length; ++j) 
        {
            CategoryList.add(new SelectItem(Categories[j][0], Categories[j][0]));
             
        }
        
        // Status
        StatusList = new ArrayList<SelectItem>();
        //StatusList.add(new SelectItem("", ""));
        String[][] status =  DBController.GetData("state", "name", "");
        
        for (int j = 0; j < status.length; ++j) 
        {
            StatusList.add(new SelectItem(status[j][0], status[j][0]));
             
        }
        
        // Tutor
        TutorList = new ArrayList<SelectItem>();
        //TutorList.add(new SelectItem("", ""));
        String[][] Tutors =  DBController.GetData("user", "username", "where id_usergroup = 1");
        
        for (int j = 0; j < Tutors.length; ++j) 
        {
            TutorList.add(new SelectItem(Tutors[j][0], Tutors[j][0]));
             
        }
        
        // Worknote
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
        if (TicketReportedTime.length > 0)
        {
            ReportedTime = TicketReportedTime[0][0];
        }
        // Tutor setzen
        String[][] TutorName = DBController.GetData("user", "username", "where id =(select id_user2 from ticket where id =" + ID + ")");
        Title = TutorName[0][0];
        
        // Rolle auslesen
        String[][] UsergroupTemp = DBController.GetData("user", "id_usergroup", "where username ='" + Username + "'");
        Usergroup = UsergroupTemp[0][0];

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
        try
        {
            if (NewComment.isEmpty() == false)
            {
                Result = DBController.InsertData("Worknotes (internal,notes,creationDate,id_Ticket)", "false,'" + NewComment + "','" + LocalDateTime.now() + "'," + ID);
            }

        }
        catch ( NullPointerException ex) 
        {
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
    public List<SelectItem> getTutorList()
    {
    return TutorList;
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
        public Integer getID() 
    {
	return ID;
    }
    public String getUsergroup() 
    {
	return Usergroup;
    }    
    public String getTutor() 
    {
	return Tutor;
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
    public void setID(Integer ID) 
    {
        this.ID = ID;
    }
    public void setUsergroup(String Usergroup) 
    {
        this.Usergroup = Usergroup;
    }
        public void setTutor(String Tutor) 
    {
        this.Tutor = Tutor;
    }
}
