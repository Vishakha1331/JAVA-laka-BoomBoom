package request;

import constants.RequestCode;
import java.io.Serializable;

public class GetGroupPlaylistMusicRequest extends Request implements Serializable {
    
    private String gname,pName;

    public GetGroupPlaylistMusicRequest(String gname,String pName) {
        this.pName=pName;
        this.gname=gname;
    }
    
    public String getGName(){
        return this.gname;
        
    }
    
    public String getPName(){
        return this.pName;
        
    }

    @Override
    public RequestCode getRequestCode() {
        return RequestCode.GET_GROUP_PLAYLIST_SONGS;
        
    }
    
    
    
}