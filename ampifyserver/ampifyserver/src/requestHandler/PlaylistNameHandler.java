package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import request.GetPlaylistRequest;
import request.Response;
import tools.DBConnection;

public class PlaylistNameHandler {
    private final GetPlaylistRequest getPlaylistRequest;
    
    public PlaylistNameHandler(GetPlaylistRequest getPlaylistRequest){
        this.getPlaylistRequest = getPlaylistRequest;
    }
    
    public Response getResponse(){
        String query = "SELECT * FROM playlist WHERE username=?";
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1,this.getPlaylistRequest.getUserName());
            ResultSet rs = pdt.executeQuery();
            ArrayList<String> playlistNames = new ArrayList<>();
            
            while(rs.next()){
                playlistNames.add(rs.getString(2));
            }
            //returns code successful if playlist names are found for the given username in database
            if(!playlistNames.isEmpty()){
            return new Response(playlistNames, ResponseCode.SUCCESSFUL); }
        } catch(SQLException e) {}
        //else it returns false, that is there are playlists of this user in our database;
        return new Response(null, ResponseCode.FAILURE);
    }
}
