package data;

import java.io.Serializable;

public class History implements Serializable{
    private int sid;
    private String song;
    private static final long serialVersionUID = 3434334897764765L;
    
    public void setSID(int sid){
        this.sid = sid;
    }
    
    public int getSID(){
        return sid;
    }
    
    
    public void setSong(String song){
        this.song = song;
    }
    
    public String getSong(){
        return song;
    }
    
    
}
