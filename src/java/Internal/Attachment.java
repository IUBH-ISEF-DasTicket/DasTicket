/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Internal;

import java.io.File;
import java.io.FileInputStream;

/*
* Attachments
* Getter und Setter für Attachments Attribute 
*
* <Klassenvariablen>
* id = Primary Key
* file = Datei
*
* <Sichtbarkeit>
* public
*/ 

public class Attachment

{
    private int id;
    private File file;
    private int id_TICKET;
    
    // Getter Methoden
     public int getId() 
    {
        return id;
    }
     public int getId_TICKET() 
    {
        return id_TICKET;
    }
    
    
    public File getFile() 
    {
        return file;
    }
    
    // Setter Methoden
	
    public void setId(int newId) 
    {
        id = newId;
    }
    
    public void setFile (File newFile) 
    {
        file = newFile;
    }
    public void setId_TICKET (int newId_TICKET) 
    {
        id_TICKET = newId_TICKET;
    }
}
