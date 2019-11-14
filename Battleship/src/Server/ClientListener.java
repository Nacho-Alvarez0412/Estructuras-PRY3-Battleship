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

import Packages.GrafoPackage;
import Packages.LogicBoardPackage;
import java.net.*; 
import java.io.*; 
import Packages.Package;

public class ClientListener extends Thread
{ 
    public Socket socket;
    
    public ClientListener(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Package paq = (Package) in.readObject();
                switch (paq.tipo) {
                    case "chat": 
                        Server.instancia().enviarPaquete(paq);
                        break;
                    
                    case "Grafo":
                        GrafoPackage grafo= (GrafoPackage) paq;
                        if(grafo.id==1){
                            Server.instancia().game.grafoP1=grafo.grafo;
                        }
                        else if(grafo.id==2){
                            Server.instancia().game.grafoP2=grafo.grafo;
                        }
                        else if(grafo.id==3){
                            Server.instancia().game.grafoP3=grafo.grafo;
                        }
                        else if(grafo.id==4){
                            Server.instancia().game.grafoP4=grafo.grafo;
                        }
                        break;
                        
                    case "LogicBoard":
                        LogicBoardPackage LB= (LogicBoardPackage) paq;
                        if(LB.id==1){
                            Server.instancia().game.LogicBoardPlayer1=LB.board;
                        }
                        else if(LB.id==2){
                            Server.instancia().game.LogicBoardPlayer2=LB.board;
                        }
                        else if(LB.id==3){
                            Server.instancia().game.LogicBoardPlayer3=LB.board;
                        }
                        else if(LB.id==4){
                            Server.instancia().game.LogicBoardPlayer4=LB.board;
                        }
                        break;
                        
                        
                    case "Turn":
                        Server.instancia().game.userTurn=false;
                        break;
                        
                    case "Attack":
                        System.out.println("me atacan");
                        break;
                }
            }
            catch(IOException | ClassNotFoundException e) { 
                 System.out.println(e); 
            }
        }
    }

} 

