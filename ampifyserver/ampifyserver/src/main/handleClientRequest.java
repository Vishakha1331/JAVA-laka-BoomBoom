package main;

import authenticationHandler.Login;
import authenticationHandler.SignUp;
import constants.RequestCode;
import constants.ResponseCode;
import constants.StatusCode;
import data.User;
import feedbackHandler.FeedbackResponse;
import feedbackHandler.GetFeedbackHandler;
import feedbackHandler.SendFeedbackHandler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import request.AddGroupMemberRequest;
import request.AddSongToGroupPlaylist;
import request.AddSongToPlaylistRequest;
import request.CreateGroupPlaylistRequest;
import request.CreateGroupRequest;
import request.CreatePlaylistRequest;
import request.GetFeedbackRequest;
import request.GetGroupMembersRequest;
import request.GetGroupPlaylistMusicRequest;
import request.GetGroupPlaylistRequest;
import request.GetGroupsRequest;
import request.GetPlaylistRequest;
import request.GetPlaylistSongsRequest;
import request.GetStatusRequest;
import request.SongRequest;
import request.HistoryRequest;
import request.LogOutRequest;
import request.LoginRequest;
import request.NewSongsRequest;
import request.RecommendationRequest;
import request.Request;
import request.Response;
import request.SearchSongRequest;
import request.SendFeedbackRequest;
import request.SetStatusRequest;
import request.SharePlaylistRequest;
import request.SignUpRequest;
import request.TrendingRequest;
import request.UserSearchRequest;
import requestHandler.AddGroupMemberHandler;
import requestHandler.AddSongToGroupPlaylistHandler;
import requestHandler.AddSongToPlaylist;
import requestHandler.CreateGroupHandler;
import requestHandler.CreateGroupPlaylistHandler;
import requestHandler.CreatePlaylistHandler;
import requestHandler.GetGroupMembersHandler;
import requestHandler.GetGroupMusicHandler;
import requestHandler.GetGroupPlaylistHandler;
import requestHandler.GetGroupsHandler;
import requestHandler.PlaylistSongHandler;
import requestHandler.HistoryHandler;
import requestHandler.NewSongsHandler;
import requestHandler.PlaylistNameHandler;
import requestHandler.RecommendationHandler;
import requestHandler.SearchSongHandler;
import requestHandler.SharePlaylistHandler;
import requestHandler.SongListHandler;
import requestHandler.SongHandler;
import requestHandler.TrendingSongsHandler;
import requestHandler.UserSearchHandler;
import statusHandler.SetUserStatusHandler;
import statusHandler.StatusResponse;
import statusHandler.GetUserStatusHandler;

public class handleClientRequest implements Runnable{
    private final Socket socket;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois ;
    
    
    public handleClientRequest(Socket socket) throws IOException{
        this.socket=socket;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }
    
