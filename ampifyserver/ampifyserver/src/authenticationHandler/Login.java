package authenticationHandler;

import constants.ResponseCode;
import data.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import request.LoginRequest;
import request.Response;
import tools.DBConnection;

public class Login {
    private final LoginRequest lgRequest;
    private Response response;
    
    public Login(LoginRequest loginRequest){
           this.lgRequest = loginRequest;
    }
    
    public Response getResponse(){
        String query = "SELECT * FROM user WHERE username=?  AND password=?";
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1,this.lgRequest.getUserName());
            pdt.setString(2,this.lgRequest.getPassword());
            ResultSet rs = pdt.executeQuery();//EXECUTING QUERY AND VERIFYING LOGIN
            
            if(!rs.next()){
                this.response = new Response(null, ResponseCode.FAILURE);
                pdt.close();
             return this.response;
            }
            
            User user = new User();
            user.setUserName(rs.getString(2));
            user.setName(rs.getString(1));
            user.setGenre(rs.getString(5));
            user.setLanguage(rs.getString(6));
            user.setEmail(rs.getString(3));
            this.response = new Response(user, ResponseCode.SUCCESSFUL);
            pdt.close();
             return this.response;
        }
        catch (SQLException e){}
            this.response = new Response(null, ResponseCode.FAILURE);
             return this.response;
    }
}

