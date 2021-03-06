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
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;
import javax.annotation.PostConstruct;

/*
* gui_TicketUebersicht
* Funktionalität für Ticketsuche und Übersicht
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
    String Tutor = Tutor
    Date creationDate = Erstellungsdatum des Tickets
    Integer ReportedTime = Berechnete Zeit am Ticket
    List CourseList = Kursliste aus DB
    List CategoryList = Kategorielist aus DB
    List PriorityList = Prioritätenliste aus DB
    List StatusList = Statusliste aus DB
    List TutorList = Liste der Benutzer aus DB
    String [][] ListOfTickets = Ergebnis der Suche
*
* <Sichtbarkeit>
* public
*/ 

// Managed Bean 
@ManagedBean
@SessionScoped

public class gui_TicketUebersicht 
{
    // Variablen
    String Priority;
    String Category;
    String Course;
    String Title;
    String Username;
    String Status;
    String CreationDate;
    String TicketID;
    String SortOrder;
    String TotalResults;
    String Tutor;
    List<SelectItem> CourseList;
    List<SelectItem> CategoryList;
    List<SelectItem> PriorityList;
    List<SelectItem> StatusList;
    List<SelectItem> TutorList;
    
    String [][] ListOfTickets;
    

    // Initialisieren und laden der Select Einträge
    @PostConstruct
    public void Init()        
    {
        // Prioritäten
        PriorityList = new ArrayList<SelectItem>();
        PriorityList.add(new SelectItem("---", "---"));
        String[][] Priorities =  DBController.GetData("priority", "name", "");
        
        for (int j = 0; j < Priorities.length; ++j) 
        {
            PriorityList.add(new SelectItem(Priorities[j][0], Priorities[j][0]));
             
        }
        
        // Kurse
        CourseList = new ArrayList<SelectItem>();
        CourseList.add(new SelectItem("---", "---"));
        String[][] Courses =  DBController.GetData("courses", "name", "");
        
        for (int j = 0; j < Courses.length; ++j) 
        {
            CourseList.add(new SelectItem(Courses[j][0], Courses[j][0]));
             
        }
        
        // Kategorien
        CategoryList = new ArrayList<SelectItem>();
        CategoryList.add(new SelectItem("---", "---"));
        String[][] Categories =  DBController.GetData("category", "name", "");
        
        for (int j = 0; j < Categories.length; ++j) 
        {
            CategoryList.add(new SelectItem(Categories[j][0], Categories[j][0]));
             
        }
        
        // Status
        StatusList = new ArrayList<SelectItem>();
        StatusList.add(new SelectItem("---", "---"));
        String[][] status =  DBController.GetData("state", "name", "");
        
        for (int j = 0; j < status.length; ++j) 
        {
            StatusList.add(new SelectItem(status[j][0], status[j][0]));
             
        }    
        
        // Tutor
        TutorList = new ArrayList<SelectItem>();
        TutorList.add(new SelectItem("---", "---"));
        String[][] tutor =  DBController.GetData("user", "username", "where id_usergroup in (1,3)");
        
        for (int j = 0; j < tutor.length; ++j) 
        {
            TutorList.add(new SelectItem(tutor[j][0], tutor[j][0]));
             
        }    
        
        String[][] Result = DBController.GetData("ticket", "id, (select name from courses where id = id_courses), title, (select name from state where id = id_state)", "where id_user=(select id from user where username ='" + Username + "')");               
        ListOfTickets = Result;       
          
    }
   
