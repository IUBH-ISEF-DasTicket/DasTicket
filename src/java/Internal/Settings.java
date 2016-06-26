
package Internal;

/*
* Settings
* Getter und Setter für Settings Attribute 
*
* <Klassenvariablen>
* id = Primary Key
* module = Bereich
* param = Einstellung
* value = Wert
*
* <Sichtbarkeit>
* public
*/ 

public class Settings 

{
    private int id;
    private char module;
    private char param;
    private char value;
    
    // Getter Methoden
     public int getId() 
    {
        return id;
    }
    
    public char getModule() 
    {
        return module;
    }
    
    public char getParam() 
    {
        return param;
    }
    
    public char getValue() 
    {
        return value;
    }
    

    // Setter Methoden
	
    public void setId(int newId) 
    {
        id = newId;
    }
    
    public void setModule(char newModule) 
    {
        module = newModule;
    }
    
    public void setParam (char newParam)
    {
        param = newParam;
    }
    
    public void setValue (char newValue)
    {
        param = newValue;
    }
    
}
