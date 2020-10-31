package request;

import constants.RequestCode;
import java.io.Serializable;

public class SynchronizationRequest extends Request implements Serializable{
    private int gid;
    
    public void setGID(int gid){
        this.gid = gid;
    }
    
    public int getGID(){
        return gid;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.SYNCHRONIZATION;
    }
}
