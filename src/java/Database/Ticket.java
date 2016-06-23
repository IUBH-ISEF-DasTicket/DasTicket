package Database;

import java.util.Date;
/*
* Ticket
* Klasse, die die einzelnen Anfragen im 
* System repräsentiert.
*
*
* <Sichtbarkeit>
* public
*/ 

public class Ticket {

	// Attribute
	
	private int id;
	private int priority;
	private Date creationDate;
	private char title;
	private int id_CATEGORY;
	private int id_COURSE;
	private int id_USER_assignedTo;
	private int id_USER_caller;
	private int id_STATE;
	
	// Getter Methoden
	
	public int getId() {
		return id;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public char getTitle() {
		return title;
	}
	
	public int getId_CATEGORY() {
		return id_CATEGORY;
	}
	
	public int getId_COURSE() {
		return id_COURSE;
	}
	
	public int getId_USER_assignedTo() {
		return id_USER_assignedTo;
	}
	
	public int getId_USER_caller() {
		return id_USER_caller;
	}
	
	public int getId_STATE() {
		return id_STATE;
	}
	
	// Setter Methoden
	
	public void setId(int newId) {
		id = newId;
	}
	
	public void setPriority(int newPriority) {
		priority = newPriority;
	}
	
	public void setCreationDate(Date newDate) {
		creationDate = newDate;
	}
	
	public void setTitle(char newTitle) {
		title = newTitle;
	}
	
	public void setId_CATEGORY(int newCategory) {
		id_CATEGORY = newCategory;
	}
	
	public void setId_COURSE(int newCourse) {
		id_COURSE = newCourse;
	}
	
	public void setId_USER_assignedTo(int newUser_assignedTo) {
		id_USER_assignedTo = newUser_assignedTo;
	}
	
	public void setId_USER_caller(int newUser_caller) {
		id_USER_caller = newUser_caller;
	}
	
	public void setId_STATE(int newState) {
		id_STATE = newState;
	}

	
	
}
