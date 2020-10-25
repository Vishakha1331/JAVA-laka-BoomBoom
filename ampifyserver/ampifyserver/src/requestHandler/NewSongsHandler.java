package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import request.NewSongsRequest;
import request.Response;
import tools.DBConnection;

public class NewSongsHandler {
    private NewSongsRequest newSongsRequest;
    
    public NewSongsHandler(NewSongsRequest newSongsRequest){
        this.newSongsRequest = newSongsRequest;
    }
    
    public Response getResponse(){
         String query = "SELECT * FROM music ORDER BY date DESC";
         try{
             PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
             ResultSet rs = pdt.executeQuery();
             int i=0;
             ArrayList<String> songs=new ArrayList<>();
             
             while(rs.next() && i<this.newSongsRequest.getNumberOfSongs()){
                songs.add(rs.getString(2));
                i++;
             }
             return new Response(songs, ResponseCode.SUCCESSFUL);
         } catch(SQLException e) {}
         return new Response(null, ResponseCode.FAILURE);
    }
}
