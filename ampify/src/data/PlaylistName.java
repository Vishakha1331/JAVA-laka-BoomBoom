package data;

import java.io.Serializable;


public class PlaylistName implements Serializable{
    private static final long serialVersionUID = 3434334657823435655L;
    private String pname = null;
    private int pid;
    
    public void setPID(int pid){
        this.pid = pid;
    }
    
    public int getPID(){
        return pid;
    }
    
    public void setPname(String pname){
        this.pname = pname;
    }
    
    public String getPname(){
        return pname;
    }
}
