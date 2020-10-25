package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import request.Response;
import request.SearchSongRequest;
import tools.DBConnection;

public class SearchSongHandler {
    private final SearchSongRequest searchSongRequest;
    
    public SearchSongHandler(SearchSongRequest searchSongRequest){
        this.searchSongRequest = searchSongRequest;
    }
    
    public Response getResponse() throws SQLException{
        
        String query = "SELECT * FROM music WHERE genre LIKE ? AND language LIKE? AND artist LIKE ? AND name LIKE ? ";
        
        
        // we will make our query as request recieved
        //like for ex if client only wants filter for artist or only wants to search a song
        
        try{
        
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            
            if(searchSongRequest.getSearchGenre().equals("--SELECT--"))
                pdt.setString(1,"%%") ;
            else
                pdt.setString(1,"%"+searchSongRequest.getSearchGenre()+"%");
            
            
            if(searchSongRequest.getSearchLanguge().equals("--SELECT--"))
                pdt.setString(2,"%%");
            else
                pdt.setString(2,"%"+searchSongRequest.getSearchLanguge()+"%"); 
            
            
            if(searchSongRequest.getSearchArtist().equals("--SELECT--"))
                pdt.setString(3,"%%");
            else
                pdt.setString(3,"%"+searchSongRequest.getSearchArtist()+"%"); 
            
            System.out.println(searchSongRequest.getSearchSongName().toLowerCase());
            if(searchSongRequest.getSearchSongName()!="")
                pdt.setString(4, "%"+searchSongRequest.getSearchSongName().toLowerCase()+"%");
            else
                pdt.setString(4, "");
            
             ResultSet rs = pdt.executeQuery();
            
             ArrayList<String> songs=new ArrayList<>();
            
             while(rs.next()){
                 songs.add(rs.getString(2));
             }
            
             if(!songs.isEmpty()){
               return new Response(songs, ResponseCode.SUCCESSFUL);
             }
        } catch(SQLException e) {}
        
        return new Response(null, ResponseCode.FAILURE);
    
    }
}