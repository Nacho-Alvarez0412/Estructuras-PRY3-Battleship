/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Clients.Client;
import Packages.IDPackage;
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
        int cont=1;
        try {
            ServerSocket serverSocket = new ServerSocket(5000);  
            while (true) {
                if(Server.instancia().window.numberOfPlayers>Server.instancia().listeners.size()){
                    Socket socket = serverSocket.accept();
                    ClientListener listener = new ClientListener(socket);
                    Server.instancia().addClient(listener);
                    IDPackage paq=new IDPackage(cont);
                    Server.instancia().enviarPaqueteA(paq, cont-1);
                    cont++;
                    listener.start();
                }
                else{
                    Server.instancia().window.getTextArea().append("Ya se conectaron todos los jugadores!");
                    break;
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
