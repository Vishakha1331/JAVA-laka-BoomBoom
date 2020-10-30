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
public class GetGroupsRequest extends Request implements Serializable{
    private String username;
    public GetGroupsRequest(String username) {
        this.username=username;
    }
    
    public String getUsername(){
        return this.username;
    }

    @Override
    public RequestCode getRequestCode() {
        return RequestCode.GET_GROUPS_REQUEST;
    }
    
}
