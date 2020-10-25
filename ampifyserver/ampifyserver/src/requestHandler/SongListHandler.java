package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import request.Response;
import tools.DBConnection;


public class SongListHandler {
    private Response response;
    
    public Response getResponse(){
        String query = "SELECT * FROM music";
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            ResultSet rs = pdt.executeQuery();
            ArrayList<String> songs=new ArrayList<>();
            
            while(rs.next()){
                songs.add(rs.getString(2));
            }
            return new Response(songs, ResponseCode.SUCCESSFUL);
        }catch(SQLException e){}
        return new Response(null, ResponseCode.FAILURE);
    }
}
