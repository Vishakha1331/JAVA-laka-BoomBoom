package request;

import constants.RequestCode;
import java.io.Serializable;



public class UpdateRequest extends Request implements Serializable{
    private String name, email, username;
    
    public void setName(String name){
        this.name=name;
    }
    public void setUserName(String username){
        this.username=username;
    }
    public void setEmail(String email){
        this.email=email;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getUserName(){
        return this.username;
    }
    public String getEmail(){
        return this.email;
    }

    @Override
    public RequestCode getRequestCode() {
        
        return RequestCode.UPDATE_REQUEST;
        
    }
    
    
    
}