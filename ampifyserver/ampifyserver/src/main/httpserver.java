/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;

/**
 *
 * @author rishi
 */
public class httpserver {

    public static void main(String[] args) throws IOException {
       HttpServer httpServer =HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 7777),0);
       
       httpServer.createContext("/",new customhttphandler("/C:/Users/rishi/Documents/NetBeansProjects/ampifyserver/ampifyserver/songsdata"));
        Executor executor = httpServer.getExecutor();
        
        httpServer.start();
        System.out.println("http server staarted");
        
    }
   
}

class customhttphandler implements HttpHandler{
    
    private String rootdirectory;
    public customhttphandler(String rootdirectory){
        this.rootdirectory=rootdirectory;
    }
    public void handle(HttpExchange httpExchange ) throws IOException{
        System.out.println("huiisjfdisnjd");
        URI uri =httpExchange.getRequestURI();
        System.out.println(uri.getPath());
        
        File file =new File(rootdirectory+uri.getPath()).getCanonicalFile();
        Headers responseHeader = httpExchange.getResponseHeaders();
        if(uri.toString().contains(".mp3")){
            responseHeader.set("Content-Type", "audio/MP3");
        }
        else{
            responseHeader.set("Content-Type", "application/vnd.apple.mpegurl");
            
        }
        if(file.exists()){
            byte[] bytes =Files.readAllBytes(Paths.get(file.toURI()));
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream outputStream =httpExchange.getResponseBody();
            outputStream.write(bytes);
        }
        
        
                
    
}
    
    
    
}
