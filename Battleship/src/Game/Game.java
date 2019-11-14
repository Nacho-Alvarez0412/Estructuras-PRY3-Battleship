/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Packages.TurnPackage;
import Server.Server;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebasgamboa
 */



    /*
    Logic Board Info:

    1=connector
    2=mine
    3=armory
    4=temple
    5=market
    6=energy source
    */

public class Game extends Thread{
    
    public int turn=1;
    
    public int[][] LogicBoardPlayer1=new int[20][20];
    public int[][] LogicBoardPlayer2=new int[20][20];
    public int[][] LogicBoardPlayer3=new int[20][20];
    public int[][] LogicBoardPlayer4=new int[20][20];
    
    public Grafo grafoP1;
    public Grafo grafoP2;
    public Grafo grafoP3;
    public Grafo grafoP4;
    public Server server;
    public boolean userTurn;

    public Game(Server server) {
        this.server = server;
        userTurn = false;
    }
    
    public void changeTurn(){
        turn++;
        
        if(turn > server.listeners.size())
            turn = 1;
    }
    
    public int getTurn() {
        return turn;
    }

    public int[][] getLogicBoardPlayer1() {
        return LogicBoardPlayer1;
    }

    public int[][] getLogicBoardPlayer2() {
        return LogicBoardPlayer2;
    }

    public int[][] getLogicBoardPlayer3() {
        return LogicBoardPlayer3;
    }

    public int[][] getLogicBoardPlayer4() {
        return LogicBoardPlayer4;
    }

    public Grafo getGrafoP1() {
        return grafoP1;
    }

    public Grafo getGrafoP2() {
        return grafoP2;
    }

    public Grafo getGrafoP3() {
        return grafoP3;
    }

    public Grafo getGrafoP4() {
        return grafoP4;
    }
    
    public void run(){
        while(true){
            server.enviarPaquete(new TurnPackage(this.turn));
            userTurn = true;
            while(userTurn){
                
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            changeTurn();            
        }
        
    }
    
    
    
    
}
