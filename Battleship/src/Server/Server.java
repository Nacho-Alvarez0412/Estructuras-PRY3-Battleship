/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Clients.BoardLabel;
import Game.Game;
import Packages.Package;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebasgamboa
 */
public class Server implements Serializable{
    
    
    //Singleton
    private static Server _instancia = null;
    public static Server instancia() {
        if (_instancia == null) {
            _instancia = new Server();
        }
        return _instancia;
    }
    
    public ServerWindow window = new ServerWindow();    
    public ArrayList<ClientListener> listeners = new ArrayList();
    public Game game;
    
    
    public Server() {
        game=new Game(this);
        ConnectionController controller = new ConnectionController();
        controller.start();
        window.setVisible(true);
        window.getTextArea().append("Servidor activo, esperando clientes...\n");
    }
    
    public void addClient(ClientListener listener) {
        listeners.add(listener);
        window.getTextArea().append("Cliente Agregado\n");
    }
    
    public void enviarPaquete(Package paq) {
        for (ClientListener listener: listeners) {
            /*if (listener == clienteExcluido) {
                continue;
            }*/
            try {
                ObjectOutputStream out = new ObjectOutputStream(listener.socket.getOutputStream());
                out.writeObject(paq);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void enviarPaqueteA(Package paq,int id) {
        ClientListener listener= listeners.get(id);
        try {
            ObjectOutputStream out = new ObjectOutputStream(listener.socket.getOutputStream());
            out.writeObject(paq);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<BoardLabel> getDiscoveries(Point p,int target){
        ArrayList<BoardLabel> labels=new ArrayList<>();
        if(target==1){
            labels.add(this.game.LabelsP1[p.x-1][p.y+1]);
            labels.add(this.game.LabelsP1[p.x][p.y+1]);
            labels.add(this.game.LabelsP1[p.x+1][p.y+1]);
            labels.add(this.game.LabelsP1[p.x+1][p.y]);
            labels.add(this.game.LabelsP1[p.x+1][p.y-1]);
            labels.add(this.game.LabelsP1[p.x][p.y-1]);
            labels.add(this.game.LabelsP1[p.x-1][p.y-1]);
            labels.add(this.game.LabelsP1[p.x-1][p.y]);
        }
        else if(target==2){
            
            labels.add(this.game.LabelsP2[p.x-1][p.y+1]);
            labels.add(this.game.LabelsP2[p.x][p.y+1]);
            labels.add(this.game.LabelsP2[p.x+1][p.y+1]);
            labels.add(this.game.LabelsP2[p.x+1][p.y]);
            labels.add(this.game.LabelsP2[p.x+1][p.y-1]);
            labels.add(this.game.LabelsP2[p.x][p.y-1]);
            labels.add(this.game.LabelsP2[p.x-1][p.y-1]);
            labels.add(this.game.LabelsP2[p.x-1][p.y]);
        }
        else if(target==3){
            labels.add(this.game.LabelsP2[p.x-1][p.y+1]);
            labels.add(this.game.LabelsP2[p.x][p.y+1]);
            labels.add(this.game.LabelsP2[p.x+1][p.y+1]);
            labels.add(this.game.LabelsP2[p.x+1][p.y]);
            labels.add(this.game.LabelsP2[p.x+1][p.y-1]);
            labels.add(this.game.LabelsP2[p.x][p.y-1]);
            labels.add(this.game.LabelsP2[p.x-1][p.y-1]);
            labels.add(this.game.LabelsP2[p.x-1][p.y]);
        }
        else if(target==4){
            labels.add(this.game.LabelsP2[p.x-1][p.y+1]);
            labels.add(this.game.LabelsP2[p.x][p.y+1]);
            labels.add(this.game.LabelsP2[p.x+1][p.y+1]);
            labels.add(this.game.LabelsP2[p.x+1][p.y]);
            labels.add(this.game.LabelsP2[p.x+1][p.y-1]);
            labels.add(this.game.LabelsP2[p.x][p.y-1]);
            labels.add(this.game.LabelsP2[p.x-1][p.y-1]);
            labels.add(this.game.LabelsP2[p.x-1][p.y]);
        }
        
        return labels;
    }
    
}
