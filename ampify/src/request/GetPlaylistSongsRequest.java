package request;

import constants.RequestCode;
import java.io.Serializable;

public class GetPlaylistSongsRequest extends Request implements Serializable {
    
    private String userName,pName;

    public GetPlaylistSongsRequest(String userName,String pName) {
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
        return RequestCode.GET_PLAYLIST_SONGS;
        
    }
    
    
    
}