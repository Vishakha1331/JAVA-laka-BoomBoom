package request;

import constants.FeedbackCode;
import constants.RequestCode;
import java.io.Serializable;

public class SendFeedbackRequest extends Request implements Serializable{
    private String userName, song;
    private FeedbackCode feedbackCode;
    
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
    
    public void setFeedbackCode(FeedbackCode feedbackCode){
        this.feedbackCode = feedbackCode;
    }
    
    public FeedbackCode getFeedbackCode(){
        return feedbackCode;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.SEND_FEEDBACK;
    }
}
