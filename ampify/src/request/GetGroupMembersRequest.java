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
public class GetGroupMembersRequest extends Request implements Serializable{

    private String gname;

    public GetGroupMembersRequest(String gname) {
    this.gname=gname;
        
    }
    
    public String getGname(){
        return this.gname;
    }

    @Override
    public RequestCode getRequestCode() {
        
        return RequestCode.GET_GROUP_MEMBERS_REQUEST;

//To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
