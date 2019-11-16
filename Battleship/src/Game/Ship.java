/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Server.Server;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebasgamboa
 */
public class Ship extends Thread implements Serializable{

    public boolean client;
    //public Server server;
    
    public Ship(boolean c){
        this.client=c;
    }
    
    public void init(Server s){
        //this.server=s;
    }
    
    @Override
    public void run(){
        try {
            sleep(3000);
            client=false;
        } catch (InterruptedException ex) {
            Logger.getLogger(Ship.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
