package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import request.CreateGroupRequest;
import request.Response;
import tools.DBConnection;

public class CreateGroupHandler {
    private final CreateGroupRequest createGroupRequest;

    public CreateGroupHandler(CreateGroupRequest createGroupRequest) {
        this.createGroupRequest = createGroupRequest;
    }
    
    public Response getrResponse(){
        try{
            String query ="SELECT * FROM groups WHERE gname=?";
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1, createGroupRequest.getGroupName());
            ResultSet rs = pdt.executeQuery();
            
            if(rs.next()){
                return new Response(null, ResponseCode.ALREADY_EXISTS);
            }
            
            else{
                query = "INSERT INTO groups(gname,username) VALUES(?,?)";
                pdt = DBConnection.connectDB().prepareStatement(query);
                pdt.setString(1, createGroupRequest.getGroupName());
                pdt.setString(2, createGroupRequest.getMembers());
                pdt.executeUpdate();
                return new Response(null, ResponseCode.SUCCESSFUL);
            }
        } catch(SQLException e){}
        return new Response(null, ResponseCode.FAILURE);
    }
}
