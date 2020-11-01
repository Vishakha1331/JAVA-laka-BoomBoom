/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import static main.MainClass.serverIP;
import request.DownloadSongRequest;
import request.DownloadSrtrequest;

/**
 *
 * @author rishi
 */
public class DownloadSong {
    
    private String songname;
    private final static int downloadPortNO =63423;
    
        private static Socket downloadsocket;
        private static ObjectOutputStream downloadoos;
        private static InputStream fileIS;
        private boolean online;
        
        
        public DownloadSong(String songname,boolean  online) throws FileNotFoundException, IOException {
        this.songname=songname;
        this.online=online;
        
        downloadsocket = new Socket(serverIP, downloadPortNO);
            fileIS = downloadsocket.getInputStream();
           
        downloadoos = new ObjectOutputStream(downloadsocket.getOutputStream());
            
        DownloadSongRequest downloadSongRequest =new DownloadSongRequest(songname);
        
        downloadoos.writeObject(downloadSongRequest);
        downloadoos.flush();
        
        int ii=fileIS.read();
        
        
        if(online){
            System.out.println("i am in online download");
            if(!Files.isDirectory(Paths.get("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp"))){
            new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp").mkdir();
            
        }
            
            OutputStream fileosonline=null;
            if(ii==0){
                 fileosonline =new FileOutputStream("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+songname+".mp3");
            }
            else if(ii==1){
                fileosonline =new FileOutputStream("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+songname+".mp4"); 
            }
            
            byte[] buffer =new byte[10*1024];
            int count;
          while ((count = fileIS.read(buffer)) > 0) {
              //System.out.println("i am inside while bachao");
            fileosonline.write(buffer, 0, count);
        }
            System.out.println("ho gya download");
          fileosonline.close();
          downloadsocket.close();
           
          //=========
            new DownloadSong(songname);
          //=========
        
            
        }
        else{
            System.out.println("downloading offline");
        if(!Files.isDirectory(Paths.get("C:\\Ampify_Downloaded_Songs"))){
            new File("C:\\Ampify_Downloaded_Songs").mkdir();
            
        }
        if(!Files.isDirectory(Paths.get("C:\\Ampify_Downloaded_lyrics"))){
            new File("C:\\Ampify_Downloaded_lyrics").mkdir();
            
        }
        
        OutputStream fileOS=null;
        if(ii==0){
         fileOS = new FileOutputStream("C:\\Ampify_Downloaded_Songs\\"+songname+".mp3");
        }
        else if(ii==1){
            fileOS =new FileOutputStream("C:\\Ampify_Downloaded_Songs\\"+songname+".mp4");
        }
        
        byte[] buffer = new byte[10*1024];
         
         int count;
          while ((count = fileIS.read(buffer)) > 0) {
            fileOS.write(buffer, 0, count);
        }
          //MainClass.fileIS.close();
          
         fileOS.close();
         downloadsocket.close();
         
         
 
        JOptionPane.showMessageDialog(null, "Song Downloaded");
        
        
        //=========================
        
        downloadsocket = new Socket(serverIP, downloadPortNO);
            fileIS = downloadsocket.getInputStream();
           
        downloadoos = new ObjectOutputStream(downloadsocket.getOutputStream());
            
        DownloadSrtrequest downloadSrtRequest =new DownloadSrtrequest(songname);
        
        downloadoos.writeObject(downloadSrtRequest);
        downloadoos.flush();
        
        fileOS = new FileOutputStream("C:\\Ampify_Downloaded_lyrics\\"+songname+".srt");
        
        byte[] buffers = new byte[10*1024];
         
         int count1;
          while ((count1 = fileIS.read(buffers)) > 0) {
            fileOS.write(buffers, 0, count1);
        }
          //MainClass.fileIS.close();
          
         fileOS.close();
         downloadsocket.close();
        
        //=========================
        
        }
        
    }

    public DownloadSong(String songname) {
    
        
        System.out.println("consructior shi h");
        try {
            //=========================
            
            downloadsocket = new Socket(serverIP, downloadPortNO);
            fileIS = downloadsocket.getInputStream();
           
        downloadoos = new ObjectOutputStream(downloadsocket.getOutputStream());
            
        DownloadSrtrequest downloadSrtRequest =new DownloadSrtrequest(songname);
        
        downloadoos.writeObject(downloadSrtRequest);
        downloadoos.flush();
        OutputStream fileosonline =null;
        fileosonline = new FileOutputStream("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+songname+".srt");
        
        byte[] buffers = new byte[10*1024];
         
         int count1;
          while ((count1 = fileIS.read(buffers)) > 0) {
            fileosonline.write(buffers, 0, count1);
        }
          //MainClass.fileIS.close();
          
         fileosonline.close();
         downloadsocket.close();
        } catch (IOException ex) {
            Logger.getLogger(DownloadSong.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        //=========================
            
        }
}
    
    
   

    
     
                
   

    

