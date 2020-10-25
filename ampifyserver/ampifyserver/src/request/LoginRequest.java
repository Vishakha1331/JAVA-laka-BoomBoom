package request;

import constants.RequestCode;
import java.io.Serializable;


public class LoginRequest extends Request implements Serializable{
    private String userName;
    private String password;
    
    
    public LoginRequest(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.LOGIN_REQUEST;
    }
}