    public String Search()            
    {          
            // Variablen
            String ID_User;

            String[][] user = DBController.GetData ("USER", "id", "WHERE username = '" + Username + "'");
            ID_User = user[0][0];
            
            // Aufbauen der Bedingungen
            String query = "WHERE id_user = '" + ID_User + "'";            
            
            // ID
            if (TicketID.isEmpty() == false )
            {
                query += " AND id = " + TicketID;
            }
            
            // Priorität
            if (Priority.equalsIgnoreCase("---") == false )
            {                
                query += " AND id_priority = (select id from priority where name = '" + Priority + "')";
            }
            
            // Kategorie
            if (Category.equalsIgnoreCase("---") == false )
            {                
                query += " AND id_category = (select id from category where name = '" + Category + "')";
            }
            
            // Kurs
            if (Course.equalsIgnoreCase("---") == false )
            {
                query += " AND id_courses = (select id from courses where name = '" + Course + "')";                
            }
            
            // Erstellungsdatum
            if (CreationDate.isEmpty() == false )
            {
                query += " AND creationDate = STR_TO_DATE('" + CreationDate + "','%d-%m-%Y')";
            }
            
            // Status
            if (Status.equalsIgnoreCase("---") == false )
            {
                query += " AND id_state = (select id from state where name = '" + Status + "')";
            }
            
            // Zuordnung
            if (Tutor.equalsIgnoreCase("---") == false )
            {
                query += " AND id_user2 = (select id from user where username = '" + Tutor + "')";                
            }                                                
            
            // Suchreihenfolge
            switch (SortOrder) 
            {
                case "ID":          SortOrder = "id";
                                    break;
                case "Datum":    SortOrder = "creationDate";
                                    break;
                case "Kurs":       SortOrder = "id_courses";
                                    break;
                case "Status":      SortOrder = "id_state";
                                    break;
                case "Titel":      SortOrder = "title";
                                    break;
                default:            SortOrder = "id";
                                    break;
            }
            
            if (query.isEmpty() == false)
            {
                query = query + " ORDER BY " + SortOrder;
            }
                
            
            // Anzahl der Ergebnisse
            String MaxValues;           
            if (TotalResults.equalsIgnoreCase("Alle"))
            {
               MaxValues = "";
            }
            else
            {
               MaxValues = "LIMIT " + TotalResults;
            }
            query += " " + MaxValues;                        
            
            // Ergebnis prüfen
            String[][] Result = DBController.GetData("ticket", "id, (select name from courses where id = id_courses), title, (select name from state where id = id_state)", query);            
            if (Result.length > 0)
                {
                    ListOfTickets = Result;
                }
            else
                {
                    Result = DBController.GetData("ticket", "id, (select name from courses where id = id_courses), title, (select name from state where id = id_state)", "where id_user=(select id from user where username ='" + Username + "')");               
                    ListOfTickets = Result;  
                    FacesContext.getCurrentInstance().addMessage(
                    null,new FacesMessage(FacesMessage.SEVERITY_WARN,
			"ACHTUNG: Keine Tickets mit der Suchmaske gefunden!",
			""));
                }
                                    
            return null;

    }
    
    public String Reset()
    {
        // Alle Kriterien zurücksetzen
        TicketID = "";
        Category = "---";
        Priority = "---";
        Course = "---";
        Status = "---";
        CreationDate = "";
        
        return null;
    }    
    
    public String editTicket()
    {
        // Parameter lesen
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();

        // Zu Ticket springen
        String TicketID  = params.get("TicketID");
        return "Ticket_bearbeiten.xhtml?id=" + TicketID ;       
         
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
    public String getUsername() 
    {
	return Username;
    }
    public String getStatus() 
    {
	return Status;
    }
    public String getCreationDate() 
    {
	return CreationDate;
    }
    public String getTicketID() 
    {
	return TicketID;
    }
    public String getSortOrder() 
    {
	return SortOrder;
    }
    public String getTotalResults() 
    {
	return TotalResults;
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
    public void setUsername(String Username) 
    {
        this.Username = Username;
    }
    public void setStatus(String Status) 
    {
        this.Status = Status;
    }
        public void setCreationDate(String CreationDate) 
    {
        this.CreationDate = CreationDate;
    }
    public void setTicketID(String TicketID) 
    {
        this.TicketID = TicketID;
    }
    public void setSortOrder(String SortOrder) 
    {
        this.SortOrder = SortOrder;
    }
    public void setTutor(String Tutor) 
    {
        this.Tutor = Tutor;
    }
    public void setTotalResults(String TotalResults) 
    {
        this.TotalResults = TotalResults;
    }
    
    public String[][] getListOfTickets() 
    {
	return ListOfTickets;
    }
    
    public void setListOfTickets( String[][] listOfTickets) 
    {
	this.ListOfTickets = listOfTickets;
    }

}

    

