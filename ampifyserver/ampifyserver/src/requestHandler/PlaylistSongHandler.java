package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import request.GetPlaylistSongsRequest;
import request.Response;
import tools.DBConnection;

public class PlaylistSongHandler {
    private final GetPlaylistSongsRequest getPlaylistSongsRequest;
    
    public PlaylistSongHandler(GetPlaylistSongsRequest getPlaylistSongsRequest){
        this.getPlaylistSongsRequest = getPlaylistSongsRequest;
    }
    
    public Response getResponse(){
        
        String query= "SELECT * FROM playlist_music WHERE username=? AND pname=?";
        
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
                pdt.setString(1,this.getPlaylistSongsRequest.getUserName());
                pdt.setString(2,this.getPlaylistSongsRequest.getPName());
                ResultSet rs = pdt.executeQuery();
                ArrayList<String> songs = new ArrayList<>();
                while(rs.next()){
                    songs.add(rs.getString(3));
                }
            
                if(!songs.isEmpty()){ return new Response(songs, ResponseCode.SUCCESSFUL);}
                else{ return new Response(null, ResponseCode.FAILURE);}
            } 
         catch(SQLException e) {}
        return new Response(null, ResponseCode.FAILURE);
    }
}
