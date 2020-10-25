package request;

import constants.RequestCode;
import java.io.Serializable;

public class RecommendationRequest extends Request implements Serializable{
    private String genre;
    private String language;
    
    public void setGenre(String genre){
        this.genre = genre;
    }
    
    public String getGenre(){
        return genre;
    }
    
    public void setLanguage(String language){
        this.language = language;
    }
    
    public String getLanguage(){
        return language;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.RECOMMENDATION;
    }
}
