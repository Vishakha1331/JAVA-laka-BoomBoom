package request;

import constants.RequestCode;
import java.io.Serializable;

public class AddSongToGroupPlaylist extends Request implements Serializable{
    private String gname, pname, song;

    public AddSongToGroupPlaylist(String gname, String pname, String song) {
        this.gname = gname;
        this.pname = pname;
        this.song = song;
    }

    public String getGname() {
        return gname;
    }

    public String getPname() {
        return pname;
    }

    public String getSong() {
        return song;
    }
    
    @Override
    public RequestCode getRequestCode() {
        return RequestCode.ADD_SONG_TO_GROUP_PLAYLIST;
    }
}