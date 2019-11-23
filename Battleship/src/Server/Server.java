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
    
    public ServerWindow window = new ServerWindow(this);    
    public ArrayList<ClientListener> listeners = new ArrayList();
    public Game game;
    
    
    public Server() {
        game=new Game(this);
        ConnectionController controller = new ConnectionController();
        controller.setServer(this);
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
    
    public void enviarPaqueteMenosA(Package paq,int id) {
        int cont=0;
        for (ClientListener listener: listeners) {
            if (cont==id) {
                continue;
            }
            try {
                ObjectOutputStream out = new ObjectOutputStream(listener.socket.getOutputStream());
                out.writeObject(paq);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            cont++;
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
        switch (target) {
            case 1:
                if(p.x-1>=0&&p.y+1<20)
                    labels.add(this.game.LabelsP1[p.x-1][p.y+1]);
                if(p.y+1<20)
                    labels.add(this.game.LabelsP1[p.x][p.y+1]);
                if(p.x-1<20&&p.y+1<20)
                    labels.add(this.game.LabelsP1[p.x+1][p.y+1]);
                if(p.x+1<20)
                    labels.add(this.game.LabelsP1[p.x+1][p.y]);
                if(p.x+1<20&&p.y-1>=0)
                    labels.add(this.game.LabelsP1[p.x+1][p.y-1]);
                if(p.y-1>=0)
                    labels.add(this.game.LabelsP1[p.x][p.y-1]);
                if(p.x-1>=0&&p.y-1>=0)
                    labels.add(this.game.LabelsP1[p.x-1][p.y-1]);
                if(p.x-1>=0)
                    labels.add(this.game.LabelsP1[p.x-1][p.y]);
                
                break;
            case 2:
                if(p.x-1>=0&&p.y+1<20)
                    labels.add(this.game.LabelsP2[p.x-1][p.y+1]);
                if(p.y+1<20)
                    labels.add(this.game.LabelsP2[p.x][p.y+1]);
                if(p.x-1<20&&p.y+1<20)
                    labels.add(this.game.LabelsP2[p.x+1][p.y+1]);
                if(p.x+1<20)
                    labels.add(this.game.LabelsP2[p.x+1][p.y]);
                if(p.x+1<20&&p.y-1>=0)
                    labels.add(this.game.LabelsP2[p.x+1][p.y-1]);
                if(p.y-1>=0)
                    labels.add(this.game.LabelsP2[p.x][p.y-1]);
                if(p.x-1>=0&&p.y-1>=0)
                    labels.add(this.game.LabelsP2[p.x-1][p.y-1]);
                if(p.x-1>=0)
                    labels.add(this.game.LabelsP2[p.x-1][p.y]);
                
                break;
                
            case 3:
                if(p.x-1>=0&&p.y+1<20)
                    labels.add(this.game.LabelsP3[p.x-1][p.y+1]);
                if(p.y+1<20)
                    labels.add(this.game.LabelsP3[p.x][p.y+1]);
                if(p.x-1<20&&p.y+1<20)
                    labels.add(this.game.LabelsP3[p.x+1][p.y+1]);
                if(p.x+1<20)
                    labels.add(this.game.LabelsP3[p.x+1][p.y]);
                if(p.x+1<20&&p.y-1>=0)
                    labels.add(this.game.LabelsP3[p.x+1][p.y-1]);
                if(p.y-1>=0)
                    labels.add(this.game.LabelsP3[p.x][p.y-1]);
                if(p.x-1>=0&&p.y-1>=0)
                    labels.add(this.game.LabelsP3[p.x-1][p.y-1]);
                if(p.x-1>=0)
                    labels.add(this.game.LabelsP3[p.x-1][p.y]);
                
                break;
            case 4:
                if(p.x-1>=0&&p.y+1<20)
                    labels.add(this.game.LabelsP4[p.x-1][p.y+1]);
                if(p.y+1<20)
                    labels.add(this.game.LabelsP4[p.x][p.y+1]);
                if(p.x-1<20&&p.y+1<20)
                    labels.add(this.game.LabelsP4[p.x+1][p.y+1]);
                if(p.x+1<20)
                    labels.add(this.game.LabelsP4[p.x+1][p.y]);
                if(p.x+1<20&&p.y-1>=0)
                    labels.add(this.game.LabelsP4[p.x+1][p.y-1]);
                if(p.y-1>=0)
                    labels.add(this.game.LabelsP4[p.x][p.y-1]);
                if(p.x-1>=0&&p.y-1>=0)
                    labels.add(this.game.LabelsP4[p.x-1][p.y-1]);
                if(p.x-1>=0)
                    labels.add(this.game.LabelsP4[p.x-1][p.y]);
                
                break;
            default:
                break;
        }
        
        return labels;
    }
    
}
