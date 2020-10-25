package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import request.CreateGroupPlaylistRequest;
import request.Response;
import tools.DBConnection;

public class CreateGroupPlaylistHandler {
        private CreateGroupPlaylistRequest createGroupPlaylistRequest;
    
    public CreateGroupPlaylistHandler(CreateGroupPlaylistRequest createGroupPlaylistRequest){
        this.createGroupPlaylistRequest = createGroupPlaylistRequest;
    }
        
    public Response getResponse(){
        try{
            String query = "SELECT * FROM group_playlists WHERE gname=? AND pname=?";
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1, createGroupPlaylistRequest.getGroupName());
            pdt.setString(2, createGroupPlaylistRequest.getPlaylistName());
            ResultSet rs = pdt.executeQuery();
            
            if(rs.next())
                return new Response(null, ResponseCode.ALREADY_EXISTS);
            
            else{
                query = "INSERT INTO group_playlists(gname, pname) VALUES(?,?)";
                pdt = DBConnection.connectDB().prepareStatement(query);
                pdt.setString(1, createGroupPlaylistRequest.getGroupName());
                pdt.setString(2, createGroupPlaylistRequest.getPlaylistName());
                pdt.executeUpdate();
                return new Response(null, ResponseCode.SUCCESSFUL);
            }
        }  catch(Exception e){}
        return new Response(null, ResponseCode.FAILURE);
    }
}