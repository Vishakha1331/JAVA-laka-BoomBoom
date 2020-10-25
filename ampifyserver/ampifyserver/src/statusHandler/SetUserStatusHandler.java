package statusHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import request.Response;
import request.SetStatusRequest;
import tools.DBConnection;

public class SetUserStatusHandler {
    private final SetStatusRequest setStatusRequest;
    
    public SetUserStatusHandler(SetStatusRequest setStatusRequest){
        this.setStatusRequest = setStatusRequest;
    }
    
        public Response getResponse(){
                String query = "SELECT * FROM onlineusers WHERE username=?";
                try{
                    PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
                    pdt.setString(1, SetUserStatusHandler.this.setStatusRequest.getUserName());
                    
                    if(pdt.executeUpdate()>0){
                        query= "UPDATE onlineusers SET status=? WHERE username=?";
                        pdt = DBConnection.connectDB().prepareStatement(query);
                        pdt.setString(1, String.valueOf((SetUserStatusHandler.this.setStatusRequest.getStatusCode())));
                        pdt.setString(2,SetUserStatusHandler.this.setStatusRequest.getUserName());
                        pdt.executeUpdate();
                        return new Response(null, ResponseCode.SUCCESSFUL);
                    }
                    
                    else{
                        query = "INSERT INTO onlineusers(username, status) VALUES(?,?)";
                        pdt = DBConnection.connectDB().prepareStatement(query);
                        pdt.setString(1, SetUserStatusHandler.this.setStatusRequest.getUserName());
                        pdt.setString(2,String.valueOf((SetUserStatusHandler.this.setStatusRequest.getStatusCode())));
                        pdt.executeUpdate();
                        return new Response(null, ResponseCode.SUCCESSFUL);
                    }
                } catch(SQLException e) {}
                return new Response(null, ResponseCode.FAILURE);
            }
}
