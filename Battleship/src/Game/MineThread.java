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
public class MineThread extends Thread implements Serializable{
    public Client client;
    public boolean vivo=true;
    
    public MineThread(Client c){
        this.client=c;
    }
    
    @Override
    public void run(){
        while(vivo){
            try {
                sleep(6000);
                this.client.acero+=500;
                this.client.window.setAcero();
            } catch (InterruptedException ex) {
                Logger.getLogger(MineThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
