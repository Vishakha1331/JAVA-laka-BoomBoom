package request;

import constants.RequestCode;
import constants.StatusCode;
import java.io.Serializable;

public class SetStatusRequest extends Request implements Serializable{
    private String userName;
    private StatusCode statusCode;
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public void setStatusCode(StatusCode statusCode){
        this.statusCode = statusCode;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public StatusCode getStatusCode(){
        return statusCode;
    }

    @Override
    public RequestCode getRequestCode() {
      return RequestCode.SET_STATUS;
    }
    
    
}
