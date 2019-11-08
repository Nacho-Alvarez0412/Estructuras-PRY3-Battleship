/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebasgamboa
 */
public class ConnectionController extends Thread {
    
    
    
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            
            while (true) {
                Socket socket = serverSocket.accept();
                
                ClientListener listener = new ClientListener(socket);
                Server.instancia().addClient(listener);
                listener.start();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
