package request;

import constants.RequestCode;
import java.io.Serializable;


public class HistoryRequest extends Request implements Serializable{
    private String userName;
    private int numberOfSongs;
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setNumberOfSongs(int numberOfSongs){
         this.numberOfSongs = numberOfSongs;
     }
     
     public int getNumberOfSongs(){
         return numberOfSongs;
     }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.HISTORY_REQUEST;
    }
}
