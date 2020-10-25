package request;

import constants.RequestCode;
import java.io.Serializable;

public class SearchSongRequest extends Request implements Serializable{
    private String searchSongName=null, searchArtist=null, searchGenre=null, searchLanguage =null;
    
    public SearchSongRequest(String Artist,String Genre,String Language,String Name) {
        this.searchArtist=Artist;
        this.searchGenre=Genre;
        this.searchLanguage=Language;
        this.searchSongName=Name;
       
    }
    
    
    public String getSearchArtist(){
        return searchArtist;
    }
    
    
    public String getSearchGenre(){
        return searchGenre;
    }
    
    
    public String getSearchLanguge(){
        return searchLanguage;
    }
    
    
    public String getSearchSongName(){
        return searchSongName;
    }
    
    @Override
    public RequestCode getRequestCode(){
        return RequestCode.SEARCH_SONG;
    }
}