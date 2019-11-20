/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Game.ArmoryThread;
import Game.Grafo;
import Game.MineThread;
import Game.TempleThread;
import Game.Vertice;
import Packages.Package;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author sebasgamboa
 */
public class Client implements Serializable{
    
    private static Client _instancia = null;
    public static Client instancia() {
        if (_instancia == null) {
            _instancia = new Client();
        }
        return _instancia;
    }
    
    public Socket socket;
    public ArrayList<MineThread> mine=new ArrayList<>();
    public ArrayList<ArmoryThread> armory=new ArrayList<>();
    public ArrayList<TempleThread> temple=new ArrayList<>();
    public int id;
    public int money=4000;
    public int acero=0;
    public int torpedos=0;
    public int multi=0;
    public int bombs=0;
    public int trumpedos=0;
    public int ships=0;
    public boolean torpedosE=false;
    public boolean multiE=false;
    public boolean bombsE=false;
    public boolean trumpedosE=false;
    public boolean shipsE=false;
    public boolean canComodin=false;  
    public boolean comodinOn=false;
    public int[][] LogicBoard=new int[20][20];
    public Grafo grafo=new Grafo();
    public int verticesDead=0;
    public ArrayList<Point> hitsP1=new ArrayList<>();
    public ArrayList<Point> hitsP2=new ArrayList<>();
    public ArrayList<Point> hitsP3=new ArrayList<>();
    public ArrayList<Point> hitsP4=new ArrayList<>();
    public ArrayList<Vertice> disconexosP1=new ArrayList<>();
    public ArrayList<Vertice> disconexosP2=new ArrayList<>();
    public ArrayList<Vertice> disconexosP3=new ArrayList<>();
    public ArrayList<Vertice> disconexosP4=new ArrayList<>();
    public boolean UsusarioVivo=true;
    public ClientWindow window = new ClientWindow(this);
    
    
    public Client() {
        //this.window.setRemolinos();
        window.setVisible(true);
    }
    
    public void conectar(String address, int port) throws IOException {
        socket = new Socket(address, port); 
        ServerListener listener = new ServerListener();
        listener.init(this);
        listener.start();
    }
    
    public void enviarPaquete(Package paq) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(paq);
    }
    
    public void setID(int id){
        this.id = id;
    }
}
