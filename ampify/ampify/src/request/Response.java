package request;

import constants.ResponseCode;
import java.io.Serializable;


public class Response implements Serializable{
    private Object responseObject;
    private ResponseCode responseCode;
    private static final long serialVersionUID = 323232323565656L;
    
    public Response(Object responseObject, ResponseCode responsecode) {
        this.responseObject = responseObject;
        this.responseCode = responsecode;
    }
    
    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
    
    
}
