package request;

import constants.RequestCode;
import java.io.Serializable;

public class AddGroupMemberRequest extends Request implements Serializable{
    private String member, gname;
    
    public AddGroupMemberRequest(String member, String gname){
        this.gname = gname;
        this.member = member;
    }

    public String getMember() {
        return member;
    }

    public String getGname() {
        return gname;
    }

    @Override
    public RequestCode getRequestCode() {
        return RequestCode.ADD_GROUP_MEMBER;
    }
}