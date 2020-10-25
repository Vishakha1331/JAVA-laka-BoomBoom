package request;

import constants.RequestCode;
import java.io.Serializable;

public class UserSearchRequest extends Request implements Serializable{
    private String searchString; // can be username or user's name
    
    public void setSearchString(String searchString){
        this.searchString = searchString;
    }
    
    public String getSearchString(){
        return searchString;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.USER_SEARCH;
    }
}
