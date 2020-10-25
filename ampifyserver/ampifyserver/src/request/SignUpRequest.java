package request;

import constants.RequestCode;
import java.io.Serializable;

public class SignUpRequest extends Request implements Serializable{
    private String userName;
    private String password;
    private String name;
    private String genre;
    private String language;
    private String email;
    
    public SignUpRequest(String name, String userName, String email, String password, String genre, String language){
       
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.genre = genre;
        this.language = language;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getGenre(){
        return genre;
    }
    
    public void setGenre(String genre){
        this.genre = genre;
    }
    
    public String getLanguage(){
        return language;
    }
    
    public void setLanguage(String language){
        this.language = language;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.SIGNUP_REQUEST;
    }
}
