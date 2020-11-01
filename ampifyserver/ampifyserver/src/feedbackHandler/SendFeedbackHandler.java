package feedbackHandler; 

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import request.SendFeedbackRequest;
import tools.DBConnection;

public class SendFeedbackHandler {
    private final SendFeedbackRequest sendFeedbackRequest;
    
    public SendFeedbackHandler(SendFeedbackRequest sendFeedbackRequest){
        this.sendFeedbackRequest = sendFeedbackRequest;
        
        
        System.out.println("=====================");
        System.out.println(this.sendFeedbackRequest.getUserName());
        System.out.println(this.sendFeedbackRequest.getSong());
        System.out.println("===========================================");
        new Thread(new Runnable() {
            @Override
            public void run(){
                //checking if song already exists for this user in feedback table
                String query = "SELECT * FROM feedback WHERE username=? AND song=?";
                try{
                    PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
                    pdt.setString(1,SendFeedbackHandler.this.sendFeedbackRequest.getUserName());
                    pdt.setString(2,SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                    ResultSet rs = pdt.executeQuery();
                    
                    
                    if(rs.next()){
                        String ExistingFeedback = rs.getString(3);
                        System.out.println("======="+ExistingFeedback+"@@@@@@@@@@");
                        if(ExistingFeedback.equals("LIKE") || ExistingFeedback.equals("DISLIKE")){
                            System.out.println("like ya dislike?.............");
                            query = "SELECT * FROM music WHERE name=?";
                            pdt = DBConnection.connectDB().prepareStatement(query);
                            pdt.setString(1,SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                            rs = pdt.executeQuery();
                            int likescount=0;
                            int dislikecount=0;
                            if(rs.next()){
                            likescount = rs.getInt(6);
                             dislikecount = rs.getInt(7);}
                            
                            if(ExistingFeedback.equals("LIKE")){
                                System.out.println("like mai hu yr............");
                            query = "UPDATE music SET likes=? WHERE name=?";
                            likescount--;
                            pdt = DBConnection.connectDB().prepareStatement(query);
                            pdt.setInt(1,likescount);
                           pdt.setString(2,SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                           pdt.executeUpdate();
                           
                           if(String.valueOf(SendFeedbackHandler.this.sendFeedbackRequest.getFeedbackCode()).equals("DISLIKE")){
                               dislikecount++;
                               query = "UPDATE music SET dislikes=? WHERE name=?";
                               pdt = DBConnection.connectDB().prepareStatement(query);
                            pdt.setInt(1,dislikecount);
                           pdt.setString(2,SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                           pdt.executeUpdate();
                           }
                            }
                            else if(ExistingFeedback.equals("DISLIKE")){
                                query="UPDATE music SET dislikes=? WHERE name=?";
                                dislikecount--;
                                pdt = DBConnection.connectDB().prepareStatement(query);
                                pdt.setInt(1,dislikecount);
                            pdt.setString(2,SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                            pdt.executeUpdate();
                            
                            if(String.valueOf(SendFeedbackHandler.this.sendFeedbackRequest.getFeedbackCode()).equals("LIKE")){
                               likescount++;
                               query = "UPDATE music SET likes=? WHERE name=?";
                               pdt = DBConnection.connectDB().prepareStatement(query);
                            pdt.setInt(1,likescount);
                           pdt.setString(2,SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                           pdt.executeUpdate();
                           }
                            }
                            
                        }
                        
                        pdt.close();
                        //if song already exists, we just update its feedback status, like, dislike, none
                        query = "UPDATE feedback SET feedback=? WHERE username=? AND song=?";
                        pdt = DBConnection.connectDB().prepareStatement(query);
                        pdt.setString(1,String.valueOf(SendFeedbackHandler.this.sendFeedbackRequest.getFeedbackCode()));
                        pdt.setString(2,SendFeedbackHandler.this.sendFeedbackRequest.getUserName());
                        pdt.setString(3,SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                        pdt.executeUpdate();
                        pdt.close();
                    }
                    else{
                        // if song isn't already present it would make an entry for it
                        query = "INSERT INTO feedback (username, song, feedback) VALUES(?,?,?)";
                        pdt = DBConnection.connectDB().prepareStatement(query);
                        
                        pdt.setString(1,SendFeedbackHandler.this.sendFeedbackRequest.getUserName());
                        pdt.setString(2,SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                        pdt.setString(3,String.valueOf(SendFeedbackHandler.this.sendFeedbackRequest.getFeedbackCode()));
                        pdt.executeUpdate();
                        pdt.close();
                        
                        query ="SELECT * FROM music WHERE name=?";
                        pdt = DBConnection.connectDB().prepareStatement(query);
                        pdt.setString(1, SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                        
                         rs =pdt.executeQuery();
                         
                         int likes=0;
                         int dislikes=0;
                         if(rs.next()){
                              likes=rs.getInt(6);
                              dislikes=rs.getInt(7);
                             
                         }
                            
                        if(String.valueOf(SendFeedbackHandler.this.sendFeedbackRequest.getFeedbackCode()).equals("LIKE")){
                           likes++;
                        }
                        else if(String.valueOf(SendFeedbackHandler.this.sendFeedbackRequest.getFeedbackCode()).equals("DISLIKE")){
                           dislikes++;
                        }
                        query="UPDATE music SET likes=? AND dislikes=? WHERE name=?";
                        pdt = DBConnection.connectDB().prepareStatement(query);
                        pdt.setInt(1, likes);
                        pdt.setInt(2, dislikes);
                        pdt.setString(3, SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                        System.out.println(pdt);
                        System.out.println(likes);
                        System.out.println(dislikes);
                        System.out.println(SendFeedbackHandler.this.sendFeedbackRequest.getSong());
                                
                        pdt.executeUpdate();
                        
                        
                    }
                } catch(SQLException e) {}
            }
        }).start();
    }
}