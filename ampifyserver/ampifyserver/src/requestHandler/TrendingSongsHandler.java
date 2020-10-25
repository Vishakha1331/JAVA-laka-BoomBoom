package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import request.Response;
import request.TrendingRequest;
import tools.DBConnection;

public class TrendingSongsHandler {
    private TrendingRequest trendingRequest;
    
    public TrendingSongsHandler(TrendingRequest trendingRequest){
        this.trendingRequest = trendingRequest;
    }
    
    public Response getResponse(){
        String query = "SELECT * FROM music ORDER BY playcount DESC";
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
             ResultSet rs = pdt.executeQuery();
             ArrayList<String> songs=new ArrayList<>();
             int i=0;
             while(rs.next() && i<this.trendingRequest.getNumberOfSongs()){
                songs.add(rs.getString(2));
                        i++;
             }
             if(!songs.isEmpty()){
            return new Response(songs, ResponseCode.SUCCESSFUL);
             }
        } catch(SQLException e) {return new Response(null, ResponseCode.FAILURE);}
        return new Response(null, ResponseCode.FAILURE);
    }
}
