package request;

import constants.RequestCode;
import java.io.Serializable;

public class GetStatusRequest extends Request implements Serializable{
    private String userName;
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.GET_STATUS;
    }
}
