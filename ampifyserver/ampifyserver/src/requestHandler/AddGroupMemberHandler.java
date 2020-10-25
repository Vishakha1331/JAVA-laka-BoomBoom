package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import request.AddGroupMemberRequest;
import request.Response;
import tools.DBConnection;

public class AddGroupMemberHandler {
        private AddGroupMemberRequest addGroupMemberRequest;
    
    public AddGroupMemberHandler(AddGroupMemberRequest addGroupMemberRequest){
        this.addGroupMemberRequest = addGroupMemberRequest;
    }
        
    public Response getResponse(){
        try{
            String query = "SELECT * FROM user WHERE username=?";
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1, addGroupMemberRequest.getMember());
            ResultSet rs = pdt.executeQuery();
            if(!rs.next()){
                return new Response(null, ResponseCode.FAILURE);
            }
            
            else{
            query = "SELECT * FROM groups WHERE gname=? AND username=?";
            pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1, addGroupMemberRequest.getGname());
            pdt.setString(2, addGroupMemberRequest.getMember());
            rs = pdt.executeQuery();
            
            if(rs.next()){
                return new Response(null, ResponseCode.ALREADY_EXISTS);
            }
            else{
                  query = "INSERT INTO groups(gname,username) VALUES(?,?)";
                pdt = DBConnection.connectDB().prepareStatement(query);
                pdt.setString(1, addGroupMemberRequest.getGname());
                pdt.setString(2, addGroupMemberRequest.getMember());
                pdt.executeUpdate();
                return new Response(null, ResponseCode.SUCCESSFUL);
            }
            }
        }  catch(Exception e){}
        return new Response(null, ResponseCode.FAILURE);
    }
}