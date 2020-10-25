package statusHandler;

import constants.StatusCode;
import java.io.Serializable;

public class StatusResponse implements Serializable{
    private Object responseObject;
    private StatusCode statusCode;
    private static final long serialVersionUID = 323232376873565656L;
    
    public StatusResponse(Object responseObject, StatusCode statusCode) {
        this.responseObject = responseObject;
        this.statusCode = statusCode;
    }
    
    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
    
    
}
