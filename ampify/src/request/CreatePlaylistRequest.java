package request;

import constants.RequestCode;
import java.io.Serializable;

public class CreatePlaylistRequest extends Request implements Serializable {
    
    private String userName,pName;

    public CreatePlaylistRequest(String userName,String pName) {
        this.pName=pName;
        this.userName=userName;
    }
    
    public String getUserName(){
        return this.userName;
        
    }
    
    public String getPName(){
        return this.pName;
        
    }

    @Override
    public RequestCode getRequestCode() {
        return RequestCode.CREATE_PLAYLIST;
        
    }
    
    
    
}