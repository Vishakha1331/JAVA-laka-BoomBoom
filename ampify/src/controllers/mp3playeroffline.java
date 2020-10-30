/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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
 
import javax.swing.JFrame;


/**
 *
 * @author rishi
 */
public class mp3playeroffline extends JFrame implements  ActionListener{

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
    
    //
//    jFrame.addWindowListener(new WindowAdapter() {
// 
//@Override
//public void windowClosing(WindowEvent e) {
//        System.out.println("close ho ja yrrrr");
//    System.exit(0);
// 
//}
// 
//  });
    
    
    ////
    
    public mp3playeroffline() {
        
        initComponents();
        seekbar.setValue(0);
        
        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
         
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
 
                System.out.println("ho gya ho gya");
                        
                        
                
                close();
                dispose();
            }
        });
        
         
         
    }
    
    
   
//    JFrame frame = new JFrame();
//    frame.addWindowListener(new java.awt.event.WindowAdapter() {
//    @Override
//    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//        if (JOptionPane.showConfirmDialog(frame, 
//            "Are you sure you want to close this window?", "Close Window?", 
//            JOptionPane.YES_NO_OPTION,
//            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
//            System.exit(0);
//        }
//    }
//});
//    
    //@Override
    public void setDefaultCloseOperation() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
                dispose();
            }
        });
    }
    
    
    
    
    public void exitProcedure(){
        
        this.close();
        this.dispose();
        //System.exit(0);
        
    }
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
 
    public void toolkit() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFXPanel(); // this will prepare JavaFX toolkit and environment
            }
            });
    }
    
    private void mp3playeronline(int i){
       nowplayingsong=queueEditor.getNowplayinglist().get(i);
        SongRequest songRequest =new SongRequest();
        songRequest.setSong(nowplayingsong);
        songRequest.setUserName(MainClass.user.getUserName());
        try {
            MainClass.oos.writeObject(songRequest);
            MainClass.oos.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(mp3playeroffline.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
             System.out.println((int) media.getDuration().toSeconds());
             seekbar.setMaximum((int) media.getDuration().toSeconds());
             mediaPlayer.currentTimeProperty().addListener(l-> {
                 
                 seekbar.removeChangeListener(seekListener);
                 Duration currentTime = mediaPlayer.getCurrentTime();
                 
                 int value = (int) currentTime.toSeconds();
                 seekbar.setValue(value);
                 seekbar.addChangeListener(seekListener);
                 
         
        mediaPlayer.setOnEndOfMedia(() -> {
               
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_OFF":
                    mediaPlayer.dispose();
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
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=queueEditor.getNowplayinglist().size()){
                        playAllQueueCounter = 0; }
                    
                    mp3playeronline(playAllQueueCounter);
                     playAllQueueCounter++;
                    break;
            }
            
        });
             });});
            }
        });
       }
       //
       else if(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".mp3").exists()){
           //System.out.println("getting http media");
           //new File("C:\\Ampify_Downloaded_Songs\\"+queueEditor.getNowplayinglist().get(i)+".mp3").toURI().toString()
           //http://localhost:7777/a.mp3
           media = new Media(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".mp3").toURI().toString());
           
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
             System.out.println((int) media.getDuration().toSeconds());
             seekbar.setMaximum((int) media.getDuration().toSeconds());
             mediaPlayer.currentTimeProperty().addListener(l-> {
                 
                 seekbar.removeChangeListener(seekListener);
                 Duration currentTime = mediaPlayer.getCurrentTime();
                 
                 int value = (int) currentTime.toSeconds();
                 seekbar.setValue(value);
                 seekbar.addChangeListener(seekListener);
                 
                 
        mediaPlayer.setOnEndOfMedia(() -> {
                System.out.println("end of media");
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_OFF":
                    mediaPlayer.dispose();
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
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=queueEditor.getNowplayinglist().size()){
                        playAllQueueCounter = 0; }
                    
                    mp3playeronline(playAllQueueCounter);
                     playAllQueueCounter++;
                    break;
            }
            
        });
             });});
       }
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
             media = new Media(new File("C:\\Users\\rishi\\Documents\\NetBeansProjects\\ampify\\ampify\\temp\\"+queueEditor.getNowplayinglist().get(i)+".mp3").toURI().toString());
           
           System.out.println("i am mp3 online");
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
             System.out.println((int) media.getDuration().toSeconds());
             seekbar.setMaximum((int) media.getDuration().toSeconds());
             mediaPlayer.currentTimeProperty().addListener(l-> {
                 
                 seekbar.removeChangeListener(seekListener);
                 Duration currentTime = mediaPlayer.getCurrentTime();
                 
                 int value = (int) currentTime.toSeconds();
                 seekbar.setValue(value);
                 seekbar.addChangeListener(seekListener);
                 
                 
        mediaPlayer.setOnEndOfMedia(() -> {
                System.out.println("end of media");
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_OFF":
                    mediaPlayer.dispose();
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
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=queueEditor.getNowplayinglist().size()){
                        playAllQueueCounter = 0; }
                    
                    mp3playeronline(playAllQueueCounter);
                     playAllQueueCounter++;
                    break;
            }
            
        });
             });});
       }
       
                 seekListener = (ChangeEvent event) -> {
             mediaPlayer.seek(Duration.seconds(seekbar.getValue()));
       };
         
        seekbar.addChangeListener(seekListener);   
                }
    
    
    
     private void mp3player(int i){
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
             System.out.println((int) media.getDuration().toSeconds());
             seekbar.setMaximum((int) media.getDuration().toSeconds());
             mediaPlayer.currentTimeProperty().addListener(l-> {
                 
                 seekbar.removeChangeListener(seekListener);
                 Duration currentTime = mediaPlayer.getCurrentTime();
                 
                 int value = (int) currentTime.toSeconds();
                 seekbar.setValue(value);
                 seekbar.addChangeListener(seekListener);
                 
         
        mediaPlayer.setOnEndOfMedia(() -> {
               
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_OFF":
                    mediaPlayer.dispose();
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
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=queueEditor.getNowplayinglist().size()){
                        playAllQueueCounter = 0; }
                    
                    mp3player(playAllQueueCounter);
                     playAllQueueCounter++;
                    break;
            }
            
        });
             });});
            }
        });
       }
       //
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
             System.out.println((int) media.getDuration().toSeconds());
             seekbar.setMaximum((int) media.getDuration().toSeconds());
             mediaPlayer.currentTimeProperty().addListener(l-> {
                 
                 seekbar.removeChangeListener(seekListener);
                 Duration currentTime = mediaPlayer.getCurrentTime();
                 
                 int value = (int) currentTime.toSeconds();
                 seekbar.setValue(value);
                 seekbar.addChangeListener(seekListener);
                 
                 
        mediaPlayer.setOnEndOfMedia(() -> {
                System.out.println("end of media");
            if(null != isLooping)switch (isLooping) {
                
                case "LOOP_OFF":
                    mediaPlayer.dispose();
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
                    seekbar.removeChangeListener(seekListener);
                    seekbar.setValue(0);
                    
                    if(playAllQueueCounter>=queueEditor.getNowplayinglist().size()){
                        playAllQueueCounter = 0; }
                    
                    mp3player(playAllQueueCounter);
                     playAllQueueCounter++;
                    break;
            }
            
        });
             });});
       }
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
        jButton3 = new javax.swing.JButton();
        downloadbutton = new javax.swing.JButton();
        onlineofflinebutton = new javax.swing.JButton();
        offlineplaybt = new javax.swing.JLabel();
        songlength = new javax.swing.JLabel();
        likebox = new javax.swing.JCheckBox();
        dislikebox = new javax.swing.JCheckBox();
        equaliserpanel = new javax.swing.JPanel();
        jSlider2 = new javax.swing.JSlider();
        jSlider3 = new javax.swing.JSlider();
        jSlider4 = new javax.swing.JSlider();
        jSlider5 = new javax.swing.JSlider();
        jSlider6 = new javax.swing.JSlider();
        jSlider7 = new javax.swing.JSlider();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
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

        jButton3.setText("EQUALISER");

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
                .addComponent(jButton3)
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
                    .addComponent(jButton3)
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        jButton2.setText("SET EQUALISER");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout equaliserpanelLayout = new javax.swing.GroupLayout(equaliserpanel);
        equaliserpanel.setLayout(equaliserpanelLayout);
        equaliserpanelLayout.setHorizontalGroup(
            equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(equaliserpanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSlider4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(equaliserpanelLayout.createSequentialGroup()
                        .addComponent(jSlider5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(142, 142, 142)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        equaliserpanelLayout.setVerticalGroup(
            equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(equaliserpanelLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(equaliserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        jPanel2.add(equaliserpanel, "card5");

        mainpanelmp3.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel4.setBackground(new java.awt.Color(51, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 505));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
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
    private void offlineplaybtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlineplaybtMouseClicked
        // TODO add your handling code here:
       playAllQueueCounter = 0;
        
        if(queueEditor.getNowplayinglist().isEmpty()){
            JOptionPane.showMessageDialog(null,"NO SONG IN LIST");
        }else{
        
         if(isPlaying){
             mediaPlayer.dispose();
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

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        
        JOptionPane.showMessageDialog(null, "i am fine");
        equaliserpanel.setVisible(false);
        offlineplaybutton.setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void offlinestopbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlinestopbuttonMouseClicked
        if(isPlaying){
        mediaPlayer.dispose();
        seekbar.removeChangeListener(seekListener);
        seekbar.setValue(0);
        isPlaying = false;
        offlinepausebutton.setText("PAUSE");
        }
    }//GEN-LAST:event_offlinestopbuttonMouseClicked

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

    private void offlinemutebuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlinemutebuttonMouseClicked
        // TODO add your handling code here:
        if(!ismuted){
            mediaPlayer.setVolume(0);
            ismuted=true;}
                    
            else{
            mediaPlayer.setVolume(volume);
            ismuted=false;}
    }//GEN-LAST:event_offlinemutebuttonMouseClicked

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

    private void offlineplaynextbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlineplaynextbuttonMouseClicked
        if((songArraySize = queueEditor.getNowplayinglist().size())==0){
            JOptionPane.showMessageDialog(null, "NO SONGS IN QUEUE");
        }
        
        else if(null != isLooping)switch (isLooping) {
                
                case "LOOP_ALL":
                    
                    mediaPlayer.dispose();
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
                    
                    mediaPlayer.dispose();
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
        
    }//GEN-LAST:event_offlineplaynextbuttonMouseClicked

    private void offlineplaypreviousbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlineplaypreviousbuttonMouseClicked
            if((songArraySize = queueEditor.getNowplayinglist().size())==0){
            JOptionPane.showMessageDialog(null, "NO SONGS IN QUEUE");
        }  
        
        else if(null != isLooping)switch (isLooping) {
                
                case "LOOP_ALL":
                    mediaPlayer.dispose();
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
                    playAllQueueCounter--;
                    playAllQueueCounter--;
                    
                    if(playAllQueueCounter<0 && isshuffle==false){
                        JOptionPane.showMessageDialog(null, "NO PREVIOUS SONGS");
                        playAllQueueCounter = 0;
                        break;
                    }
                    else if(playAllQueueCounter<0 && isshuffle==false){
                        playAllQueueCounter=random.nextInt(queueEditor.getNowplayinglist().size());
                    }
                    
                    mediaPlayer.dispose();
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
        
    }//GEN-LAST:event_offlineplaypreviousbuttonMouseClicked

    private void offlineshufflebuttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_offlineshufflebuttonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_offlineshufflebuttonMouseEntered

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
    private javax.swing.JPanel equaliserpanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JSlider jSlider3;
    private javax.swing.JSlider jSlider4;
    private javax.swing.JSlider jSlider5;
    private javax.swing.JSlider jSlider6;
    private javax.swing.JSlider jSlider7;
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
    private javax.swing.JLabel songlength;
    private javax.swing.JPanel videopanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("exitttt");
        this.close();
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
     
}
