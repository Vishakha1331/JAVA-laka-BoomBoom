package request;

import constants.RequestCode;
import java.io.Serializable;

public class SongRequest extends Request implements Serializable{
    private String song;
    private String username;
    
    
    public void setSong(String song){
        this.song = song;
    }
    
    public String getSong(){
        return song;
    }
    
    public void setUserName(String username ){
        this.username=username;
    }
    public String getUserName(){
        return username;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.GET_SONG;
    }
}
