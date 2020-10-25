package request;

import constants.RequestCode;
import java.io.Serializable;

public class SongRequest extends Request implements Serializable{
    private String song;
    
    public void setSong(String song){
        this.song = song;
    }
    
    public String getSong(){
        return song;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.GET_SONG;
    }
}
