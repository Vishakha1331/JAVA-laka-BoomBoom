package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import request.AddSongToGroupPlaylist;
import request.Response;
import tools.DBConnection;

public class AddSongToGroupPlaylistHandler {
        private AddSongToGroupPlaylist addSongToGroupPlaylist;
    
    public AddSongToGroupPlaylistHandler(AddSongToGroupPlaylist addSongToGroupPlaylist){
        this.addSongToGroupPlaylist = addSongToGroupPlaylist;
    }
        
    public Response getResponse(){
        try{
            String query = "SELECT * FROM group_music WHERE gname=? AND pname=? AND song=?";
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1, addSongToGroupPlaylist.getGname());
            pdt.setString(2, addSongToGroupPlaylist.getPname());
            pdt.setString(3, addSongToGroupPlaylist.getSong());
            ResultSet rs = pdt.executeQuery();
            
            if(rs.next())
                return new Response(null, ResponseCode.ALREADY_EXISTS);
            
            else{
                query = "INSERT INTO group_music(gname, pname, song) VALUES(?,?,?)";
                pdt = DBConnection.connectDB().prepareStatement(query);
                pdt.setString(1, addSongToGroupPlaylist.getGname());
                pdt.setString(2, addSongToGroupPlaylist.getPname());
                 pdt.setString(3, addSongToGroupPlaylist.getSong());
                pdt.executeUpdate();
                return new Response(null, ResponseCode.SUCCESSFUL);
            }
        }  catch(Exception e){}
        return new Response(null, ResponseCode.FAILURE);
    }
}