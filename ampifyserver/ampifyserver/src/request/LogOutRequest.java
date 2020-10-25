package request;

import constants.RequestCode;
import java.io.Serializable;

public class LogOutRequest extends Request implements Serializable{
    private String userName;
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getUserName(){
        return userName;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.LOGOUT_REQUEST;
    }
}
