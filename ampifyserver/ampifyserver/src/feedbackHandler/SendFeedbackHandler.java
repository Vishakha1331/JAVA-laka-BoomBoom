package feedbackHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import request.SendFeedbackRequest;
import tools.DBConnection;

public class SendFeedbackHandler {
    private final SendFeedbackRequest sendFeedbackRequest;
    
    public SendFeedbackHandler(SendFeedbackRequest sendFeedbackRequest){
        this.sendFeedbackRequest = sendFeedbackRequest;
        
        new Thread(new Runnable() {
            @Override
            public void run(){
                //checking if song already exists for this user in feedback table
                String query = "SELECT * FROM feedback WHERE username=? AND sid=?";
                try{
                    PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
                    pdt.setString(1,SendFeedbackHandler.this.sendFeedbackRequest.getUserName());
                    pdt.setString(2,SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                    ResultSet rs = pdt.executeQuery();
                    
                    if(rs.next()){
                        pdt.close();
                        //if song already exists, we just update its feedback status, like, dislike, none
                        query = "UPDATE feedback SET feedback=? WHERE username=? AND sid=?";
                        pdt = DBConnection.connectDB().prepareStatement(query);
                        pdt.setString(1,String.valueOf(SendFeedbackHandler.this.sendFeedbackRequest.getFeedbackCode()));
                        pdt.setString(2,SendFeedbackHandler.this.sendFeedbackRequest.getUserName());
                        pdt.setString(1,SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                        pdt.executeQuery();
                        pdt.close();
                    }
                    else{
                        // if song isn't already present it would make an entry for it
                        query = "INSERT INTO feedback (username, sid, feedback) VALUES(?,?,?)";
                        pdt = DBConnection.connectDB().prepareStatement(query);
                        pdt.setString(3,String.valueOf(SendFeedbackHandler.this.sendFeedbackRequest.getFeedbackCode()));
                        pdt.setString(1,SendFeedbackHandler.this.sendFeedbackRequest.getUserName());
                        pdt.setString(2,SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                        pdt.executeQuery();
                        pdt.close();
                    }
                } catch(SQLException e) {}
            }
        }).start();
    }
}
