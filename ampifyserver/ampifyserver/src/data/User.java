package data;

import java.io.Serializable;

public class User implements Serializable, Cloneable{
    private  String userName = null;
    private  String name = null;
    private  String email = null;
    private  String genre = null;
    private  String language = null;
   
    
    private static final long serialVersionUID = 2121212325454673131L;
    
    public void setUserName(String userName){
            this.userName = userName;
        }
    
    public String getUserName(){
        return this.userName;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setName(String name){
            this.name = name;
        }
    
    public String getName(){
        return this.name;
    }
    
    public void setGenre(String genre){
            this.genre = genre;
    }
    
    public String getGenre(){
        return this.genre;
    }
    
    public void setLanguage(String language){
            this.language= language;
        }
    
    public String getLanguage(){
            return this.language;
        }
    
    
    @Override
    public String toString() {
        return this.getUserName();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}

