/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author rishi
 */
public class GroupPlaylist {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rishi
 */
    private String PID;
    private String name;
    private String GID;
    private String[] musics;
    
    public GroupPlaylist(String PID,String name,String GID,String[] musics){
        this.PID=PID;
        this.name=name;
        this.GID=GID;
        this.musics=musics;
           
    }
    public String getPID() {
        return this.PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getGID() {
        return this.GID;
    }

    public void setGID(String UID) {
        this.GID=GID;
    }
    
    public String[] getMusics() {
        return this.musics;
    }

    public void setMusics(String[] musics) {
        this.musics=musics;
    }
    

}
