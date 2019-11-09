/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Packages.Package;
import Packages.ChatPackage;
import Packages.IDPackage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebasgamboa
 */
public class ServerListener extends Thread {
    
    public Client client;
    
    public void init(Client client){
        this.client = client;
    }
    
    @Override
    public void run() {        
        try {
            while (true) {
                ObjectInputStream in = new ObjectInputStream(Client.instancia().socket.getInputStream());
                Package paq = (Package) in.readObject();
                
                switch (paq.tipo) {
                    
                    case "chat": 
                        ChatPackage chat = (ChatPackage) paq;
                        Client.instancia().window.addMessage(chat.mensaje);
                        break; 
                        
                    case "ID":
                        IDPackage ID = (IDPackage) paq;
                        this.client.setID(ID.id);
                        break;
                        
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
