/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package request;

import constants.RequestCode;
import java.io.Serializable;

/**
 *
 * @author rishi
 */
public class GetGroupPlaylistRequest extends Request implements Serializable{
     private String groupname;

    public GetGroupPlaylistRequest(String groupname) {
        this.groupname=groupname;
    }
     public String getGroupname(){
         return this.groupname;
     }
    @Override
    public RequestCode getRequestCode() {
        return RequestCode.GET_GROUP_PLAYLIST_REQUEST;
    }
    
}