    @Override
    public void run(){
        Request request = null;
        
        // running an infinite loop to get client's requests
        while(true){
            try{
                Object o= ois.readObject();
                System.out.println(o.getClass());
                // type casting object to request type, to identify different requests by their request code
                request = (Request) o;
                
            } catch (IOException | ClassNotFoundException a ) {
                System.out.println("CLIENT DISCONNECTED");
                
            return;
            } 
            //==================================
            
            //makes user online upon successful registration and return responce code as ALREADY_EXISTS if
            //username or email are already registered in database
            if(request.getRequestCode().equals(RequestCode.SIGNUP_REQUEST)){
                SignUp signUp = new SignUp((SignUpRequest) request);
                Response response = signUp.getResponse();
                
                if(response.getResponseCode().equals(ResponseCode.SUCCESSFUL)){
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //if signup is successful we would create/update his status as ONLINE
                            SetStatusRequest setStatusRequest = new SetStatusRequest();
                            setStatusRequest.setUserName(((User) response.getResponseObject()).getUserName());
                            setStatusRequest.setStatusCode(StatusCode.ONLINE);
                            SetUserStatusHandler setUserStatusHandler = new SetUserStatusHandler(setStatusRequest);
                            setUserStatusHandler.getResponse();
                        }
                    }).start();
                    
                }
                try{
                    oos.writeObject(response);
                    oos.flush();
                }catch (IOException e){}
            }
            
            
            
            else if(request.getRequestCode().equals(RequestCode.GET_GROUP_MEMBERS_REQUEST)){
                GetGroupMembersHandler getGroupMembersHandler = new GetGroupMembersHandler((GetGroupMembersRequest)request);
                Response response = getGroupMembersHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            else if(request.getRequestCode().equals(RequestCode.GET_GROUP_PLAYLIST_SONGS)){
                GetGroupMusicHandler getGroupMusicHandler = new GetGroupMusicHandler((GetGroupPlaylistMusicRequest) request);
                Response response = getGroupMusicHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(Exception e){}
            }
            
            else if(request.getRequestCode().equals(RequestCode.ADD_SONG_TO_GROUP_PLAYLIST)){
                AddSongToGroupPlaylistHandler addSongToGroupPlaylistHandler = new AddSongToGroupPlaylistHandler((AddSongToGroupPlaylist) request);
                Response response = addSongToGroupPlaylistHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            
            else if(request.getRequestCode().equals(RequestCode.CREATE_GROUP_PLAYLIST)){
                CreateGroupPlaylistHandler createGroupPlaylistHandler = new CreateGroupPlaylistHandler((CreateGroupPlaylistRequest) request);
                Response response = createGroupPlaylistHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //========================================
          else if(request.getRequestCode().equals(RequestCode.ADD_GROUP_MEMBER)){
                AddGroupMemberHandler addGroupMemberHandler = new AddGroupMemberHandler((AddGroupMemberRequest) request);
                Response response = addGroupMemberHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
          
          else if(request.getRequestCode().equals(RequestCode.GET_GROUP_PLAYLIST_REQUEST)){
                GetGroupPlaylistHandler getGroupPlaylistHandler = new GetGroupPlaylistHandler((GetGroupPlaylistRequest) request);
                Response response = getGroupPlaylistHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
          
          
            //if details are incorrect it will return failure as response code
            else if(request.getRequestCode().equals(RequestCode.LOGIN_REQUEST)) {
                Login login = new Login((LoginRequest) request);
                Response response = login.getResponse();
                
                if(response.getResponseCode().equals(ResponseCode.SUCCESSFUL)){
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //if login is successful we would create/update his status as ONLINE
                            SetStatusRequest setStatusRequest = new SetStatusRequest();
                            setStatusRequest.setUserName(((User) response.getResponseObject()).getUserName());
                            setStatusRequest.setStatusCode(StatusCode.ONLINE);
                            SetUserStatusHandler setUserStatusHandler = new SetUserStatusHandler(setStatusRequest);
                            setUserStatusHandler.getResponse();
                        }
                    }).start();
                    
                }
                try {
                    oos.writeObject(response);
                    oos.flush();
                } catch (IOException ex) {}    
            }
            //==============================================
            
            // this request will set user's status as offline
            else if(request.getRequestCode().equals(RequestCode.LOGOUT_REQUEST)) {
                SetStatusRequest setStatusRequest = new SetStatusRequest();
                setStatusRequest.setUserName(((LogOutRequest) request).getUserName());
                setStatusRequest.setStatusCode(StatusCode.OFFLINE);
                SetUserStatusHandler setUserStatusHandler = new SetUserStatusHandler(setStatusRequest);
                setUserStatusHandler.getResponse();
                break;
            }
            //==================================================
            
            
            //return FAILURE if no song exists in database, returns song names from our database
            else if(request.getRequestCode().equals(RequestCode.GET_SONGLIST)){
                SongListHandler songSender = new SongListHandler();
                Response response = songSender.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //======================================================
            
            //sorts and returns song name based on datetime they were played
            //it does not return distinct records 
            else if(request.getRequestCode().equals(RequestCode.HISTORY_REQUEST)){
                HistoryHandler historyHandler = new HistoryHandler((HistoryRequest) request);
                Response response = historyHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //=============================================
            
            //
            else if(request.getRequestCode().equals(RequestCode.GET_GROUPS_REQUEST)){
                GetGroupsHandler getGroupsHandler = new GetGroupsHandler((GetGroupsRequest)request);
                Response response= getGroupsHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //======================================
            
            
            else if(request.getRequestCode().equals(RequestCode.GET_SONG)){
                SongHandler songHandler = new SongHandler((SongRequest) request);
                Response response = songHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //==================================================
            //
            else if(request.getRequestCode().equals(RequestCode.SHARE_PLAYLIST_REQUEST)){
                SharePlaylistHandler sharePlaylistHandler = new SharePlaylistHandler((SharePlaylistRequest)request);
                Response response = sharePlaylistHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            
            //retune failure if a user does not have any playlists
            else if(request.getRequestCode().equals(RequestCode.GET_PLAYLISTS)){
                PlaylistNameHandler playlistNameHandler = new PlaylistNameHandler((GetPlaylistRequest)request);
                Response response = playlistNameHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //===============================================================
            
            //returns successful if songs are found in a playlist otherwise failure
            //returns successful if new playlist is created, if it does not already exists
            else if(request.getRequestCode().equals(RequestCode.GET_PLAYLIST_SONGS)){
                PlaylistSongHandler createPlaylistAndSongHandler = new PlaylistSongHandler((GetPlaylistSongsRequest) request);
                Response response = createPlaylistAndSongHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //===============================================
            
            //returns song's names ordered by date latest first, 
            else if(request.getRequestCode().equals(RequestCode.NEW_SONGS)){
                NewSongsHandler newSongsHandler = new NewSongsHandler((NewSongsRequest) request);
                Response response = newSongsHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //===================================================
            
            //
            else if(request.getRequestCode().equals(RequestCode.CREATE_PLAYLIST)){
                CreatePlaylistHandler createPlaylistHandler = new CreatePlaylistHandler((CreatePlaylistRequest) request);
                Response response =createPlaylistHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e){}
            }
            //================================================
            
            //
            else if(request.getRequestCode().equals(RequestCode.SEARCH_SONG)){
                SearchSongHandler searchSongHandler = new SearchSongHandler((SearchSongRequest) request);
                
                try{
                    Response response = searchSongHandler.getResponse();
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {} catch (SQLException ex) {
                    Logger.getLogger(handleClientRequest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //===================================================
            
            //we try to match searchstring given with username and users'name and if any result found it returns success
            else if(request.getRequestCode().equals(RequestCode.USER_SEARCH)){
                UserSearchHandler userSearchHandler = new UserSearchHandler((UserSearchRequest) request);
                Response response = userSearchHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //==============================================
            
            //it returns a status code online, offline and none if no info present.
            else if(request.getRequestCode().equals(RequestCode.GET_STATUS)){
                GetUserStatusHandler userStatusHandler = new GetUserStatusHandler((GetStatusRequest) request);
                StatusResponse statusResponse = userStatusHandler.getResponse();
                try{
                    oos.writeObject(statusResponse);
                    oos.flush();
                } catch(IOException e) {}
            }
            //=======================================================
            
            //sets a user's status as statuscode sent by client, online, offline, busy
            else if(request.getRequestCode().equals(RequestCode.SET_STATUS)){
                SetUserStatusHandler setUserStatusHandler = new SetUserStatusHandler((SetStatusRequest) request);
                Response response = setUserStatusHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //===================================================
            
            //return object is null and code is successful after entering given song into given playlist
            //return code is already exists if song is already in that playlist
            else if(request.getRequestCode().equals(RequestCode.ADD_SONG_TO_PLAYLIST)){
                AddSongToPlaylist addSongToPlaylist = new AddSongToPlaylist((AddSongToPlaylistRequest) request);
                Response response = addSongToPlaylist.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //============================================
            
            //return a string type arraylist containing song names matchingn user's choice of genre and language
            else if(request.getRequestCode().equals(RequestCode.RECOMMENDATION)){
                RecommendationHandler recommendationHandler = new RecommendationHandler((RecommendationRequest) request);
                Response response = recommendationHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //===========================================================
            
            //
            else if(request.getRequestCode().equals(RequestCode.CREATE_GROUP)){
                CreateGroupHandler createGroupHandler =new CreateGroupHandler((CreateGroupRequest)request);
                Response response = createGroupHandler.getrResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e){}
            }
            //returns songs based on most playcount in decreasing order
            else if(request.getRequestCode().equals(RequestCode.TRENDING)){
                TrendingSongsHandler trendingSongsHandler = new TrendingSongsHandler((TrendingRequest) request);
                Response response = trendingSongsHandler.getResponse();
                try{
                    oos.writeObject(response);
                    oos.flush();
                } catch(IOException e) {}
            }
            //============================================================
            
            //user will send his feedback for a song, like, dislike, none
            //does not return
            else if(request.getRequestCode().equals(RequestCode.SEND_FEEDBACK)){
                new SendFeedbackHandler((SendFeedbackRequest) request);
            }
            //=====================================================
            
            //return feedback code like, dislike, none for a song; object returned is null
            else if(request.getRequestCode().equals(RequestCode.GET_FEEDBACK)){
                GetFeedbackHandler getFeedbackHandler = new GetFeedbackHandler((GetFeedbackRequest) request);
                FeedbackResponse feedbackresponse = getFeedbackHandler.getFeedbackResponse();
                try{
                    oos.writeObject(feedbackresponse);
                    oos.flush();
                } catch(IOException e) {}
            }
            //================================================================
            
    }
}
}
