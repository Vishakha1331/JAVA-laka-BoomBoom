/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package request;

import constants.RequestCode;
import constants.ResponseCode;
import java.io.Serializable;

/**
 *
 * @author rishi
 */
public class CreateGroupPlaylistRequest extends Request implements Serializable{

    private String groupname;
    private String playlistname;

    public CreateGroupPlaylistRequest(String groupname,String playlistname) {
        this.groupname=groupname;
        this.playlistname=playlistname;
    }
    
    public String getGroupName(){
        return this.groupname;
    }
    public String getPlaylistName(){
        return this.playlistname;
    }
            
    @Override
    public RequestCode getRequestCode() {
        return RequestCode.CREATE_GROUP_PLAYLIST;
    }
    
    
}