package request;

import constants.RequestCode;
import java.io.Serializable;

public class SharePlaylistRequest extends Request implements Serializable{
private final String userName,targetUserName,playlistName;
    public SharePlaylistRequest(String username ,String targetusername ,String playlistname) {
        this.playlistName=playlistname;
        this.targetUserName=targetusername;
        this.userName=username;
    }
    
    public String getUserName(){
        return this.userName;
    }
    
    
    public String getTargetUserName(){
        return this.targetUserName;
    }
    
    public String getPlaylistName(){
        return this.playlistName;
    }

    @Override
    public RequestCode getRequestCode() {
        return RequestCode.SHARE_PLAYLIST_REQUEST;
    
    }
}