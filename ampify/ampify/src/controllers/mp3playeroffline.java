/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

//importing required dependencies

import com.sun.javafx.stage.WindowCloseRequestHandler;
import constants.FeedbackCode;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
//import javafx.beans.value.ChangeListener;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.StageBuilder;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import main.PlayerOffline;
import main.QueueEditor;
import javax.swing.event.ChangeListener;
import main.DownloadSong;
import main.MainClass;
import sun.management.counter.perf.PerfLongArrayCounter;
import main.QueueEditor.*;
import request.SendFeedbackRequest;
import request.SongRequest;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javafx.scene.media.AudioEqualizer;
 
import javax.swing.JFrame;


/**
 *
 * @author rishi
 */
public class mp3playeroffline extends JFrame {

   //declare variables here
    QueueEditor queueEditor ;
    PlayerOffline playerOffline =new PlayerOffline();
    Random random =new Random();
    private Media media;
    private static MediaPlayer mediaPlayer=null;
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private boolean  isshuffle=false;
    private static ChangeListener seekListener;
    private static ChangeListener add;
    //private ArrayList<String> songarray = new ArrayList<>();
    private int playAllQueueCounter=0;
    private int songArraySize;
    private String isLooping= "LOOP_OFF";
    private boolean ismuted =false;
    private float volume=0.8F;
    private JFXPanel jfxPanel;
    private boolean online=true;
    private String nowplayingsong;
    BufferedImage bufferedImage;
    JLabel jlabel;
    BufferedReader reader = null;
    ArrayList<String> lyrictext = new ArrayList<String>();
    ArrayList<ArrayList<Integer>> timerange = new ArrayList<>();
    ArrayList<String> tempArrayList = new ArrayList<String>();
    boolean set=false;
    
