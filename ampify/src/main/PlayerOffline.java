/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


import java.io.File;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.SceneBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.StageBuilder;
import javax.swing.SwingUtilities;

/**
 *
 * @author rishi
 */
public class PlayerOffline  implements Runnable{
    private String path;
    
    public PlayerOffline() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFXPanel(); // this will prepare JavaFX toolkit and environment
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        StageBuilder.create()
                                .scene(SceneBuilder.create()
                                        .root(LabelBuilder.create()
                                                .build())
                                        .build())
                                .build();
                    }
                    });
            }
            });
        
    
    }
    
    public void run(){
    
    }
    
     public void playbt(String path,int i){
         this.path =path;
         new Thread(new Runnable() {
             @Override
             public void run() {
                 
    Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        if(i==0)
        mediaPlayer.play();
        if(i==1)
            mediaPlayer.stop();
             }
         }).start();
        
}
}
