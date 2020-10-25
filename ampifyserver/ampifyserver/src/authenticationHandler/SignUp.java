package authenticationHandler;

import constants.ResponseCode;
import data.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import request.Response;
import request.SignUpRequest;
import tools.DBConnection;

public class SignUp{
    private final SignUpRequest signUpRequest;
    private Response response;
    
    public SignUp(SignUpRequest signUpRequest){
        this.signUpRequest = signUpRequest;
    }
    
    public Response getResponse(){
        String query = "SELECT * FROM user WHERE username='"+ this.signUpRequest.getUserName() +"' OR "
                + "email='" + this.signUpRequest.getEmail() +"'";
        
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            ResultSet rs =pdt.executeQuery();
            if(rs.next()){
                return new Response(null, ResponseCode.ALREADY_EXISTS);
            }
            else {
            query = "INSERT INTO user(name, username, email, password, genre, language) VALUES(?,?,?,?,?,?)";
            pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1,this.signUpRequest.getName());
            pdt.setString(2, this.signUpRequest.getUserName());
            pdt.setString(3,this.signUpRequest.getEmail());
            pdt.setString(4,this.signUpRequest.getPassword());
            pdt.setString(5, this.signUpRequest.getGenre());
            pdt.setString(6, this.signUpRequest.getLanguage());
            pdt.executeUpdate();
            if(true){
                System.out.println("i am inn");
                User user = new User();
                user.setUserName(this.signUpRequest.getUserName());
                user.setName(this.signUpRequest.getName());
                user.setGenre(this.signUpRequest.getGenre());
                user.setLanguage(this.signUpRequest.getLanguage());
                user.setEmail(this.signUpRequest.getEmail());
                return this.response = new Response(user, ResponseCode.SUCCESSFUL);
            }
            
            }
        } catch(SQLException e){}
       
        
        return new Response(null, ResponseCode.FAILURE);
    }
}
