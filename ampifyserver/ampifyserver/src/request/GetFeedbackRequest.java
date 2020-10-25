package request;

import constants.RequestCode;
import java.io.Serializable;

public class GetFeedbackRequest extends Request implements Serializable{
    private String userName, song;
    
    
    public void setSong(String song){
        this.song = song;
    }
    
    public String getSong(){
        return song;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.GET_FEEDBACK;
    }
}
