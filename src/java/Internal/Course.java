package Internal;

/*
* Course
* Klasse, die die einzelnen Kurse repr�sentiert, 
* zu denen Anfragen gestellt werden k�nnen.
*
*
* <Sichtbarkeit>
* public
*/ 

public class Course {

	// Attribute
	
	private int id;
	private char name;
	private int id_USER;
	
	// Getter Methoden
	
	public int getId() {
		return id;
	}
	
	public char getName() {
		return name;
	}
	
	public int getId_USER() {
		return id_USER;
	}
	// Setter Methoden
	
	public void setId(int newId){
		id = newId;
	}
	
	public void setName(char newName){
		name = newName;
	}
	public void setId_USER(int newUser){
		id_USER = newUser;
	}
	
	
}