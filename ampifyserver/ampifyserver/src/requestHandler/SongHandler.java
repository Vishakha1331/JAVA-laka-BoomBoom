package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import request.SongRequest;
import request.Response;
import tools.DBConnection;

// to be edited, working on it
public class SongHandler {
    private final SongRequest songRequest;
    
    public SongHandler(SongRequest songRequest){
        this.songRequest = songRequest;
    }
    
    public Response getResponse(){
        String query = "SELECT * FROM music WHERE sid=?";
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setInt(1,this.songRequest.getSID());
            ResultSet rs = pdt.executeQuery();
            
            if(rs.next()){
                Song song = new Song();
                song.setSID(rs.getInt(1));
                song.setName(rs.getString(3));
                song.setSongName(rs.getString(4));
                song.setLength(rs.getLong(11));
                song.setDownloadLink(rs.getString(12));
                song.setLyricFile(rs.getString(13));
                return new Response(song, ResponseCode.SUCCESSFUL);
            }
            return new Response(null, ResponseCode.FAILURE);
        } catch(SQLException e) {}
        return new Response(null, ResponseCode.FAILURE);
    }
}
