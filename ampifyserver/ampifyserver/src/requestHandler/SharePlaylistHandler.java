package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import request.Response;
import request.SharePlaylistRequest;
import tools.DBConnection;

/**
 *
 * @author PAVANPANDEZ
 */
public class SharePlaylistHandler {
    private final SharePlaylistRequest sharePlaylistRequest;
    
    public SharePlaylistHandler(SharePlaylistRequest sharePlaylistRequest){
        this.sharePlaylistRequest = sharePlaylistRequest;
    }
            
    public Response getResponse(){
        try{
            String query = "SELECT * FROM user WHERE username=?";
            PreparedStatement pdt =DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1, sharePlaylistRequest.getTargetUserName());
            ResultSet rs = pdt.executeQuery();
            if(!rs.next()){
                return new Response(null, ResponseCode.FAILURE);
            }
           
            else{
              pdt.close();
            query = "SELECT * FROM playlist WHERE username=? AND pname=?";
            pdt =DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1, sharePlaylistRequest.getTargetUserName());
            pdt.setString(2, sharePlaylistRequest.getPlaylistName());
            rs = pdt.executeQuery();
        
            if(rs.next()){
              return new Response(null, ResponseCode.ALREADY_EXISTS);      
            } 
        
            else{
                pdt.close();
                query="INSERT INTO playlist(username, pname) VALUES(?,?)";
                pdt =DBConnection.connectDB().prepareStatement(query);
                pdt.setString(1, sharePlaylistRequest.getTargetUserName());
                pdt.setString(2, sharePlaylistRequest.getPlaylistName());
                pdt.executeUpdate();
                pdt.close();
                
                query="INSERT INTO playlist_music(username, pname, song) SELECT '"+sharePlaylistRequest.getTargetUserName()+"', '"
                        + sharePlaylistRequest.getPlaylistName()+ "', song FROM playlist_music WHERE username='" + 
                        sharePlaylistRequest.getUserName() +"' AND pname='"+ sharePlaylistRequest.getPlaylistName()+"'";
                System.out.println(query);
                pdt =DBConnection.connectDB().prepareStatement(query);
                pdt.executeUpdate();
                pdt.close();
                return new Response(null, ResponseCode.SUCCESSFUL);
            }
            }
         } catch(SQLException e){e.printStackTrace();}
        return new Response(null, ResponseCode.FAILURE);
    }
}