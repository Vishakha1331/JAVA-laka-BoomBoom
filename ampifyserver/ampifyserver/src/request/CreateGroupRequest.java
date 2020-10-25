package request;

import constants.RequestCode;
import java.io.Serializable;
import java.util.ArrayList;

public class CreateGroupRequest  extends Request implements Serializable{
     private String gname;
     private String member;

    public CreateGroupRequest(String gname, String member) {
        this.gname = gname;
        this.member = member;
    }
 
     public String getGroupName(){
         return gname;
     }
     
     public String getMembers(){
         return member;
     }
     
     @Override
    public RequestCode getRequestCode(){
        return RequestCode.CREATE_GROUP;
    }
}
