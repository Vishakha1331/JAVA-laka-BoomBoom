package constants;

public enum ResponseCode {
    SUCCESSFUL,
    NEW_PLAYLIST_CREATED,
    ALREADY_EXISTS,
    FAILURE;
    
    ResponseCode(){
        this.toString();
    }
}
