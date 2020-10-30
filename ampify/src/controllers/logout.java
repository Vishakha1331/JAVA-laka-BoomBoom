/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.MainClass;
import request.LogOutRequest;
import sun.security.krb5.internal.rcache.AuthList;

/**
 *
 * @author rishi
 */
public class logout {
    
    
    private String username;

    public logout(String username) {
        this.username = username;
        
        
        LogOutRequest logOutRequest =new LogOutRequest();
        logOutRequest.setUserName(username);
        
        try {
            MainClass.oos.writeObject(logOutRequest);
            MainClass.oos.flush();
            
            if(Files.isDirectory(Paths.get("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp"))){
              
                //FileUtils.deleteDirectory(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp"));
            File file=new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp");
                File[] contents = file.listFiles();
    if (contents != null) {
        for (File f : contents) {
            f.delete();
        }
    }
    file.delete();
    
    
        }
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(logout.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    

    
    
}
