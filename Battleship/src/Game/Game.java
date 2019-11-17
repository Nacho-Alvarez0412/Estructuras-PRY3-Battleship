/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Clients.BoardLabel;
import Packages.AttackReceivedPackage;
import Packages.TurnMesagePackage;
import Packages.TurnPackage;
import Server.Server;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
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
    7=remolino
    */

public class Game extends Thread implements Serializable{
    
    public int turn=1;
    public ArrayList<Integer> deadUsers=new ArrayList<>();
    public boolean game=true;
    
    public int[][] LogicBoardPlayer1=new int[20][20];
    public int[][] LogicBoardPlayer2=new int[20][20];
    public int[][] LogicBoardPlayer3=new int[20][20];
    public int[][] LogicBoardPlayer4=new int[20][20];
    
    public Grafo grafoP1;
    public Grafo grafoP2;
    public Grafo grafoP3;
    public Grafo grafoP4;
    public Server server;
    
    public BoardLabel[][] LabelsP1=new BoardLabel[20][20];
    public BoardLabel[][] LabelsP2=new BoardLabel[20][20];
    public BoardLabel[][] LabelsP3=new BoardLabel[20][20];
    public BoardLabel[][] LabelsP4=new BoardLabel[20][20];
    
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
    
    public void Attack(ArrayList<Point> points,int target,String tipo,int origen){
        boolean hitLanded=false;
        switch (target) {
            case 1:
                for(Point p:points){
                    System.out.println(p.x);
                    System.out.println(p.y);
                    if(this.LogicBoardPlayer1[p.x][p.y]!=0){
                        if(this.LogicBoardPlayer1[p.x][p.y]==7){
                            System.out.println("remolino");
                            Random randomGenerator = new Random();
                            int x1 = randomGenerator.nextInt(20);
                            int y1 = randomGenerator.nextInt(20);
                            int x2 = randomGenerator.nextInt(20);
                            int y2 = randomGenerator.nextInt(20);
                            int x3 = randomGenerator.nextInt(20);
                            int y3 = randomGenerator.nextInt(20);
                            ArrayList<Point> rAttacks=new ArrayList<>();
                            rAttacks.add(new Point(x1,y1));
                            rAttacks.add(new Point(x2,y2));
                            rAttacks.add(new Point(x3,y3));
                            this.Attack(rAttacks, origen, tipo,target);
                            AttackReceivedPackage paq2=new AttackReceivedPackage(rAttacks,origen,"Attack to player "+origen,origen,hitLanded);
                            server.enviarPaquete(paq2);
                        }else{
                            System.out.println("me pegaron");
                            this.LogicBoardPlayer1[p.x][p.y]=0;
                            
                            hitLanded=true;
                            if(tipo=="multi"){
                                ArrayList<Point> points2=new ArrayList<>();
                                for(int i=0;i<4;i++){
                                    Random randomGenerator = new Random();
                                    int x = randomGenerator.nextInt(20);
                                    int y = randomGenerator.nextInt(20);
                                    points2.add(new Point(x,y));
                                }

                                this.Attack(points2, target, tipo,origen);
                                AttackReceivedPackage paq2=new AttackReceivedPackage(points2,target,"Attack to player "+target,origen,hitLanded);
                                server.enviarPaquete(paq2);
                            }
                        }
                    }
                }
                break;
            case 2:
                for(Point p:points){
                    if(this.LogicBoardPlayer2[p.x][p.y]!=0){
                        if(this.LogicBoardPlayer2[p.x][p.y]==7){
                            System.out.println("remolino");
                            Random randomGenerator = new Random();
                            int x1 = randomGenerator.nextInt(20);
                            int y1 = randomGenerator.nextInt(20);
                            int x2 = randomGenerator.nextInt(20);
                            int y2 = randomGenerator.nextInt(20);
                            int x3 = randomGenerator.nextInt(20);
                            int y3 = randomGenerator.nextInt(20);
                            ArrayList<Point> rAttacks=new ArrayList<>();
                            rAttacks.add(new Point(x1,y1));
                            rAttacks.add(new Point(x2,y2));
                            rAttacks.add(new Point(x3,y3));
                            this.Attack(rAttacks, origen, tipo,target);
                            AttackReceivedPackage paq2=new AttackReceivedPackage(rAttacks,origen,"Attack to player "+origen,target,hitLanded);
                            server.enviarPaquete(paq2);
                        }else{
                            System.out.println("me pegaron");
                            this.LogicBoardPlayer2[p.x][p.y]=0;
                            hitLanded=true;
                            if(tipo=="multi"){
                                ArrayList<Point> points2=new ArrayList<>();
                                for(int i=0;i<4;i++){
                                    Random randomGenerator = new Random();
                                    int x = randomGenerator.nextInt(20);
                                    int y = randomGenerator.nextInt(20);
                                    points2.add(new Point(x,y));
                                }

                                this.Attack(points2, target, tipo,origen);
                                AttackReceivedPackage paq2=new AttackReceivedPackage(points2,target,"Attack to player "+target,origen,hitLanded);
                                server.enviarPaquete(paq2);
                            }
                        }
                    }
                }
                break;
            case 3:
                 for(Point p:points){
                    if(this.LogicBoardPlayer3[p.x][p.y]!=0){
                        if(this.LogicBoardPlayer3[p.x][p.y]==7){
                            System.out.println("remolino");
                            Random randomGenerator = new Random();
                            int x1 = randomGenerator.nextInt(20);
                            int y1 = randomGenerator.nextInt(20);
                            int x2 = randomGenerator.nextInt(20);
                            int y2 = randomGenerator.nextInt(20);
                            int x3 = randomGenerator.nextInt(20);
                            int y3 = randomGenerator.nextInt(20);
                            ArrayList<Point> rAttacks=new ArrayList<>();
                            rAttacks.add(new Point(x1,y1));
                            rAttacks.add(new Point(x2,y2));
                            rAttacks.add(new Point(x3,y3));
                            this.Attack(rAttacks, origen, tipo,target);
                            AttackReceivedPackage paq2=new AttackReceivedPackage(rAttacks,origen,"Attack to player "+origen,target,hitLanded);
                            server.enviarPaquete(paq2);
                        }else{
                            System.out.println("me pegaron");
                            this.LogicBoardPlayer3[p.x][p.y]=0;
                            hitLanded=true;
                            if(tipo=="multi"){
                                ArrayList<Point> points2=new ArrayList<>();
                                for(int i=0;i<4;i++){
                                    Random randomGenerator = new Random();
                                    int x = randomGenerator.nextInt(20);
                                    int y = randomGenerator.nextInt(20);
                                    points2.add(new Point(x,y));
                                }

                                this.Attack(points2, target, tipo,origen);
                                AttackReceivedPackage paq2=new AttackReceivedPackage(points2,target,"Attack to player "+target,origen,hitLanded);
                                server.enviarPaquete(paq2);
                            }
                        }
                    }
                }
                break;
            case 4:
                 for(Point p:points){
                    if(this.LogicBoardPlayer4[p.x][p.y]!=0){
                        if(this.LogicBoardPlayer4[p.x][p.y]==7){
                            System.out.println("remolino");
                            Random randomGenerator = new Random();
                            int x1 = randomGenerator.nextInt(20);
                            int y1 = randomGenerator.nextInt(20);
                            int x2 = randomGenerator.nextInt(20);
                            int y2 = randomGenerator.nextInt(20);
                            int x3 = randomGenerator.nextInt(20);
                            int y3 = randomGenerator.nextInt(20);
                            ArrayList<Point> rAttacks=new ArrayList<>();
                            rAttacks.add(new Point(x1,y1));
                            rAttacks.add(new Point(x2,y2));
                            rAttacks.add(new Point(x3,y3));
                            this.Attack(rAttacks, origen, tipo,target);
                            AttackReceivedPackage paq2=new AttackReceivedPackage(rAttacks,origen,"Attack to player "+origen,target,hitLanded);
                            server.enviarPaquete(paq2);
                        }else{
                            System.out.println("me pegaron");
                            this.LogicBoardPlayer4[p.x][p.y]=0;
                            hitLanded=true;
                            if(tipo=="multi"){
                                ArrayList<Point> points2=new ArrayList<>();
                                for(int i=0;i<4;i++){
                                    Random randomGenerator = new Random();
                                    int x = randomGenerator.nextInt(20);
                                    int y = randomGenerator.nextInt(20);
                                    points2.add(new Point(x,y));
                                }

                                this.Attack(points2, target, tipo,origen);
                                AttackReceivedPackage paq2=new AttackReceivedPackage(points2,target,"Attack to player "+target,origen,hitLanded);
                                server.enviarPaquete(paq2);
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
    
    @Override
    public void run(){
        while(game){
            TurnMesagePackage t=new TurnMesagePackage(turn);
            server.enviarPaquete(t);     
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
        System.out.println("se termino");
    }
    
    
    
    
}