    public mp3playeroffline() {
        
        initComponents();
        seekbar.setValue(0);
         
    }
    // overloaded constructor 
    public mp3playeroffline(QueueEditor qe){
        initComponents();
        seekbar.setValue(0);
        this.queueEditor=qe;
        online=false;
        likebox.setVisible(false);
        dislikebox.setVisible(false);
        downloadbutton.setVisible(false);
        onlineofflinebutton.setText("ONLINE");
    }
 //preparing toolkit for using mediaplayer of java fx
    public void toolkit() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFXPanel(); // this will prepare JavaFX toolkit and environment
            }
            });
    }
    //class which will play music in online mode(basic-3)
    private void mp3playeronline(int i){
        
        set=false;
       nowplayingsong=queueEditor.getNowplayinglist().get(i);
       System.out.println("========================"+nowplayingsong+"222222222222222222222222222222");
        SongRequest songRequest =new SongRequest();
        songRequest.setSong(nowplayingsong);
        songRequest.setUserName(MainClass.user.getUserName());
        
        try {
            MainClass.oos.writeObject(songRequest);
            MainClass.oos.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
        }
        ///READING SUBTITLES  =====================================
        
//        new Thread(new Runnable() {
//           @Override
//           public void run() {
//               //DownloadSong dss =new DownloadSong(queueEditor.getNowplayinglist().get(i));
//               
//           }
//       }).start();
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
//        }
         
        
        
       // to play mp4,already in temp folder
       if(  new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".mp4").exists()){
            System.out.println("i am mp4 online");
           videopanel.removeAll();
            videopanel.repaint();
            jfxPanel= new JFXPanel();
            jfxPanel.setSize(videopanel.getHeight(), videopanel.getWidth());
            //jfxPanel.setBackground(Color.black);
            videopanel.setLayout(new BorderLayout());
            videopanel.add(jfxPanel,BorderLayout.CENTER);
            Platform.setImplicitExit(false);
            
            //================================================
        if(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".srt").exists()){
        File srtFile = new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".srt");
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(srtFile), "UTF-8"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        int ii = 0, value1,value2=0;
        String temp, line = null;    
        String[] split, converter = null;
        
            try {
                while((line = reader.readLine())!=null){
                    temp="";
                    split = null;
                    tempArrayList.clear();
                    
                    split = reader.readLine().split(" --> ");
                    converter = split[0].split(":");
                    value1 = Integer.valueOf(converter[0])*3600+Integer.valueOf(converter[1])*60 + Integer.valueOf(converter[2].split(",")[0]);
                    converter = split[1].split(":");
                    value2  = Integer.valueOf(converter[0])*3600+Integer.valueOf(converter[1])*60 + Integer.valueOf(converter[2].split(",")[0]);
                    
                    timerange.add(ii, new ArrayList<>());
                    timerange.get(ii).add(0, value1);
                    timerange.get(ii).add(1, value2);
                    
                    while(!(line = reader.readLine()).equals("")){
                        temp += line +" "; }
                    
                    lyrictext.add(ii, temp);
                    ii++;
                }   } catch (IOException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //=================================
        
            
            Platform.runLater(new Runnable() {
            // thread to run the music,(advanced-11)
            @Override
            public void run() {
                media = new Media(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".mp4").toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(mediaPlayer);
                
                                    mediaView.setFitHeight(videopanel.getHeight());
                                    mediaView.setFitWidth(videopanel.getWidth());
	                    jfxPanel.setScene(new Scene(new Group(mediaView)));  
                                    
                                     System.out.println(mediaView.getFitWidth());
                                     System.out.println(mediaView.getFitHeight());
//            mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);

            mediaPlayer.setAutoPlay(false);
            mediaPlayer.play();
            
            mediaPlayer.setOnReady(() -> {
                setequaliseronplay();
                
                //==============================
                mediaPlayer.currentTimeProperty().addListener(l->{
                            if(mediaPlayer!=null){
                Duration currentTime = mediaPlayer.getCurrentTime();
                 int mediavalue = (int) currentTime.toSeconds();
                 int lyricvalue1, lyricvalue2, j =0, flag=0; 

                 for(j=0; j<timerange.size();j++){
                               lyricvalue1 = timerange.get(j).get(0);
                                lyricvalue2 = timerange.get(j).get(1);

                                if(lyricvalue1<= mediavalue && mediavalue<=lyricvalue2){
                                    flag=1;
                                    break;
                                }
                        }

                 if(flag==1)
                        subtitledisplaybox.setText(lyrictext.get(j));
                 else
                        subtitledisplaybox.setText("");
                 
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    
                }
                            }
            });
                //==============================
             System.out.println((int) media.getDuration().toSeconds());
             seekbar.setMaximum((int) media.getDuration().toSeconds());
             mediaPlayer.currentTimeProperty().addListener(l-> {
                 
                 seekbar.removeChangeListener(seekListener);
                 Duration currentTime = mediaPlayer.getCurrentTime();
                 
                 int value = (int) currentTime.toSeconds();
                 seekbar.setValue(value);
                 seekbar.addChangeListener(seekListener);
                 
         if(set==false){
        mediaPlayer.setOnEndOfMedia(() -> {
              
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_OFF":
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=0 && playAllQueueCounter<queueEditor.getNowplayinglist().size()){
                        
                        mp3playeronline(playAllQueueCounter);
                        playAllQueueCounter++;
                    }   break;
                    
                case "LOOP_ONE":
                    seekbar.setValue(0);
                    
                    break;
                    
                case "LOOP_ALL":
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=queueEditor.getNowplayinglist().size()){
                        playAllQueueCounter = 0; }
                    
                    mp3playeronline(playAllQueueCounter);
                     playAllQueueCounter++;
                    break;
            }
            
        });}
             });});
            }
        });
       }
       //play mp3,already inside temp folder
       else if(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".mp3").exists()){
           //System.out.println("getting http media");
           //new File("C:\\Ampify_Downloaded_Songs\\"+queueEditor.getNowplayinglist().get(i)+".mp3").toURI().toString()
           //http://localhost:7777/a.mp3
           media = new Media(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".mp3").toURI().toString());
           
           System.out.println("i am mp3");
           //================================================
        if(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".srt").exists()){
        File srtFile = new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".srt");
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(srtFile), "UTF-8"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        int ii = 0, value1,value2=0;
        String temp, line = null;    
        String[] split, converter = null;
        
            try {
                while((line = reader.readLine())!=null){
                    temp="";
                    split = null;
                    tempArrayList.clear();
                    
                    split = reader.readLine().split(" --> ");
                    converter = split[0].split(":");
                    value1 = Integer.valueOf(converter[0])*3600+Integer.valueOf(converter[1])*60 + Integer.valueOf(converter[2].split(",")[0]);
                    converter = split[1].split(":");
                    value2  = Integer.valueOf(converter[0])*3600+Integer.valueOf(converter[1])*60 + Integer.valueOf(converter[2].split(",")[0]);
                    
                    timerange.add(ii, new ArrayList<>());
                    timerange.get(ii).add(0, value1);
                    timerange.get(ii).add(1, value2);
                    if(reader!=null){
                    while(!(line = reader.readLine()).equals("")){
                        temp += line +" "; }
                    }
                    
                    lyrictext.add(ii, temp);
                    ii++;
                }   } catch (IOException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //=================================
        
           
           
           try {
               //bufferedImage=(BufferedImage) ImageIO.read(new File("./mp3logo.PNG")).getScaledInstance(videopanel.getWidth(),videopanel.getHeight(),Image.SCALE_SMOOTH);
               ImageIcon imageIcon =new ImageIcon(ImageIO.read(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\src\\controllers\\mp3logo.PNG")).getScaledInstance(videopanel.getWidth(),videopanel.getHeight(),Image.SCALE_SMOOTH));
               videopanel.removeAll();
               videopanel.setBackground(Color.yellow);
               videopanel.repaint();
               
               
               //jlabel.setSize(videopanel.getWidth(),videopanel.getHeight());
               //jlabel.setIcon(imageIcon);
               //videopanel.add(jlabel);
               
               
           } catch (IOException ex) {
               Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
           }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        
           System.out.println("yellow screen");
        
        mediaPlayer.setOnReady(() -> {
            setequaliseronplay();
            //==============================
                mediaPlayer.currentTimeProperty().addListener(l->{
                            if(mediaPlayer!=null){
                Duration currentTime = mediaPlayer.getCurrentTime();
                 int mediavalue = (int) currentTime.toSeconds();
                 int lyricvalue1, lyricvalue2, j =0, flag=0; 

                 for(j=0; j<timerange.size();j++){
                               lyricvalue1 = timerange.get(j).get(0);
                                lyricvalue2 = timerange.get(j).get(1);

                                if(lyricvalue1<= mediavalue && mediavalue<=lyricvalue2){
                                    flag=1;
                                    break;
                                }
                        }

                 if(flag==1)
                        subtitledisplaybox.setText(lyrictext.get(j));
                 else
                        subtitledisplaybox.setText("");
                 
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    
                }
                            }
            });
                //==============================
             System.out.println((int) media.getDuration().toSeconds());
             seekbar.setMaximum((int) media.getDuration().toSeconds());
             mediaPlayer.currentTimeProperty().addListener(l-> {
                 
                 seekbar.removeChangeListener(seekListener);
                 Duration currentTime = mediaPlayer.getCurrentTime();
                 
                 int value = (int) currentTime.toSeconds();
                 seekbar.setValue(value);
                 seekbar.addChangeListener(seekListener);
                 
                 if(set==false){
        mediaPlayer.setOnEndOfMedia(() -> {
                System.out.println("end of media");
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_OFF":
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=0 && playAllQueueCounter<queueEditor.getNowplayinglist().size()){
                        mp3playeronline(playAllQueueCounter);
                        playAllQueueCounter++;
                    }   break;
                    
                case "LOOP_ONE":
                    seekbar.setValue(0);
                    break;
                    
                case "LOOP_ALL":
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=queueEditor.getNowplayinglist().size()){
                        playAllQueueCounter = 0; }
                    
                    mp3playeronline(playAllQueueCounter);
                     playAllQueueCounter++;
                    break;
            }
            
        });}
             });});
       }
       
       //play file, which has to be asked to server
       else{
           
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           DownloadSong ds =new DownloadSong(queueEditor.getNowplayinglist().get(i), true);
                       } catch (IOException ex) {
                           Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       
                   }
               }).start();
               
               
           
           try {
               Thread.sleep(1000);
           } catch (InterruptedException ex) {
               Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           //playing mp3 file
           if(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".mp3").exists()){
             media = new Media(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".mp3").toURI().toString());
           
           
           System.out.println("i am mp3 online");
           
           //================================================
        if(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".srt").exists()){
        File srtFile = new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".srt");
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(srtFile), "UTF-8"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        int ii = 0, value1,value2=0;
        String temp, line = null;    
        String[] split, converter = null;
        
            try {
                while((line = reader.readLine())!=null){
                    temp="";
                    split = null;
                    tempArrayList.clear();
                    
                    split = reader.readLine().split(" --> ");
                    converter = split[0].split(":");
                    value1 = Integer.valueOf(converter[0])*3600+Integer.valueOf(converter[1])*60 + Integer.valueOf(converter[2].split(",")[0]);
                    converter = split[1].split(":");
                    value2  = Integer.valueOf(converter[0])*3600+Integer.valueOf(converter[1])*60 + Integer.valueOf(converter[2].split(",")[0]);
                    
                    timerange.add(ii, new ArrayList<>());
                    timerange.get(ii).add(0, value1);
                    timerange.get(ii).add(1, value2);
                    if(reader!=null){
                    while(!(line = reader.readLine()).equals("")){
                        temp += line +" "; }}
                    
                    lyrictext.add(ii, temp);
                    ii++;
                }   } catch (IOException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //=================================
        
           try {
               //bufferedImage=(BufferedImage) ImageIO.read(new File("./mp3logo.PNG")).getScaledInstance(videopanel.getWidth(),videopanel.getHeight(),Image.SCALE_SMOOTH);
               ImageIcon imageIcon =new ImageIcon(ImageIO.read(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\src\\controllers\\mp3logo.PNG")).getScaledInstance(videopanel.getWidth(),videopanel.getHeight(),Image.SCALE_SMOOTH));
               videopanel.removeAll();
               videopanel.setBackground(Color.yellow);
               videopanel.repaint();
               
               
               //jlabel.setSize(videopanel.getWidth(),videopanel.getHeight());
               //jlabel.setIcon(imageIcon);
               //videopanel.add(jlabel);
               
               
           } catch (IOException ex) {
               Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
           }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
           System.out.println("yellow screen");
        
        mediaPlayer.setOnReady(() -> {
            setequaliseronplay();
            //==============================
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
                //===============================    
                mediaPlayer.currentTimeProperty().addListener(l->{
                            if(mediaPlayer!=null){
                Duration currentTime = mediaPlayer.getCurrentTime();
                 int mediavalue = (int) currentTime.toSeconds();
                 int lyricvalue1, lyricvalue2, j =0, flag=0; 

                 for(j=0; j<timerange.size();j++){
                               lyricvalue1 = timerange.get(j).get(0);
                                lyricvalue2 = timerange.get(j).get(1);

                                if(lyricvalue1<= mediavalue && mediavalue<=lyricvalue2){
                                    flag=1;
                                    break;
                                }
                        }

                 if(flag==1)
                        subtitledisplaybox.setText(lyrictext.get(j));
                 else
                        subtitledisplaybox.setText("");
                 
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    
                }
                            }
            });
                //==============================
//                }
//            }).start();
                
             System.out.println((int) media.getDuration().toSeconds());
             seekbar.setMaximum((int) media.getDuration().toSeconds());
             mediaPlayer.currentTimeProperty().addListener(l-> {
                 
                 seekbar.removeChangeListener(seekListener);
                 Duration currentTime = mediaPlayer.getCurrentTime();
                 
                 int value = (int) currentTime.toSeconds();
                 seekbar.setValue(value);
                 seekbar.addChangeListener(seekListener);
                 
            if(set==false){     
        mediaPlayer.setOnEndOfMedia(() -> {
                System.out.println("end of media");
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_OFF":
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=0 && playAllQueueCounter<queueEditor.getNowplayinglist().size()){
                        mp3playeronline(playAllQueueCounter);
                        playAllQueueCounter++;
                    }   break;
                    
                case "LOOP_ONE":
                    seekbar.setValue(0);
                    break;
                    
                case "LOOP_ALL":
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=queueEditor.getNowplayinglist().size()){
                        playAllQueueCounter = 0; }
                    
                    mp3playeronline(playAllQueueCounter);
                     playAllQueueCounter++;
                    break;
            }
            
        });
            }
             });});
       }
           //playing mp4 file in online mode
           else if(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".mp4").exists()){
               
               
               System.out.println("i am mp4 online");
               //================================================
        if(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".srt").exists()){
        File srtFile = new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".srt");
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(srtFile), "UTF-8"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        int ii = 0, value1,value2=0;
        String temp, line = null;    
        String[] split, converter = null;
        
            try {
                while((line = reader.readLine())!=null){
                    temp="";
                    split = null;
                    tempArrayList.clear();
                    
                    split = reader.readLine().split(" --> ");
                    converter = split[0].split(":");
                    value1 = Integer.valueOf(converter[0])*3600+Integer.valueOf(converter[1])*60 + Integer.valueOf(converter[2].split(",")[0]);
                    converter = split[1].split(":");
                    value2  = Integer.valueOf(converter[0])*3600+Integer.valueOf(converter[1])*60 + Integer.valueOf(converter[2].split(",")[0]);
                    
                    timerange.add(ii, new ArrayList<>());
                    timerange.get(ii).add(0, value1);
                    timerange.get(ii).add(1, value2);
                    
                    while(!(line = reader.readLine()).equals("")){
                        temp += line +" "; }
                    
                    lyrictext.add(ii, temp);
                    ii++;
                }   } catch (IOException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //=================================
        
           videopanel.removeAll();
            videopanel.repaint();
            jfxPanel= new JFXPanel();
            jfxPanel.setSize(videopanel.getHeight(), videopanel.getWidth());
            //jfxPanel.setBackground(Color.black);
            videopanel.setLayout(new BorderLayout());
            videopanel.add(jfxPanel,BorderLayout.CENTER);
            Platform.setImplicitExit(false);
            Platform.runLater(new Runnable() {

            @Override
            public void run() {
                media = new Media(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".mp4").toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(mediaPlayer);
                
                                    mediaView.setFitHeight(videopanel.getHeight());
                                    mediaView.setFitWidth(videopanel.getWidth());
	                    jfxPanel.setScene(new Scene(new Group(mediaView)));  
                                    
                                     System.out.println(mediaView.getFitWidth());
                                     System.out.println(mediaView.getFitHeight());
//            mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);

            mediaPlayer.setAutoPlay(false);
            mediaPlayer.play();
            
            mediaPlayer.setOnReady(() -> {
                setequaliseronplay();
                
                //==============================
//                new Thread(new Runnable() {
//                @Override
//                public void run() {
                //===============================    
                mediaPlayer.currentTimeProperty().addListener(l->{
                            if(mediaPlayer!=null){
                Duration currentTime = mediaPlayer.getCurrentTime();
                 int mediavalue = (int) currentTime.toSeconds();
                 int lyricvalue1, lyricvalue2, j =0, flag=0; 

                 for(j=0; j<timerange.size();j++){
                               lyricvalue1 = timerange.get(j).get(0);
                                lyricvalue2 = timerange.get(j).get(1);

                                if(lyricvalue1<= mediavalue && mediavalue<=lyricvalue2){
                                    flag=1;
                                    break;
                                }
                        }

                 if(flag==1)
                        subtitledisplaybox.setText(lyrictext.get(j));
                 else
                        subtitledisplaybox.setText("");
                 
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    
                }
                            }
            });
                //==============================
//                }
//            }).start();
//            
                //==============================
             System.out.println((int) media.getDuration().toSeconds());
             seekbar.setMaximum((int) media.getDuration().toSeconds());
             mediaPlayer.currentTimeProperty().addListener(l-> {
                 
                 seekbar.removeChangeListener(seekListener);
                 Duration currentTime = mediaPlayer.getCurrentTime();
                 
                 int value = (int) currentTime.toSeconds();
                 seekbar.setValue(value);
                 seekbar.addChangeListener(seekListener);
                 
         if(set==false){
        mediaPlayer.setOnEndOfMedia(() -> {
               
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_OFF":
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=0 && playAllQueueCounter<queueEditor.getNowplayinglist().size()){
                        
                        mp3playeronline(playAllQueueCounter);
                        playAllQueueCounter++;
                    }   break;
                    
                case "LOOP_ONE":
                    seekbar.setValue(0);
                    
                    break;
                    
                case "LOOP_ALL":
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=queueEditor.getNowplayinglist().size()){
                        playAllQueueCounter = 0; }
                    
                    mp3playeronline(playAllQueueCounter);
                     playAllQueueCounter++;
                    break;
            }
            
        });
         }
             });});
            }
        });
           }
           
       }
       
       seekListener = (ChangeEvent event) -> {
             mediaPlayer.seek(Duration.seconds(seekbar.getValue()));
       };
         
        seekbar.addChangeListener(seekListener);   
                }
    
    
    //playing sog in offline mode(downloaded song play)
     private void mp3player(int i){
         set=false;
         ///READING SUBTITLES  =====================================
        if(new File("C:\\Ampify_Downloaded_lyrics\\"+queueEditor.getNowplayinglist().get(i)+".srt").exists()){
        File srtFile = new File("C:\\Ampify_Downloaded_lyrics\\"+queueEditor.getNowplayinglist().get(i)+".srt");
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(srtFile), "UTF-8"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        int ii = 0, value1,value2=0;
        String temp, line = null;    
        String[] split, converter = null;
        
            try {
                while((line = reader.readLine())!=null){
                    temp="";
                    split = null;
                    tempArrayList.clear();
                    
                    split = reader.readLine().split(" --> ");
                    converter = split[0].split(":");
                    value1 = Integer.valueOf(converter[0])*3600+Integer.valueOf(converter[1])*60 + Integer.valueOf(converter[2].split(",")[0]);
                    converter = split[1].split(":");
                    value2  = Integer.valueOf(converter[0])*3600+Integer.valueOf(converter[1])*60 + Integer.valueOf(converter[2].split(",")[0]);
                    
                    timerange.add(ii, new ArrayList<>());
                    timerange.get(ii).add(0, value1);
                    timerange.get(ii).add(1, value2);
                    
                    while(!(line = reader.readLine()).equals("")){
                        temp += line +" "; }
                    
                    lyrictext.add(ii, temp);
                    ii++;
                }   } catch (IOException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //=================================
         
         
         
       //play downloaded mp4 song
       if(  new File("C:\\Ampify_Downloaded_Songs\\"+queueEditor.getNowplayinglist().get(i)+".mp4").exists()){
            System.out.println("i am mp4");
           videopanel.removeAll();
            videopanel.repaint();
            jfxPanel= new JFXPanel();
            jfxPanel.setSize(videopanel.getHeight(), videopanel.getWidth());
            //jfxPanel.setBackground(Color.black);
            videopanel.setLayout(new BorderLayout());
            videopanel.add(jfxPanel,BorderLayout.CENTER);
            Platform.setImplicitExit(false);
            Platform.runLater(new Runnable() {

            @Override
            public void run() {
                media = new Media(new File("C:\\Ampify_Downloaded_Songs\\"+queueEditor.getNowplayinglist().get(i)+".mp4").toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(mediaPlayer);
                
                                    mediaView.setFitHeight(videopanel.getHeight());
                                    mediaView.setFitWidth(videopanel.getWidth());
	                    jfxPanel.setScene(new Scene(new Group(mediaView)));  
                                    
                                     System.out.println(mediaView.getFitWidth());
                                     System.out.println(mediaView.getFitHeight());
//            mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);

            mediaPlayer.setAutoPlay(false);
            mediaPlayer.play();
            
            mediaPlayer.setOnReady(() -> {
                setequaliseronplay();
                
                //==============================
//                new Thread(new Runnable() {
//                @Override
//                public void run() {
                //===============================    
                mediaPlayer.currentTimeProperty().addListener(l->{
                            if(mediaPlayer!=null){
                Duration currentTime = mediaPlayer.getCurrentTime();
                 int mediavalue = (int) currentTime.toSeconds();
                 int lyricvalue1, lyricvalue2, j =0, flag=0; 

                 for(j=0; j<timerange.size();j++){
                               lyricvalue1 = timerange.get(j).get(0);
                                lyricvalue2 = timerange.get(j).get(1);

                                if(lyricvalue1<= mediavalue && mediavalue<=lyricvalue2){
                                    flag=1;
                                    break;
                                }
                        }

                 if(flag==1)
                        subtitledisplaybox.setText(lyrictext.get(j));
                 else
                        subtitledisplaybox.setText("");
                 
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    
                }
                            }
            });
                //==============================
//                }
//            }).start();
            
                //==============================
             System.out.println((int) media.getDuration().toSeconds());
             seekbar.setMaximum((int) media.getDuration().toSeconds());
             mediaPlayer.currentTimeProperty().addListener(l-> {
                 
                 seekbar.removeChangeListener(seekListener);
                 Duration currentTime = mediaPlayer.getCurrentTime();
                 
                 int value = (int) currentTime.toSeconds();
                 seekbar.setValue(value);
                 seekbar.addChangeListener(seekListener);
                 
         if(set==false){
        mediaPlayer.setOnEndOfMedia(() -> {
               
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_OFF":
                    
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=0 && playAllQueueCounter<queueEditor.getNowplayinglist().size()){
                        mp3player(playAllQueueCounter);
                        playAllQueueCounter++;
                    }   break;
                    
                case "LOOP_ONE":
                    seekbar.setValue(0);
                    
                    break;
                    
                case "LOOP_ALL":
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=queueEditor.getNowplayinglist().size()){
                        playAllQueueCounter = 0; }
                    
                    mp3player(playAllQueueCounter);
                     playAllQueueCounter++;
                    break;
            }
            
        });}
             });});
            }
        });
       }
       //play downlaoded mp3 song
       else if(new File("C:\\Ampify_Downloaded_Songs\\"+queueEditor.getNowplayinglist().get(i)+".mp3").exists()){
           //System.out.println("getting http media");
           //new File("C:\\Ampify_Downloaded_Songs\\"+queueEditor.getNowplayinglist().get(i)+".mp3").toURI().toString()
           //http://localhost:7777/a.mp3
           media = new Media(new File("C:\\Ampify_Downloaded_Songs\\"+queueEditor.getNowplayinglist().get(i)+".mp3").toURI().toString());
           
           System.out.println("i am mp3");
           try {
               //bufferedImage=(BufferedImage) ImageIO.read(new File("./mp3logo.PNG")).getScaledInstance(videopanel.getWidth(),videopanel.getHeight(),Image.SCALE_SMOOTH);
               ImageIcon imageIcon =new ImageIcon(ImageIO.read(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\src\\controllers\\mp3logo.PNG")).getScaledInstance(videopanel.getWidth(),videopanel.getHeight(),Image.SCALE_SMOOTH));
               videopanel.removeAll();
               videopanel.setBackground(Color.yellow);
               videopanel.repaint();
               
               
               //jlabel.setSize(videopanel.getWidth(),videopanel.getHeight());
               //jlabel.setIcon(imageIcon);
               //videopanel.add(jlabel);
               
               
           } catch (IOException ex) {
               Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
           }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
           System.out.println("yellow screen");
        
        mediaPlayer.setOnReady(() -> {
            setequaliseronplay();
            //==============================
//                new Thread(new Runnable() {
//                @Override
//                public void run() {
                //===============================    
                mediaPlayer.currentTimeProperty().addListener(l->{
                  if( mediaPlayer!=null){
                Duration currentTime = mediaPlayer.getCurrentTime();
                 int mediavalue = (int) currentTime.toSeconds();
                 int lyricvalue1, lyricvalue2, j =0, flag=0; 

                 for(j=0; j<timerange.size();j++){
                               lyricvalue1 = timerange.get(j).get(0);
                                lyricvalue2 = timerange.get(j).get(1);

                                if(lyricvalue1<= mediavalue && mediavalue<=lyricvalue2){
                                    flag=1;
                                    break;
                                }
                        }

                 if(flag==1)
                        subtitledisplaybox.setText(lyrictext.get(j));
                 else
                        subtitledisplaybox.setText("");
                 
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    
                }
                  }
            });
                //==============================
//                }
//            }).start();
            
                //==============================
             System.out.println((int) media.getDuration().toSeconds());
             seekbar.setMaximum((int) media.getDuration().toSeconds());
             mediaPlayer.currentTimeProperty().addListener(l-> {
                 
                 seekbar.removeChangeListener(seekListener);
                 Duration currentTime = mediaPlayer.getCurrentTime();
                 
                 int value = (int) currentTime.toSeconds();
                 seekbar.setValue(value);
                 seekbar.addChangeListener(seekListener);
                 
            if(set==false){     
        mediaPlayer.setOnEndOfMedia(() -> {
                System.out.println("end of media");
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_OFF":
                    mediaPlayer.dispose();
                {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=0 && playAllQueueCounter<queueEditor.getNowplayinglist().size()){
                        mp3player(playAllQueueCounter);
                        playAllQueueCounter++;
                    }   break;
                    
                case "LOOP_ONE":
                    seekbar.setValue(0);
                    break;
                    
                case "LOOP_ALL":
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=queueEditor.getNowplayinglist().size()){
                        playAllQueueCounter = 0; }
                    
                    mp3player(playAllQueueCounter);
                     playAllQueueCounter++;
                    break;
            }
            
        });
            }
             });});
       }
       
       // add a change listener to seekbaar
                 seekListener = (ChangeEvent event) -> {
             mediaPlayer.seek(Duration.seconds(seekbar.getValue()));
       };
         
        seekbar.addChangeListener(seekListener);   
                }

