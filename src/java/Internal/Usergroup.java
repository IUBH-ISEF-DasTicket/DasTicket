
package Internal;

/*
* Usergroup
* Getter und Setter für Usergroup Attribute 
*
* <Klassenvariablen>
* id = Primary Key
* name = Gruppenname
*
* <Sichtbarkeit>
* public
*/ 
public class Usergroup 
{
    private int id;
    private char name;
    
    // Getter Methoden
	
    public int getId() 
    {
        return id;
    }
    
    public char getName() 
    {
        return name;
    }
    
    // Setter Methoden
	
    public void setId(int newId) 
    {
        id = newId;
    }
    
    public void name(char newName) 
    {
        name = newName;
    }
}
