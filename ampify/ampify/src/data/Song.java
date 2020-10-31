package data;

import java.io.Serializable;


public class Song implements Serializable{
    private String name = null;
    private String songName = null;
    private int sid;
    private long length;
    private String downloadLink = null;
    private String lyricFile = null;
   private static final long serialVersionUID = 3434334657878765L;
    
    public void setSID(int sid){
        this.sid = sid;
    }
    
    public int getSID(){
        return sid;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setSongName(String songName){
        this.songName = songName;
    }
    
    public String getSongName(){
        return songName;
    }
    
    public void setLength(long length){
        this.length = length;
    }
    
    public long getLength(){
        return length;
    }
    
    public void setDownloadLink(String downloadLink){
        this.downloadLink = downloadLink;
    }
    
    public String getDownloadLink(){
        return downloadLink;
    }
    
    public void setLyricFile(String lyricFile){
        this.lyricFile = lyricFile;
    }
    
    public String getLyricFile(){
        return lyricFile;
    }
}
