/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Clients.Client;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebasgamboa
 */
public class TempleThread extends Thread implements Serializable{
    public Client client;
    public boolean vivo=true;
    
    public TempleThread(Client c){
        this.client=c;
    }
    
    @Override
    public void run(){
        while(vivo){
            try {
                sleep(300000);
                this.client.window.getComodin().setEnabled(true);
                this.client.canComodin=true;
            } catch (InterruptedException ex) {
                Logger.getLogger(TempleThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
