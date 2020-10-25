package requestHandler;

import constants.ResponseCode;
import data.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import request.Response;
import request.UserSearchRequest;
import tools.DBConnection;

public class UserSearchHandler {
    private final UserSearchRequest userSearchRequest;
    
    public UserSearchHandler(UserSearchRequest userSearchRequest){
        this.userSearchRequest = userSearchRequest;
    }
    
    //search for a specific user by his username or characters of his name and return all matching usernamesS
    public Response getResponse(){
        String query = "SELECT * FROM user WHERE LOWER(name) LIKE '" + userSearchRequest.getSearchString() +"%' "
                + "OR username LIKE '" + userSearchRequest.getSearchString()+ "%'"; //dont lower username
        try{
         PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
         ResultSet rs = pdt.executeQuery();            
         ArrayList<String> users=new ArrayList<>();    
         
         while(rs.next()){
                users.add(rs.getString(2));
             }
         if(!users.isEmpty()){
                return new Response(users, ResponseCode.SUCCESSFUL);
                 }
        } catch(SQLException e) {}
        return new Response(null, ResponseCode.FAILURE);
    }
}
