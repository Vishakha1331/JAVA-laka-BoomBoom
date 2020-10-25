package requestHandler;

import constants.ResponseCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import request.HistoryRequest;
import request.Response;
import tools.DBConnection;

public class HistoryHandler {
    private final HistoryRequest historyRequest;
    
    public HistoryHandler(HistoryRequest historyRequest){
        this.historyRequest = historyRequest;
    }
    
    public Response getResponse(){
        String query = "SELECT * FROM history WHERE username=? ORDER BY date DESC";
        try{
            PreparedStatement pdt = DBConnection.connectDB().prepareStatement(query);
            pdt.setString(1,this.historyRequest.getUserName());
            ResultSet rs = pdt.executeQuery();
            ArrayList<String> histories = new ArrayList<>();
            int i=0;
            while(rs.next() && i<this.historyRequest.getNumberOfSongs()){
                histories.add(rs.getString(2));
                i++;
            }
            if(!histories.isEmpty()){
            return new Response(histories, ResponseCode.SUCCESSFUL);
            }
        } catch(SQLException e) {}
        return new Response(null, ResponseCode.FAILURE);
    }
}
