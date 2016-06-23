package Database;

import java.util.Date;
/*
* Worknotes
* Klasse, die die einzelnen Notizen die zu einer Anfrage
* Hinterlegt werden können repräsentiert.
*
*
* <Sichtbarkeit>
* public
*/ 
public class Worknotes {

	// Attribute
	
	private int id;
	private boolean internal;
	private char notes;
	private Date date;
	private int id_USER;
	private int id_TICKET;
	
	// Getter Methoden
	
	public int getId() {
		return id;
	}
	
	public boolean getInternal() {
		return internal;
	}
	
	public char getNotes() {
		return notes;
	}
	
	public Date Date() {
		return Date;
	}
	
	public int getId_USER() {
		return id_USER;
	}
	
	public int getId_TICKET() {
		return id_TICKET;
	}
	

	
	// Setter Methoden
	
	public void setId(int newId) {
		id = newId;
	}
	
	public void setInternal(boolean newInternal) {
		internal = newInteral;
	}
	
	public void setInternal(char newNotes) {
		notes = newNotes;
	}
	
	public void setDate(Date newDate) {
		date = newDate;
	}
	
	public void setId_USER(int newUser) {
		id_USER = newUser;
	}
	
	public void setId_TICKET(int newTicket) {
		id_TICKET = newTicket;
	}
	
	
}
