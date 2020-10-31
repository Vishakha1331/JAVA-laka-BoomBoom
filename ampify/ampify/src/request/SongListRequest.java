package request;

import constants.RequestCode;
import java.io.Serializable;

public class SongListRequest extends Request implements Serializable{
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.GET_SONGLIST;
    }
}
