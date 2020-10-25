/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package request;

import constants.RequestCode;
import java.io.Serializable;

/**
 *
 * @author rishi
 */
public class DownloadSongRequest extends Request implements  Serializable{
    private String songname;

    public DownloadSongRequest(String songname) {
        
        this.songname=songname;
    }
    public String getSong(){
        return this.songname;
    }
    

    @Override
    public RequestCode getRequestCode() {
        return RequestCode.DOWNLOAD_SONG_REQUEST;
    }
    
}
