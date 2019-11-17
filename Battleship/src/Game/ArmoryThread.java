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
public class ArmoryThread extends Thread implements Serializable{
    public Client client;
    public boolean vivo=true;
    public String tipo;
    
    public ArmoryThread(Client c,String t){
        this.client=c;
        this.tipo=t;
    }
    
    @Override
    public void run(){
        System.out.println("corro");
        System.out.println(tipo);
        if(null!=tipo)switch (tipo) {
            case "torpedo":
                //System.out.println("si");
                this.client.torpedosE=true;
                break;
            case "bomb":
                this.client.bombsE=true;
                break;
            case "multi":
                this.client.multiE=true;
                break;
            case "trumpedo":
                this.client.trumpedosE=true;
                break;
            case "ships":
                this.client.shipsE=true;
                break;
            default:
                break;
        }
        
        while(vivo){
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ArmoryThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(tipo=="torpedo"){
            this.client.torpedosE=false;
        }
        else if(tipo=="bomb"){
            this.client.bombsE=false;
        }
        else if(tipo=="multi"){
            this.client.multiE=false;
        }
        else if(tipo=="trumpedo"){
            this.client.trumpedosE=false;
        }
        else if(tipo=="ships"){
            this.client.shipsE=false;
        }
    }
    
}
