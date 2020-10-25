package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import request.GetGroupsRequest;
import request.Response;
import tools.DBConnection;

public class GetGroupsHandler {
    private GetGroupsRequest getGroupsRequest;

    public GetGroupsHandler(GetGroupsRequest getGroupsRequest) {
        this.getGroupsRequest = getGroupsRequest;
    }
    
    public Response getResponse(){
        try{
            String query= "SELECT * FROM groups WHERE username=?";
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1, getGroupsRequest.getUsername());
            ResultSet rs = pdt.executeQuery();
            
             ArrayList<String> groups = new ArrayList<>();
            while(rs.next()){
               groups.add(rs.getString(1));
            }
            if(!groups.isEmpty())
                return new Response(groups, ResponseCode.SUCCESSFUL);
        } catch(SQLException e) {}
        return new Response(null, ResponseCode.FAILURE);
    }
}
