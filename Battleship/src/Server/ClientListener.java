/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author sebasgamboa
 */// 

import java.net.*; 
import java.io.*; 
import Packages.Package;
import Packages.ChatPackage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientListener extends Thread
{ 
    public Socket socket;
    
    public ClientListener(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Package paq = (Package) in.readObject();
                switch (paq.tipo) {
                    case "chat": {
                        Server.instancia().enviarPaquete(paq);
                    } break;
                }
            }
            catch(IOException | ClassNotFoundException e) { 
                 System.out.println(e); 
            }
        }
    }

} 

