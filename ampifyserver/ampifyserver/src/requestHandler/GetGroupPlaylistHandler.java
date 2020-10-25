package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import request.GetGroupPlaylistRequest;
import request.Response;
import tools.DBConnection;

public class GetGroupPlaylistHandler {
        private GetGroupPlaylistRequest getGroupPlaylistRequest;
    
    public GetGroupPlaylistHandler(GetGroupPlaylistRequest getGroupPlaylistRequest){
        this.getGroupPlaylistRequest = getGroupPlaylistRequest;
    }
        
    public Response getResponse(){
        try{
            String query = "SELECT * FROM group_playlists WHERE gname=?";
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1, getGroupPlaylistRequest.getGroupname());
            ResultSet rs = pdt.executeQuery();
            ArrayList<String> plaArrayList = new ArrayList<>();
            
            while(rs.next()){
                plaArrayList.add(rs.getString(2));
            }
            if(!plaArrayList.isEmpty())
                return new Response(plaArrayList, ResponseCode.SUCCESSFUL);
        }  catch(Exception e){}
        return new Response(null, ResponseCode.FAILURE);
    }
}