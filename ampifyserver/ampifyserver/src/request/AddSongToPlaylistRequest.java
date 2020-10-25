package request;

import constants.RequestCode;
import java.io.Serializable;

public class AddSongToPlaylistRequest extends Request implements Serializable{
    private String song, userName, pname;
    
    public void setSong(String song){
        this.song = song;
    }
    
    public String getSong(){
        return song;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setPName(String pname){
        this.pname = pname;
    }
    
    public String getPName(){
        return pname;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.ADD_SONG_TO_PLAYLIST;
    }
}
