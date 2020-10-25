package feedbackHandler;

import constants.FeedbackCode;
import java.io.Serializable;

public class FeedbackResponse implements Serializable{
    private Object responseObject;
    private FeedbackCode feedbackCode;
    private static final long serialVersionUID = 3232323121213565656L;
    
    public FeedbackResponse(Object responseObject, FeedbackCode feedbackCode) {
        this.responseObject = responseObject;
        this.feedbackCode = feedbackCode;
    }
    
    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public FeedbackCode getFeedbackCode() {
        return feedbackCode;
    }

    public void setFeedbackCode(FeedbackCode feedbackCode) {
        this.feedbackCode = feedbackCode;
    }
    

}
