package request;

import constants.RequestCode;
import java.io.Serializable;

public class TrendingRequest extends Request implements Serializable{
     private int numberOfSongs;
     
     public void setNumberOfSongs(int numberOfSongs){
         this.numberOfSongs = numberOfSongs;
     }
     
     public int getNumberOfSongs(){
         return numberOfSongs;
     }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.TRENDING;
    }
}
