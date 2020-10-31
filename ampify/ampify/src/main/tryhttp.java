/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;



import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.SceneBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.stage.StageBuilder;

import javax.swing.SwingUtilities;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 *
 * @author rishi
 */

public class tryhttp {
    private static HttpURLConnection connection;
    public static void main(String[] args) throws MalformedURLException, IOException {
        BufferedReader reader;
        String line;
        StringBuffer responseconetent =new StringBuffer();
        
         
    
    
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFXPanel(); // this will prepare JavaFX toolkit and environment
                
            }
            });
        
    
    
        try{
            URL url =new URL("http://localhost:7777/a.mp3");
            //url.openConnection();
            
            Path mp3=Files.createTempFile("now-playing",".mp3");
            try(InputStream stream =url.openStream()){
                System.out.println("hello i am here");
                Files.copy(stream, mp3,StandardCopyOption.REPLACE_EXISTING);
                System.out.println("khtm hua");
            }
            Media media =new Media(mp3.toFile().toURI().toString());
            System.out.println("hi there");
                    
            MediaPlayer mediaPlayer =new MediaPlayer(media);
            mediaPlayer.play();
//            


//AudioStream as = new AudioStream(url.openStream());
//         AudioPlayer.player.start(as);
//         
//         Media media;
//            try {
//                media = new Media(url.toURI().toString());
//                System.out.println(media.getDuration().toSeconds());
//                 MediaPlayer mediaPlayer =new MediaPlayer(media);
//         mediaPlayer.play();
//                System.out.println("are ho ja yrr");
//            } catch (URISyntaxException ex) {
//                Logger.getLogger(tryhttp.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        



// AudioPlayer.player.stop(as);
//            AudioStream as = new AudioStream (url.openStream());
//        AudioData data = as.getData();
//        ContinuousAudioDataStream cas = new ContinuousAudioDataStream (data);
//        AudioPlayer.player.start(cas);
            
//connection =(HttpURLConnection) url.openConnection();
            //connection.setRequestMethod("GET");
            //connection.setConnectTimeout(5000);
            //connection.setReadTimeout(5000);
            
//            InputStream audioSrc = connection.getInputStream();
//        DataInputStream read = new DataInputStream(audioSrc);
//        AudioStream as = new AudioStream(read);
//        AudioPlayer.player.start(as);
//        //AudioPlayer.player.stop(as);
//            int status =connection.getResponseCode();
//            System.out.println(status);
//            
            //reader =new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            
//            BufferedReader dataInputStream =new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            File temfile=File.createTempFile("temp",".mp3");
//            byte[] b=new byte[10*1024];
//           

// dataInputStream.;
            
            
//            FileOutputStream fileOutputStream=new FileOutputStream(temfile);
//            fileOutputStream.write(b);
//            System.out.println("helllo");
//            
//            int count;
//            while ((count =connection.getInputStream().read(b)) > 0) {
//                System.out.println("inside while");
//            fileOutputStream.write(b, 0, count);
//            fileOutputStream.flush();
//                System.out.println("written");
//        }
//            System.out.println("i am here");
//            fileOutputStream.close();
//            System.out.println("outside whiel");

//new Thread(new Runnable() {
//             @Override
//             public void run() {
//                    //http://localhost:7777/a.mp3
//                    //https://raw.githubusercontent.com/paoovan/songs/main/05%20Why%20Not%20Me.mp3
//    Media media = new Media("http:1.1//localhost:7777/a.mp3") ;
//        
////            String url = "https://raw.githubusercontent.com/paoovan/songs/main/05%20Why%20Not%20Me.mp3"; // your URL here
////            MediaPlayer mediaPlayer = new MediaPlayer(media);
////            //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
////            mediaPlayer.setDataSource(url);
////            mediaPlayer.prepare(); // might take long! (for buffering, etc)
////            mediaPlayer.start();
////            
//        
//    
//        MediaPlayer mediaPlayer = new MediaPlayer(media) ;
//        
//        mediaPlayer.setOnReady(new Runnable() {
//        @Override
//        public void run() {
//            
//            System.out.println("hihdibdind");
//            mediaPlayer.play();
//            //System.out.println(mediaPlayer.getError());
//            System.out.println("kaam chl gya");
//            System.out.println(mediaPlayer.getCurrentRate());
//        }
//    });
////        mediaPlayer.setVolume(1.0);
////        mediaPlayer.setAutoPlay(true);
////                 System.out.println(mediaPlayer.getCurrentTime());
////                 mediaPlayer.setVolume(1.0);
//             }
//         }).start();
        



//            Media media=new Media("http://localhost:7777/ab.wav");
//            System.out.println("hello mp3");
//            MediaPlayer mediaPlayer =new MediaPlayer(media);
//            System.out.println("hello 2");
//            mediaPlayer.play();
//            System.out.println("helo3");
//            System.out.println(mediaPlayer.equals(null));
//            
            /*
            System.out.println(reader.getClass());
            while((line=reader.readLine())!=null){
                responseconetent.append(line);
            }
            reader.close();*/
        //System.out.println(responseconetent.toString());
        }
        finally{
            System.out.println("hello final");
        }
        
    
//    catch(MalformedURLException e){
//    e.printStackTrace();}
//    catch(IOException e){
//        e.printStackTrace();}
//        finally{
//            //connection.disconnect();
//        }
//        
    }
}

