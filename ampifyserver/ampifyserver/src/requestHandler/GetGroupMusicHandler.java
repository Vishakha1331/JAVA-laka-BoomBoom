package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import request.GetGroupPlaylistMusicRequest;
import request.Response;
import tools.DBConnection;

public class GetGroupMusicHandler {
    private final GetGroupPlaylistMusicRequest getGroupPlaylistMusicRequest;
    
    public GetGroupMusicHandler(GetGroupPlaylistMusicRequest getGroupPlaylistMusicRequest){
        this.getGroupPlaylistMusicRequest = getGroupPlaylistMusicRequest;
    }
    
    public Response getResponse(){
        String query = "SELECT * FROM group_music WHERE gname=? AND pname=?";
        
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1,this.getGroupPlaylistMusicRequest.getGName());
            pdt.setString(2,this.getGroupPlaylistMusicRequest.getPName());
            ArrayList<String> songs = new ArrayList<>();
            
            ResultSet rs = pdt.executeQuery();
            while(rs.next()){
                songs.add(rs.getString(3));
            }
            
            if(!songs.isEmpty())
                return new Response(songs, ResponseCode.SUCCESSFUL);
            
        } catch (SQLException e) {}
        return new Response(null, ResponseCode.FAILURE);
    }
}