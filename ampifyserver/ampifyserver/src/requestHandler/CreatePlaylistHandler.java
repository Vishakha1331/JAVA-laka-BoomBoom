package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import request.CreatePlaylistRequest;
import request.Response;
import tools.DBConnection;

public class CreatePlaylistHandler {
     private final CreatePlaylistRequest createPlaylistRequest;
     
     public CreatePlaylistHandler(CreatePlaylistRequest createPlaylistRequest){
         this.createPlaylistRequest = createPlaylistRequest;
}
     
     public Response getResponse(){
        
        String query= "SELECT * FROM playlist WHERE username=? AND pname=?";
        
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
                pdt.setString(1,this.createPlaylistRequest.getUserName());
                pdt.setString(2,this.createPlaylistRequest.getPName());
                ResultSet rs = pdt.executeQuery();
                
                if(rs.next()){
                    //playlist already exists
                    return new Response(null, ResponseCode.ALREADY_EXISTS);
                    
                }
                else{
                    query= "INSERT INTO playlist(username, pname) VALUES(?,?)";
                    pdt = DBConnection.connectDB().prepareStatement(query);
                    pdt.setString(1,this.createPlaylistRequest.getUserName());
                    pdt.setString(2,this.createPlaylistRequest.getPName());
                    pdt.executeUpdate();
                    return new Response(null, ResponseCode.NEW_PLAYLIST_CREATED);
                }
            } 
         catch(SQLException e) {e.printStackTrace();}
        return new Response(null, ResponseCode.FAILURE);
    }
}
