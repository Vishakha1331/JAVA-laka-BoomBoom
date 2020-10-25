package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import request.GetGroupMembersRequest;
import request.Response;
import tools.DBConnection;

public class GetGroupMembersHandler {
        private GetGroupMembersRequest getGroupMembersRequest;
    
    public GetGroupMembersHandler(GetGroupMembersRequest getGroupMembersRequest){
        this.getGroupMembersRequest = getGroupMembersRequest;
    }
        
    public Response getResponse(){
        try{
            String query = "SELECT * FROM groups WHERE gname=?";
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1, getGroupMembersRequest.getGname());
            ResultSet rs = pdt.executeQuery();
            
            ArrayList<String> users = new ArrayList<>();
            while(rs.next()){
                users.add(rs.getString(2));
            }
            if(!users.isEmpty()){
                return new Response(users, ResponseCode.SUCCESSFUL);
            }
        }  catch(Exception e){}
        return new Response(null, ResponseCode.FAILURE);
    }
}