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

import Packages.AttackPackage;
import Packages.AttackReceivedPackage;
import Packages.DeadPackage;
import Packages.GrafoPackage;
import Packages.LabelsPackage;
import Packages.LogicBoardPackage;
import java.net.*; 
import java.io.*; 
import Packages.Package;
import Packages.ShipPackage;
import Packages.hitsPackage;
import javax.swing.JOptionPane;

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
                        boolean f=true;
                        AttackPackage AT=(AttackPackage) paq;
                        Server.instancia().game.Attack(AT.attacks, AT.target,AT.type,AT.origin);
                        AttackReceivedPackage paq2=new AttackReceivedPackage(AT.attacks,AT.target,"Attack to player "+AT.target,AT.origin,f);
                        Server.instancia().enviarPaquete(paq2);
                        break;
                        
                    case "labels":
                        LabelsPackage L= (LabelsPackage) paq;
                        if(L.id==1){
                            Server.instancia().game.LabelsP1=L.board;
                        }
                        else if(L.id==2){
                            Server.instancia().game.LabelsP2=L.board;
                        }
                        else if(L.id==3){
                            Server.instancia().game.LabelsP3=L.board;
                        }
                        else if(L.id==4){
                            Server.instancia().game.LabelsP4=L.board;
                        }
                        break;
                        
                    case "ship":
                        ShipPackage sh=(ShipPackage) paq;
                        sh.discoveries=Server.instancia().getDiscoveries(sh.point, sh.target);
                        Server.instancia().enviarPaquete(sh);
                        break;
                        
                    case "hits":
                        hitsPackage AR=(hitsPackage) paq;
                        if(AR.origin==1){
                            Server.instancia().enviarPaqueteA(AR, 0);
                        }
                        else if(AR.origin==2){
                            Server.instancia().enviarPaqueteA(AR, 1);
                        }
                        else if(AR.origin==3){
                            Server.instancia().enviarPaqueteA(AR, 2);
                        }
                        else if(AR.origin==4){
                            Server.instancia().enviarPaqueteA(AR, 3);
                        }
                        break;
                        
                    case "dead":
                        DeadPackage d=(DeadPackage) paq;
                        Server.instancia().game.deadUsers.add(d.id);
                        if((Server.instancia().listeners.size()-1)==(Server.instancia().game.deadUsers.size())){
                            if(!Server.instancia().game.deadUsers.contains(1)){
                                JOptionPane.showMessageDialog(null,"Gano el jugador 1");
                            }
                            else if(!Server.instancia().game.deadUsers.contains(2)){
                                JOptionPane.showMessageDialog(null,"Gano el jugador 2");
                            }
                            else if(!Server.instancia().game.deadUsers.contains(3)){
                                JOptionPane.showMessageDialog(null,"Gano el jugador 3");
                            }
                            else if(!Server.instancia().game.deadUsers.contains(4)){
                                JOptionPane.showMessageDialog(null,"Gano el jugador 4");
                            }
                        }
                }
            }
            catch(IOException | ClassNotFoundException e) { 
                 System.out.println(e); 
            }
        }
    }

} 

