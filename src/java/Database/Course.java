/*
* Course
* Klasse, die die einzelnen Kurse repräsentiert, 
* zu denen Anfragen gestellt werden können.
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
	
	public setId(int newId){
		id = newId;
	}
	
	public setName(char newName){
		name = newName;
	}
	public setId_USER(int newUser){
		Id_USER = newUser;
	}
	
	
}