package Database;

/*
* User
* Klasse, die die einzelnen User repräsentiert 
* (Studenten, Tutoren, Admins)
*
* <Sichtbarkeit>
* public
*/ 

public class User {
	
	// Attribute
	
	private int id;
	private char username;
	private char password;
	private char email;
	private boolean state;
	private int id_USERGROUP;
	
	// Getter Methoden
	
	public int getId() {
		return id;
	}
	
	public char getUsername() {
		return username;
	}
	
	public char getPassword() {
		return password;
	}
	
	public char getEmail() {
		return email;
	}
	
	public boolean getState() {
		return state;
	}
	
	public int getId_USERGROUP() {
		return id_USERGROUP;
	}
	
	// Setter Methoden
	
	public void setId(int newId){
		id = newId;
	}

	public void setUsername(char newUsername){
		username = newUsername;
	}
	
	public void setPassword(char newPassword){
		password = newPassword;
	}
	
	public void setEmail(char newEmail){
		email = newEmail;
	}
	
	
	public void getState(boolean newState) {
		state = newState;
	}
	
	public void setId_USERGROUP(int newUsergroup){
		id_USERGROUP = newUsergroup;
	}
	
}
