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

import Packages.AristasPackage;
import Packages.AttackPackage;
import Packages.AttackReceivedPackage;
import Packages.BitacoraTextPackage;
import Packages.ComodinPackage;
import Packages.DeadPackage;
import Packages.GrafoPackage;
import Packages.LabelsPackage;
import Packages.LogicBoardPackage;
import Packages.MoneyPackage;
import java.net.*; 
import java.io.*; 
import Packages.Package;
import Packages.ShipPackage;
import Packages.TradeAcceptPackage;
import Packages.TradeClass;
import Packages.hitsPackage;
import javax.swing.JOptionPane;

public class ClientListener extends Thread
{ 
    public Socket socket;
    public Server server;
    
    public ClientListener(Socket socket,Server s){
        this.socket = socket;
        this.server=s;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Package paq = (Package) in.readObject();
                switch (paq.tipo) {
                    case "chat": 
                        this.server.enviarPaquete(paq);
                        break;
                    
                    case "Grafo":
                        GrafoPackage grafo= (GrafoPackage) paq;
                        if(grafo.id==1){
                            this.server.game.grafoP1=grafo.grafo;
                        }
                        else if(grafo.id==2){
                            this.server.game.grafoP2=grafo.grafo;
                        }
                        else if(grafo.id==3){
                            this.server.game.grafoP3=grafo.grafo;
                        }
                        else if(grafo.id==4){
                            this.server.game.grafoP4=grafo.grafo;
                        }
                        break;
                        
                    case "LogicBoard":
                        LogicBoardPackage LB= (LogicBoardPackage) paq;
                        if(LB.id==1){
                            this.server.game.LogicBoardPlayer1=LB.board;
                        }
                        else if(LB.id==2){
                            this.server.game.LogicBoardPlayer2=LB.board;
                        }
                        else if(LB.id==3){
                            this.server.game.LogicBoardPlayer3=LB.board;
                        }
                        else if(LB.id==4){
                            this.server.game.LogicBoardPlayer4=LB.board;
                        }
                        break;
                        
                        
                    case "Turn":
                        this.server.game.userTurn=false;
                        break;
                        
                    case "Attack":
                        System.out.println("me atacan");
                        boolean f=true;
                        AttackPackage AT=(AttackPackage) paq;
                        this.server.game.Attack(AT.attacks, AT.target,AT.type,AT.origin);
                        AttackReceivedPackage paq2=new AttackReceivedPackage(AT.attacks,AT.target,"Attack to player "+AT.target,AT.origin,f);
                        this.server.enviarPaquete(paq2);
                        break;
                        
                    case "labels":
                        LabelsPackage L= (LabelsPackage) paq;
                        if(L.id==1){
                            this.server.game.LabelsP1=L.board;
                        }
                        else if(L.id==2){
                            this.server.game.LabelsP2=L.board;
                        }
                        else if(L.id==3){
                            this.server.game.LabelsP3=L.board;
                        }
                        else if(L.id==4){
                            this.server.game.LabelsP4=L.board;
                        }
                        break;
                        
                    case "ship":
                        ShipPackage sh=(ShipPackage) paq;
                        sh.discoveries=this.server.getDiscoveries(sh.point, sh.target);
                        this.server.enviarPaquete(sh);
                        break;
                        
                    case "hits":
                        hitsPackage AR=(hitsPackage) paq;
                        if(AR.origin==1){
                            this.server.enviarPaqueteA(AR, 0);
                        }
                        else if(AR.origin==2){
                            this.server.enviarPaqueteA(AR, 1);
                        }
                        else if(AR.origin==3){
                            this.server.enviarPaqueteA(AR, 2);
                        }
                        else if(AR.origin==4){
                            this.server.enviarPaqueteA(AR, 3);
                        }
                        break;
                        
                    case "dead":
                        DeadPackage d=(DeadPackage) paq;
                        this.server.game.deadUsers.add(d.id);
                        if((this.server.listeners.size()-1)==(this.server.game.deadUsers.size())){
                            if(!this.server.game.deadUsers.contains(1)){
                                JOptionPane.showMessageDialog(this.server.window,"Gano el jugador 1");
                            }
                            else if(!this.server.game.deadUsers.contains(2)){
                                JOptionPane.showMessageDialog(this.server.window,"Gano el jugador 2");
                            }
                            else if(!this.server.game.deadUsers.contains(3)){
                                JOptionPane.showMessageDialog(this.server.window,"Gano el jugador 3");
                            }
                            else if(!this.server.game.deadUsers.contains(4)){
                                JOptionPane.showMessageDialog(this.server.window,"Gano el jugador 4");
                            }
                        }
                        break;
                        
                    case "trade":
                        TradeClass TC=(TradeClass) paq;
                        this.server.enviarPaqueteA(TC, TC.player-1);
                        break;
                        
                    case "tradeAccept":
                        TradeAcceptPackage tap=(TradeAcceptPackage) paq;
                        this.server.enviarPaqueteA(tap, tap.player-1);
                        break;
                        
                    case "aristas":
                        AristasPackage AP = (AristasPackage) paq;
                        this.server.enviarPaqueteA(AP, AP.target-1);
                        break;
                        
                    case "comodin":
                        ComodinPackage CP = (ComodinPackage) paq;
                        switch (CP.origin) {
                            case 1:
                                this.server.game.P1Comodin=!this.server.game.P1Comodin;
                                break;
                            case 2:
                                this.server.game.P2Comodin=!this.server.game.P2Comodin;
                                break;
                            case 3:
                                this.server.game.P3Comodin=!this.server.game.P3Comodin;
                                break;
                            case 4:
                                this.server.game.P4Comodin=!this.server.game.P4Comodin;
                                break;
                            default:
                                break;
                        }
                        break;
                        
                    case "bitacora":
                        BitacoraTextPackage BTP =(BitacoraTextPackage) paq;
                        this.server.enviarPaquete(BTP);
                        break;
                        
                    case "money":
                        MoneyPackage MP = (MoneyPackage) paq;
                        this.server.enviarPaqueteA(MP, MP.target-1);
                        break;

                }
            }
            catch(IOException | ClassNotFoundException e) { 
                 System.out.println(e); 
            }
        }
    }
}
