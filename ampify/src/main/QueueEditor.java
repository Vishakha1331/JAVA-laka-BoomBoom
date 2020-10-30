/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import controllers.mainpage;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author rishi
 */
public class QueueEditor {
    
     mainpage mp;
     public static ArrayList<String> nowplayinglist=new ArrayList<>();
    
     public QueueEditor(mainpage mpp){
         mp=mpp;
     }

    public QueueEditor() { }
    
   public void addsongtoqueue(String s){
       this.nowplayinglist.add(s);
       sendtoqueue();
       
   }
   
   public void addplaylisttoqueue(ArrayList<String> playlist){
       this.nowplayinglist.clear();
       for(int i=0;i<playlist.size();i++){
           this.nowplayinglist.add(playlist.get(i));
           //this.nowplayinglist=nowplayinglist;
       }
       sendtoqueue();
       System.out.println(this.nowplayinglist);
   }
    public void deletesongfromqueue(String s){
        this.nowplayinglist.remove(s);
        sendtoqueue();
                
    }
    public void sendtoqueue(){
               
        

        DefaultListModel defaultListModel =new DefaultListModel();
        defaultListModel.clear();
        DefaultListModel defaultListModel1 =new DefaultListModel();
        defaultListModel1.clear();
        DefaultListModel defaultListModel2 =new DefaultListModel();
        defaultListModel2.clear();
        System.out.println("i am in sen to queue");
        mp.getQueueList().setModel(defaultListModel);
        mp.getQueueUp().setModel(defaultListModel1);
        mp.getQueueDown().setModel(defaultListModel2);
        
        for (int i=0;i<nowplayinglist.size();i++){
            defaultListModel.add(i, nowplayinglist.get(i));
            defaultListModel1.add(i, "UP");
            defaultListModel2.add(i, "Down");
            
        }
        mp.getQueueList().setModel(defaultListModel);
        mp.getQueueUp().setModel(defaultListModel1);
        mp.getQueueDown().setModel(defaultListModel2);
        
        
        
    }
    
    public void updatequeue(int up,int down){
        if(up!=-1 && up!=0){
        String temp=nowplayinglist.get(up);
        nowplayinglist.set(up, nowplayinglist.get(up-1));
        nowplayinglist.set(up-1, temp);
            
        }
        if(down!=-1 && down!=nowplayinglist.size()-1){
        String temp=nowplayinglist.get(down);
        nowplayinglist.set(down, nowplayinglist.get(down+1));
        nowplayinglist.set(down+1, temp);
            
        }
        sendtoqueue();
        
    }
    

    public ArrayList<String> getNowplayinglist() {
        System.out.println("hiiii");
        //System.out.println(getTopSong());
        System.out.println(nowplayinglist);
        return nowplayinglist;
    }
    
}
