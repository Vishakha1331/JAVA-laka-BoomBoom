package feedbackHandler;

import constants.FeedbackCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import request.GetFeedbackRequest;
import tools.DBConnection;

public class GetFeedbackHandler {
    private final GetFeedbackRequest getFeedbackRequest;
    
    public GetFeedbackHandler(GetFeedbackRequest getFeedbackRequest){
        this.getFeedbackRequest = getFeedbackRequest;
    }
    
    public FeedbackResponse getFeedbackResponse(){
        //searching for the given song and returning its feedback code
        String query = "SELECT * FROM feedback WHERE username=? AND song=?";
        try{            
                PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
                pdt.setString(1,GetFeedbackHandler.this.getFeedbackRequest.getUserName());
                pdt.setString(2,GetFeedbackHandler.this.getFeedbackRequest.getSong());
                ResultSet rs = pdt.executeQuery();
                
                if(rs.next()){
                    return new FeedbackResponse(null, FeedbackCode.valueOf(rs.getString(3)));
                }
        } catch (SQLException e) {}
        //if no such song exists return feedback code as none
        return new FeedbackResponse(null, FeedbackCode.NONE);
    }
}
