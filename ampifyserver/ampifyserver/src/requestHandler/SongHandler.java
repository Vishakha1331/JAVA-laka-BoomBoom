package requestHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import request.SongRequest;
import request.Response;
import tools.DBConnection;


public class SongHandler {
    private final SongRequest songRequest;
    private int songplaycount;
    
    public SongHandler(SongRequest songRequest){
        this.songRequest = songRequest;
    }
    
    public void getResponse(){
        String query = "SELECT * FROM music WHERE name=?";
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1, songRequest.getSong());
            ResultSet rs = pdt.executeQuery();
            
            if(rs.next()){
                songplaycount= rs.getInt(8);
                
                            //update playcount of a song in music table
                            query = "UPDATE music SET playcount=? WHERE name=?";
                            pdt = DBConnection.connectDB().prepareStatement(query);
                            songplaycount++;
                            
                            pdt.setInt(1, SongHandler.this.songplaycount);
                            pdt.setString(2, songRequest.getSong());
                            pdt.executeUpdate();
                            
                            //--------------------------------
                            //update playcount of user's mostplayed songs
                            query = "SELECT * FROM mostplayed WHERE username=? AND song=?";
                            pdt = DBConnection.connectDB().prepareStatement(query);
                            pdt.setString(1, songRequest.getUserName());
                            pdt.setString(2, songRequest.getSong());
                            rs = pdt.executeQuery();
                            
                            if(rs.next()){
                                int playcount = rs.getInt(3);
                                playcount++;
                                query = "UPDATE mostplayed SET playcount=? WHERE username=? AND song=?";
                                pdt = DBConnection.connectDB().prepareStatement(query);
                                pdt.setInt(1, playcount);
                                pdt.setString(2, songRequest.getUserName());
                                pdt.setString(3, songRequest.getSong());
                                pdt.executeUpdate();
                            }
                                else{
                                System.out.println("mostplayed chala");
                                        query = "INSERT INTO mostplayed(username,song,playcount) VALUES(?,?,?)";
                                        pdt = DBConnection.connectDB().prepareStatement(query);
                                        
                                        pdt.setString(1, songRequest.getUserName());
                                        pdt.setString(2, songRequest.getSong());
                                        pdt.setInt(3, 1);
                                        pdt.executeUpdate();
                                        }
                            
                                      ///------------------------------------------------------
                            //insert song into user's history
                            query = "INSERT INTO history(username, song) VALUES(?,?)";
                            pdt = DBConnection.connectDB().prepareStatement(query);
                                        pdt.setString(1, songRequest.getUserName());
                                        pdt.setString(2, songRequest.getSong());
                           pdt.executeUpdate();
            }
                        } catch (SQLException ex) {
                            Logger.getLogger(SongHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                                
                    }
                
    }