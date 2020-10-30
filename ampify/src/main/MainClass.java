
package main;

import controllers.loginform;
import data.User;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainClass {
    
    public static final String serverIP ="localhost";
    public static final int portNO =2345;
    //private final static int downloadPortNO =63423;
    
    public static Socket socket;
    
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
    
    
    
    
    
    
    public static User user;
    
  
   
    public static void main(String[] args) {
        
        try{
            socket =new Socket(serverIP,portNO);
            oos= new ObjectOutputStream(socket.getOutputStream());
            ois= new ObjectInputStream(socket.getInputStream());
            loginform lgform = new loginform();
            lgform.setVisible(true);
            
            
            
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
        
    }
    
    public void restart(){
        try{
            socket =new Socket(serverIP,portNO);
            oos= new ObjectOutputStream(socket.getOutputStream());
            ois= new ObjectInputStream(socket.getInputStream());
//            loginform lgform = new loginform();
//            lgform.setVisible(true);
//            
            
            
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
}
