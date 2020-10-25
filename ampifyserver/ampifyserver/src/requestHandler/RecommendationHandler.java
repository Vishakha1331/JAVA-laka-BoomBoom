package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import request.RecommendationRequest;
import request.Response;
import tools.DBConnection;

public class RecommendationHandler {
    private RecommendationRequest recommendationRequest;
    
    public RecommendationHandler(RecommendationRequest recommendationRequest){
        this.recommendationRequest = recommendationRequest;
    }
    
    public Response getResponse(){
        String query = "SELECT * FROM music WHERE genre LIKE '" + this.recommendationRequest.getGenre() + "%' AND language='"
                + this.recommendationRequest.getLanguage() +"'";
               
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
             ResultSet rs = pdt.executeQuery();
             ArrayList<String> songs=new ArrayList<>();
             
             while(rs.next()){
                songs.add(rs.getString(2));
             }
             //returns response code positive if we find few songs corresponding to user's choice of genre and language
             if(!songs.isEmpty()){
                 return new Response(songs, ResponseCode.SUCCESSFUL);
             }
        } catch(SQLException e) {}
        //else we return failure and null as response object
        return new Response(null, ResponseCode.FAILURE);
    }
}
