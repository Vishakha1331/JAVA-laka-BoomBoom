package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import request.AddSongToPlaylistRequest;
import request.Response;
import tools.DBConnection;

public class AddSongToPlaylist {
    private final AddSongToPlaylistRequest addSongToPlaylistRequest;
    
    public AddSongToPlaylist(AddSongToPlaylistRequest addSongToPlaylistRequest){
        this.addSongToPlaylistRequest = addSongToPlaylistRequest;
    }
    
    public Response getResponse(){
        String query = "SELECT * FROM playlist_music WHERE username=? AND pname=? AND song=?";
        
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1,this.addSongToPlaylistRequest.getUserName());
            pdt.setString(2,this.addSongToPlaylistRequest.getPName());
            pdt.setString(3,this.addSongToPlaylistRequest.getSong());
            ResultSet rs = pdt.executeQuery();
            if(rs.next()){
                return new Response(null, ResponseCode.ALREADY_EXISTS);
            }
            else{
                query = "INSERT INTO playlist_music(username, pname, song) VALUES(?,?,?)";
            pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1,this.addSongToPlaylistRequest.getUserName());
            pdt.setString(2,this.addSongToPlaylistRequest.getPName());
            pdt.setString(3,this.addSongToPlaylistRequest.getSong());
            pdt.executeUpdate();
           
                return new Response(null, ResponseCode.SUCCESSFUL);
            
            }
        } catch (SQLException e) {}
        return new Response(null, ResponseCode.FAILURE);
    }
}