//    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        mainpanelmp3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        offlineplaybutton = new javax.swing.JPanel();
        seekbar = new javax.swing.JSlider();
        offlinevolumedownbutton = new javax.swing.JLabel();
        offlinevolumeupbutton = new javax.swing.JLabel();
        offlinepausebutton = new javax.swing.JLabel();
        offlinestopbutton = new javax.swing.JLabel();
        offlineloopbutton = new javax.swing.JLabel();
        offlineplaynextbutton = new javax.swing.JLabel();
        offlineplaypreviousbutton = new javax.swing.JLabel();
        offlineshufflebutton = new javax.swing.JLabel();
        offlinemutebutton = new javax.swing.JLabel();
        equaliserentrybutton = new javax.swing.JButton();
        downloadbutton = new javax.swing.JButton();
        onlineofflinebutton = new javax.swing.JButton();
        offlineplaybt = new javax.swing.JLabel();
        songlength = new javax.swing.JLabel();
        likebox = new javax.swing.JCheckBox();
        dislikebox = new javax.swing.JCheckBox();
        equaliserpanel = new javax.swing.JPanel();
        e1 = new javax.swing.JSlider();
        e3 = new javax.swing.JSlider();
        e5 = new javax.swing.JSlider();
        e2 = new javax.swing.JSlider();
        e4 = new javax.swing.JSlider();
        e6 = new javax.swing.JSlider();
        equaliserpresetcombobox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        setequaliserbutton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        subtitledisplaybox = new javax.swing.JTextArea();
        videopanel = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closehoja(evt);
            }
        });

        mainpanelmp3.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1003, 250));
        jPanel2.setLayout(new java.awt.CardLayout());

        offlineplaybutton.setBackground(new java.awt.Color(51, 51, 255));

        offlinevolumedownbutton.setText("jLabel1");
        offlinevolumedownbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                offlinevolumedownbuttonMouseClicked(evt);
            }
        });

        offlinevolumeupbutton.setText("jLabel2");
        offlinevolumeupbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                offlinevolumeupbuttonMouseClicked(evt);
            }
        });

        offlinepausebutton.setText("PAUSE/RESUME");
        offlinepausebutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                offlinepausebuttonMouseClicked(evt);
            }
        });

        offlinestopbutton.setText("STOP");
        offlinestopbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                offlinestopbuttonMouseClicked(evt);
            }
        });

        offlineloopbutton.setText("LOOP");
        offlineloopbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                offlineloopbuttonMouseClicked(evt);
            }
        });

        offlineplaynextbutton.setText("PLAYNEXT");
        offlineplaynextbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                offlineplaynextbuttonMouseClicked(evt);
            }
        });

        offlineplaypreviousbutton.setText("PLAY PREVIOUS");
        offlineplaypreviousbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                offlineplaypreviousbuttonMouseClicked(evt);
            }
        });

        offlineshufflebutton.setText("SHUFFLE");
        offlineshufflebutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                offlineshufflebuttonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                offlineshufflebuttonMouseEntered(evt);
            }
        });

        offlinemutebutton.setText("MUTE");
        offlinemutebutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                offlinemutebuttonMouseClicked(evt);
            }
        });

        equaliserentrybutton.setText("EQUALISER");
        equaliserentrybutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                equaliserentrybuttonMouseClicked(evt);
            }
        });

        downloadbutton.setText("DOWNLOAD");
        downloadbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadbuttonActionPerformed(evt);
            }
        });

        onlineofflinebutton.setText("ONLINE");
        onlineofflinebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlineofflinebuttonActionPerformed(evt);
            }
        });

        offlineplaybt.setText("PLAY");
        offlineplaybt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                offlineplaybtMouseClicked(evt);
            }
        });

        songlength.setText("00:00");

        likebox.setText("LIKE");
        likebox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                likeboxActionPerformed(evt);
            }
        });

        dislikebox.setText("DISLIKE");
        dislikebox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dislikeboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout offlineplaybuttonLayout = new javax.swing.GroupLayout(offlineplaybutton);
        offlineplaybutton.setLayout(offlineplaybuttonLayout);
        offlineplaybuttonLayout.setHorizontalGroup(
            offlineplaybuttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(offlineplaybuttonLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(seekbar, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(songlength)
                .addGap(47, 47, 47)
                .addComponent(offlinevolumedownbutton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addComponent(offlinevolumeupbutton)
                .addGap(42, 42, 42))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, offlineplaybuttonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(equaliserentrybutton)
                .addGap(64, 64, 64)
                .addComponent(offlineplaybt)
                .addGap(126, 126, 126)
                .addComponent(offlineshufflebutton)
                .addGap(18, 18, 18)
                .addComponent(offlineplaypreviousbutton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(offlineloopbutton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(offlinepausebutton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(offlinestopbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(offlineplaynextbutton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(offlinemutebutton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(offlineplaybuttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(likebox)
                    .addComponent(dislikebox))
                .addGap(63, 63, 63)
                .addGroup(offlineplaybuttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(onlineofflinebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(downloadbutton))
                .addGap(34, 34, 34))
        );
        offlineplaybuttonLayout.setVerticalGroup(
            offlineplaybuttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(offlineplaybuttonLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(offlineplaybuttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(offlineplaybuttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(offlinevolumeupbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(songlength))
                    .addComponent(offlinevolumedownbutton)
                    .addComponent(seekbar, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addGap(53, 53, 53)
                .addGroup(offlineplaybuttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(offlinepausebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(offlinestopbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(offlineloopbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(offlineplaynextbutton)
                    .addComponent(offlineplaypreviousbutton)
                    .addComponent(offlineshufflebutton)
                    .addComponent(offlinemutebutton)
                    .addComponent(equaliserentrybutton)
                    .addComponent(downloadbutton)
                    .addComponent(offlineplaybt)
                    .addComponent(likebox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(offlineplaybuttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(onlineofflinebutton)
                    .addComponent(dislikebox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(offlineplaybutton, "card6");

        equaliserpanel.setBackground(new java.awt.Color(204, 255, 204));

        equaliserpresetcombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "dance", "fullbass", "fullbasstreble", "fulltreble", "headphones", "party", "pop", "rock", "soft", "softrock", "techno" }));

        jButton1.setText("PLAY BUTTONS");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        setequaliserbutton.setText("SET EQUALISER");
        setequaliserbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setequaliserbuttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout equaliserpanelLayout = new javax.swing.GroupLayout(equaliserpanel);
        equaliserpanel.setLayout(equaliserpanelLayout);
        equaliserpanelLayout.setHorizontalGroup(
            equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(equaliserpanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(e5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(e4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(equaliserpanelLayout.createSequentialGroup()
                        .addComponent(e2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(142, 142, 142)
                        .addComponent(equaliserpresetcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(setequaliserbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        equaliserpanelLayout.setVerticalGroup(
            equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(equaliserpanelLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(e1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(equaliserpresetcombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(e3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(setequaliserbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(e5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        jPanel2.add(equaliserpanel, "card5");

        mainpanelmp3.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel4.setBackground(new java.awt.Color(51, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 505));

        subtitledisplaybox.setColumns(20);
        subtitledisplaybox.setRows(5);
        jScrollPane1.setViewportView(subtitledisplaybox);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        mainpanelmp3.add(jPanel4, java.awt.BorderLayout.LINE_END);

        javax.swing.GroupLayout videopanelLayout = new javax.swing.GroupLayout(videopanel);
        videopanel.setLayout(videopanelLayout);
        videopanelLayout.setHorizontalGroup(
            videopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 897, Short.MAX_VALUE)
        );
        videopanelLayout.setVerticalGroup(
            videopanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
        );

        mainpanelmp3.add(videopanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainpanelmp3, javax.swing.GroupLayout.PREFERRED_SIZE, 1197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainpanelmp3, javax.swing.GroupLayout.PREFERRED_SIZE, 755, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//pause music(basic-3)
    private void offlinepausebuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlinepausebuttonMouseClicked
        if(isPlaying && !isPaused){
            mediaPlayer.pause();
    
            isPaused = true;
            offlinepausebutton.setText("RESUME");
        }
        else if(isPlaying && isPaused){
           mediaPlayer.seek(Duration.seconds(seekbar.getValue()));
           mediaPlayer.play();
           isPaused = false;
           offlinepausebutton.setText("PAUSE");
           
       } 
    }//GEN-LAST:event_offlinepausebuttonMouseClicked

    public JSlider getSeekbar(){
        return this.seekbar;
    }
    //start player button(basic-3)
    private void offlineplaybtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlineplaybtMouseClicked
        // TODO add your handling code here:
       playAllQueueCounter = 0;
        
        if(queueEditor.getNowplayinglist().isEmpty()){
            JOptionPane.showMessageDialog(null,"NO SONG IN LIST");
        }else{
        
         if(isPlaying){
             mediaPlayer.dispose();
             try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
             seekbar.removeChangeListener(seekListener);
             seekbar.setValue(0);
         }  
        
        isPlaying=true;
        if(! online){
        mp3player(playAllQueueCounter);}
        else{
            mp3playeronline(playAllQueueCounter);
        }
        playAllQueueCounter++;
        
        
        }
    }//GEN-LAST:event_offlineplaybtMouseClicked
//back to play button functionality
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        
        
        equaliserpanel.setVisible(false);
        offlineplaybutton.setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked

    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void setequaliserbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setequaliserbuttonActionPerformed
      
        AudioEqualizer audioEqualizer =mediaPlayer.getAudioEqualizer();
        
        
        
    }//GEN-LAST:event_setequaliserbuttonActionPerformed
//equaliser method to  set equaliser automatically on song play(advanced-1)
    
    private void setequaliseronplay(){
        
        
        
        
        
        JSlider bands[]=new JSlider[6];
        bands[0]=e1;
        bands[1]=e2;
        bands[2]=e3;
        bands[3]=e4;
        bands[4]=e5;
        bands[5]=e6;
        
        for(int i=0;i<10;i++){
            mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(0.0F);
            if(i<6)
                bands[i].setValue(0);
            
        }
        
        ///also using equaliser preset ,which we have already stored(advanced-1)
        equaliserpresetcombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                  String s = (String) equaliserpresetcombobox.getSelectedItem();
                  equaliserpresets eq=new equaliserpresets();
                  int temp =Arrays.asList(eq.name).indexOf(s);
                  
                  for(int i=0;i<10;i++){
                      mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(eq.Presets[temp][i]);
                      
                      if(i<6){
                          final int ii=Math.round(eq.Presets[temp][i]);
                          bands[i].setValue(ii);
                      }    
                  }
                  
                  
                  
            }
        });
           
        for(int i=0;i<6;i++){
            final int index=i;
        bands[i].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					double p = bands[index].getValue();
					                               System.out.println("changed :"+index);
                                                                       System.out.println("value of "+index+"is :" +p);
                                                                              
                                                                               
					mediaPlayer.getAudioEqualizer().getBands().get(index).setGain(p);
				}
			});
    }
    }
    
    //stop music(basic-3)
    private void offlinestopbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlinestopbuttonMouseClicked
        if(isPlaying){
        mediaPlayer.dispose();
        try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
        seekbar.removeChangeListener(seekListener);
        seekbar.setValue(0);
        isPlaying = false;
        offlinepausebutton.setText("PAUSE");
        }
    }//GEN-LAST:event_offlinestopbuttonMouseClicked
//loop music(basic-3)
    private void offlineloopbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlineloopbuttonMouseClicked
            if(null != isLooping)switch (isLooping) {
            
                case "LOOP_OFF":
                isLooping = "LOOP_ONE";
                offlineloopbutton.setText("LOOP ONE");
                break;
            
                case "LOOP_ONE":
                isLooping = "LOOP_ALL";
                offlineloopbutton.setText("LOOP ALL");
                break;

                default:
                isLooping = "LOOP_OFF";
                offlineloopbutton.setText("LOOP OFF");
            }
    }//GEN-LAST:event_offlineloopbuttonMouseClicked
//mute button(basic-3)
    private void offlinemutebuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlinemutebuttonMouseClicked
        // TODO add your handling code here:
        if(!ismuted){
            mediaPlayer.setVolume(0);
            ismuted=true;}
                    
            else{
            mediaPlayer.setVolume(volume);
            ismuted=false;}
    }//GEN-LAST:event_offlinemutebuttonMouseClicked
//volume down controller(basic-3)
    private void offlinevolumedownbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlinevolumedownbuttonMouseClicked
        // TODO add your handling code here:
        if(volume>=0.1F){
        volume-=0.1F;
        mediaPlayer.setVolume(volume);}
        else{
            volume=0;
            mediaPlayer.setVolume(volume);
        }
    }//GEN-LAST:event_offlinevolumedownbuttonMouseClicked
//volume up controller (basic-3)
    private void offlinevolumeupbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlinevolumeupbuttonMouseClicked
        // TODO add your handling code here:
        if(volume<=0.9F){
        volume+=0.1F;
        mediaPlayer.setVolume(volume);}
        else{
            volume=1;
            mediaPlayer.setVolume(volume);
        }
    }//GEN-LAST:event_offlinevolumeupbuttonMouseClicked
//playnext button(basic-3)
    private void offlineplaynextbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlineplaynextbuttonMouseClicked
        if((songArraySize = queueEditor.getNowplayinglist().size())==0){
            JOptionPane.showMessageDialog(null, "NO SONGS IN QUEUE");
        }
        
//=============================================================================
            if(mediaPlayer==null){
                JOptionPane.showMessageDialog(null,"FIRST START,THEN PLAY NEXT");
            }
            else{
            mediaPlayer.seek(mediaPlayer.getTotalDuration());
           mediaPlayer.setOnEndOfMedia(() -> {
               
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_ALL":
                    
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=songArraySize && isshuffle==false){
                        playAllQueueCounter = 0;}
                    else if(isshuffle==true){
                        playAllQueueCounter=random.nextInt(queueEditor.getNowplayinglist().size());
                    }
                    if(!online){
                        mp3player(playAllQueueCounter);}
                    else{
                        mp3playeronline(playAllQueueCounter);
                    }
                        playAllQueueCounter++;
                        
                        isPlaying = true;
                        offlinepausebutton.setText("PAUSE");
                        isPaused = false;
                     break;
                    
                default:
                    if(playAllQueueCounter>=songArraySize && isshuffle==false){
                        JOptionPane.showMessageDialog(null, "END OF PLAYLIST");
                        break;
                    }
                    else if(isshuffle==true){
                        playAllQueueCounter=random.nextInt(queueEditor.getNowplayinglist().size());
                    
                    
                    }
                    System.out.println("hcishcionbdciaoscdiowd");
                    mediaPlayer.currentTimeProperty().removeListener(l->{});
                    mediaPlayer.dispose();
                    System.out.println("na");
                    System.out.println("nabajkbxjzkbjkzbjbjj");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    if(!online){
                    mp3player(playAllQueueCounter);}
                    else{
                        mp3playeronline(playAllQueueCounter);
                    }
                        playAllQueueCounter++;
                    
                        isPlaying = true;
                        offlinepausebutton.setText("PAUSE");
                        isPaused = false;
                    break;
            }
            
        });
            }
        //==============================================================================


        
    }//GEN-LAST:event_offlineplaynextbuttonMouseClicked
//play previopus button(basic-3)
    private void offlineplaypreviousbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlineplaypreviousbuttonMouseClicked
            if((songArraySize = queueEditor.getNowplayinglist().size())==0){
            JOptionPane.showMessageDialog(null, "NO SONGS IN QUEUE");
        }  
        
       //=============================================================================
        if(mediaPlayer==null){
            JOptionPane.showMessageDialog(null,"FIRST START THEN PLAY PREVIOUS");
        }    
        else{
            set=true;
            
            System.out.println("herereereferererererererererereere");
       mediaPlayer.seek(mediaPlayer.getTotalDuration());
            if(set=isPaused=true){
           mediaPlayer.setOnEndOfMedia(() -> {
               System.out.println("hierhierhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            if(null != isLooping)switch (isLooping) {
                

                case "LOOP_ALL":
                    mediaPlayer.dispose();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    playAllQueueCounter--;
                    playAllQueueCounter--;
                    
                    if(playAllQueueCounter<0 && isshuffle==false){
                        playAllQueueCounter = songArraySize-1;}
                    else if(isshuffle==true){
                    playAllQueueCounter=random.nextInt(queueEditor.getNowplayinglist().size());
                }
                    
                        if(!online){
                    mp3player(playAllQueueCounter);}
                    else{
                        mp3playeronline(playAllQueueCounter);
                    }
                        playAllQueueCounter++;
                        
                        isPlaying = true;
                        offlinepausebutton.setText("PAUSE");
                        isPaused = false;
                     break;
                
                default:
                    System.out.println(playAllQueueCounter);
                    playAllQueueCounter--;
                    playAllQueueCounter--;
                    System.out.println(playAllQueueCounter);
                    if(playAllQueueCounter<0 && isshuffle==false){
                        JOptionPane.showMessageDialog(null, "NO PREVIOUS SONGS");
                        playAllQueueCounter = 0;
                        break;
                    }
                    else if(playAllQueueCounter<0 && isshuffle==false){
                        playAllQueueCounter=random.nextInt(queueEditor.getNowplayinglist().size());
                    }
                    
                    mediaPlayer.dispose();
            {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(!online){
                    mp3player(playAllQueueCounter);}
                    else{
                        mp3playeronline(playAllQueueCounter);
                    }
                        playAllQueueCounter++;
                    
                        isPlaying=true;
                    offlinepausebutton.setText("PAUSE");
                        isPaused = false;
                    break;        

                
            }
            
        });}
        }

        //==============================================================================

        
    }//GEN-LAST:event_offlineplaypreviousbuttonMouseClicked

    private void offlineshufflebuttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlineshufflebuttonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_offlineshufflebuttonMouseEntered
//shuffle music list(basic-3)
    private void offlineshufflebuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlineshufflebuttonMouseClicked
        // TODO add your handling code here:
        if(isshuffle){
            isshuffle=false;
            offlineshufflebutton.setText("SHUFFLE OFF");
        }
        else{
            isshuffle=true;
            offlineshufflebutton.setText("SHUFFLE ON");
        }
    }//GEN-LAST:event_offlineshufflebuttonMouseClicked
//like a song in online mode(basic-8)
    private void likeboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_likeboxActionPerformed
        // TODO add your handling code here:
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(likebox.isSelected()){
                    
                    
            dislikebox.setSelected(false);
            SendFeedbackRequest sendFeedbackRequest =new SendFeedbackRequest();
            sendFeedbackRequest.setUserName(MainClass.user.getUserName());
            sendFeedbackRequest.setSong(nowplayingsong);
            sendFeedbackRequest.setFeedbackCode(FeedbackCode.LIKE);
            System.out.println("liking");
                    System.out.println(nowplayingsong);
            try {
                MainClass.oos.writeObject(sendFeedbackRequest);
                MainClass.oos.flush();
                
            } catch (IOException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        else{
                    System.out.println("disliking");
            dislikebox.setSelected(false);
            SendFeedbackRequest sendFeedbackRequest =new SendFeedbackRequest();
            sendFeedbackRequest.setUserName(MainClass.user.getUserName());
            sendFeedbackRequest.setSong(nowplayingsong);
            sendFeedbackRequest.setFeedbackCode(FeedbackCode.NONE);
            try {
                MainClass.oos.writeObject(sendFeedbackRequest);
                MainClass.oos.flush();
                
            } catch (IOException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
            }
        }).start();
        
    }//GEN-LAST:event_likeboxActionPerformed
//dislike a song in online mode(baisc-8)
    private void dislikeboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dislikeboxActionPerformed
        // TODO add your handling code here:
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(dislikebox.isSelected()){
            likebox.setSelected(false);
            SendFeedbackRequest sendFeedbackRequest =new SendFeedbackRequest();
            sendFeedbackRequest.setUserName(MainClass.user.getUserName());
            sendFeedbackRequest.setSong(nowplayingsong);
            sendFeedbackRequest.setFeedbackCode(FeedbackCode.DISLIKE);
            try {
                MainClass.oos.writeObject(sendFeedbackRequest);
                MainClass.oos.flush();
                
            } catch (IOException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        else{
            likebox.setSelected(false);
            SendFeedbackRequest sendFeedbackRequest =new SendFeedbackRequest();
            sendFeedbackRequest.setUserName(MainClass.user.getUserName());
            sendFeedbackRequest.setSong(nowplayingsong);
            sendFeedbackRequest.setFeedbackCode(FeedbackCode.NONE);
            try {
                MainClass.oos.writeObject(sendFeedbackRequest);
                MainClass.oos.flush();
                
            } catch (IOException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
            }
        }).start();
        
    }//GEN-LAST:event_dislikeboxActionPerformed
//buttonn to toggle,online/offline
    private void onlineofflinebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlineofflinebuttonActionPerformed
        // TODO add your handling code here:
        if(online){
            if(! mediaPlayer.equals(null))
                mediaPlayer.dispose();
            online=false;
            likebox.setVisible(false);
            dislikebox.setVisible(false);
            downloadbutton.setVisible(false);
            onlineofflinebutton.setText("ONLINE");
            
            String[] pathnames;
            String name;
            String fileName;

            File f = new File("C:\\Ampify_Downloaded_Songs");
            File[] fList = f.listFiles();
            ArrayList<String> songs = new ArrayList<>();
            for (File file : fList) {
                        if (file != null && file.exists()) {
                name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", "");
                songs.add(fileName);
            }
        
        }
        //System.out.println(tolist);
        queueEditor.addplaylisttoqueue(songs);
        }
        else{
            if(mediaPlayer !=null){
                mediaPlayer.dispose();}
            online=true;
            likebox.setVisible(true);
            dislikebox.setVisible(true);
            downloadbutton.setVisible(true);
            onlineofflinebutton.setText("OFFLINE");
            
        }
    }//GEN-LAST:event_onlineofflinebuttonActionPerformed
//download any song from there 
    private void downloadbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadbuttonActionPerformed
        // TODO add your handling code here:
        if(online){
            try {
                DownloadSong downloadSong =new DownloadSong(nowplayingsong,false);
            } catch (IOException ex) {
                Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_downloadbuttonActionPerformed
//window default close listener
    private void closehoja(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closehoja
        // TODO add your handling code here:
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.close();
        this.dispose();
        //logout lg=new logout(MainClass.user.getUserName());
        //JOptionPane.showMessageDialog(null,"hiwcnasinciosdnaoi");
        System.out.println("hogya hogyz...................");
    }//GEN-LAST:event_closehoja
//equaliser buton (advanced-1)
    private void equaliserentrybuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_equaliserentrybuttonMouseClicked
        // TODO add your handling code here:
        offlineplaybutton.setVisible(false);
        equaliserpanel.setVisible(true);
        
        
    }//GEN-LAST:event_equaliserentrybuttonMouseClicked
//close method to dispose mediaplayer safely
    
   public void close(){
       if(! mediaPlayer.equals(null))
       mediaPlayer.dispose();
   }
    
    private JFXPanel fxPanel;
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mp3playeroffline.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mp3playeroffline.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mp3playeroffline.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mp3playeroffline.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mp3playeroffline().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox dislikebox;
    private javax.swing.JButton downloadbutton;
    private javax.swing.JSlider e1;
    private javax.swing.JSlider e2;
    private javax.swing.JSlider e3;
    private javax.swing.JSlider e4;
    private javax.swing.JSlider e5;
    private javax.swing.JSlider e6;
    private javax.swing.JButton equaliserentrybutton;
    private javax.swing.JPanel equaliserpanel;
    private javax.swing.JComboBox<String> equaliserpresetcombobox;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox likebox;
    private javax.swing.JPanel mainpanelmp3;
    private javax.swing.JLabel offlineloopbutton;
    private javax.swing.JLabel offlinemutebutton;
    private javax.swing.JLabel offlinepausebutton;
    private javax.swing.JLabel offlineplaybt;
    private javax.swing.JPanel offlineplaybutton;
    private javax.swing.JLabel offlineplaynextbutton;
    private javax.swing.JLabel offlineplaypreviousbutton;
    private javax.swing.JLabel offlineshufflebutton;
    private javax.swing.JLabel offlinestopbutton;
    private javax.swing.JLabel offlinevolumedownbutton;
    private javax.swing.JLabel offlinevolumeupbutton;
    private javax.swing.JButton onlineofflinebutton;
    private javax.swing.JSlider seekbar;
    private javax.swing.JButton setequaliserbutton;
    private javax.swing.JLabel songlength;
    private javax.swing.JTextArea subtitledisplaybox;
    private javax.swing.JPanel videopanel;
    // End of variables declaration//GEN-END:variables

}
    

    
     

