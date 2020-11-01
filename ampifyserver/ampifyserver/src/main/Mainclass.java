package main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.DBConnection;

public class Mainclass {
    public static Connection con = DBConnection.connectDB();
    public static Socket socket;
    public static Socket downloadSocket;
    public static ServerSocket serversocket;
    public static ServerSocket downloadServerSocket;
    public static ServerSocketChannel serverClientSocketChannel;
   
    public static void main(String[] args) {
        try {
            serverClientSocketChannel = ServerSocketChannel.open();
            serverClientSocketChannel.socket().bind(new InetSocketAddress(9000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            serversocket = new ServerSocket(2345);
            downloadServerSocket=new ServerSocket(63423);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
            try{
                socket = serversocket.accept();
                Thread t = new Thread(new handleClientRequest(socket));
                t.start();
                System.out.println("Client connected");
                
                
            }catch (IOException e){
                e.printStackTrace();
            }
        }
                
            }
        }).start();
        
        
        new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while(true){
                            downloadSocket=downloadServerSocket.accept();
                            Thread t1=new Thread(new DownloadHandler(downloadSocket));
                            t1.start();
                            System.out.println("Download socket connected....");
                            }
                                    } catch (IOException ex) {
                            Logger.getLogger(Mainclass.class.getName()).log(Level.SEVERE, null, ex);
                        }
                
                        
                    }
                }).start();
                
    }
    
}
