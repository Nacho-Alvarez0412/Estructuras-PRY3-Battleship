/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Game.Arista;
import Game.Vertice;
import Packages.AristasPackage;
import Packages.AttackPackage;
import Packages.AttackReceivedPackage;
import Packages.BitacoraTextPackage;
import Packages.Package;
import Packages.ChatPackage;
import Packages.ClearPackage;
import Packages.DeadPackage;
import Packages.GrafoPackage;
import Packages.IDPackage;
import Packages.MoneyPackage;
import Packages.ShipPackage;
import Packages.TradeAcceptPackage;
import Packages.TradeClass;
import Packages.TurnMesagePackage;
import Packages.TurnPackage;
import Packages.hitsPackage;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author sebasgamboa
 */
public class ServerListener extends Thread {
    
    public Client client;
    boolean hitLanded=false;
    
    public void init(Client client){
        this.client = client;
    }
    
    @Override
    public void run() {        
        try {
            while (true) {
                ObjectInputStream in = new ObjectInputStream(Client.instancia().socket.getInputStream());
                Package paq = (Package) in.readObject();                
                switch (paq.tipo) {                    
                    case "chat": 
                        ChatPackage chat = (ChatPackage) paq;
                        Client.instancia().window.addMessage(chat.mensaje);
                        break; 
                        
                    case "ID":
                        IDPackage ID = (IDPackage) paq;
                        this.client.setID(ID.id);
                        this.client.window.setTitle("Player " +ID.id);
                        break;
                        
                    case "Turn":
                        TurnPackage turn = (TurnPackage) paq;
                        if(turn.turn==this.client.id){
                            System.out.println("es mi turno");
                            this.client.window.getEndTurn().setEnabled(true);
                        }
                        break;
                        
                    case "AttackReceived":
                        AttackReceivedPackage AR=(AttackReceivedPackage) paq;
                        String res=AR.message;
                        if(AR.target==this.client.id){
                            for(Point p:AR.attacks){
                                if(this.client.LogicBoard[p.x][p.y]!=0&&this.client.LogicBoard[p.x][p.y]!=7&&
                                        !this.client.comodinOn){
                                    this.client.LogicBoard[p.x][p.y]=0;
                                    this.client.window.board[p.x][p.y].setIcon(
                                            new ImageIcon(getClass().getResource("/Images/explosion2.png")));
                                    String res2="Atack in ("+p.x+","+p.y+") landed";
                                    BitacoraTextPackage BTP=new BitacoraTextPackage(res2);
                                    
                                    this.client.enviarPaquete(BTP);
                                    AR.hitLanded=true;
                                    hitsPackage paq2=new hitsPackage(AR.attacks,AR.target,AR.origin);
                                    this.client.enviarPaquete(paq2);
                                    this.client.window.board[p.x][p.y].verticeName.hits-=1;
                                    if(this.client.window.board[p.x][p.y].verticeName.hits==0){
                                        this.client.window.board[p.x][p.y].verticeName.vivo=false;
                                        System.out.println("ded");
                                        this.client.verticesDead+=1;
                                        System.out.println(this.client.window.board[p.x][p.y].verticeName.dato);
                                        
                                        if(this.client.window.board[p.x][p.y].verticeName.dato==6){
                                            MoneyPackage monPaq = new MoneyPackage(AR.origin,12000);
                                            this.client.enviarPaquete(monPaq);
                                        }
                                        
                                        for(Point xy : this.client.window.board[p.x][p.y].verticeName.point){
                                            ArrayList<Point> OwnTargets = addPoints(xy);
                                            AttackPackage ATP=new AttackPackage(OwnTargets,this.client.id,"Explosion",this.client.id);
                                            try {
                                                this.client.enviarPaquete(ATP);
                                            } catch (IOException ex) {
                                                Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        
                                        if(this.client.window.board[p.x][p.y].verticeName.dato==1||this.client.window.board[p.x][p.y].verticeName.dato==6){
                                            ArrayList<Vertice> v=new ArrayList<>();
                                            System.out.println("entre");
                                            for(Arista a:this.client.window.board[p.x][p.y].verticeName.aristas){
                                                System.out.println("x2");
                                                v.add(a.dato);
                                            }
                                            AristasPackage arpaq = new AristasPackage(v,AR.origin,this.client.id);
                                            this.client.enviarPaquete(arpaq);
                                        }
                                    }
                                    GrafoPackage gr=new GrafoPackage(this.client.grafo,this.client.id);
                                    this.client.enviarPaquete(gr);
                                    if(this.client.verticesDead==this.client.grafo.grafo.size()){
                                        this.client.UsusarioVivo=false;
                                        DeadPackage dead=new DeadPackage(this.client.id);
                                        client.enviarPaquete(dead);
                                    }
                                }else if(this.client.comodinOn){
                                    this.client.comodinNum-=1;
                                    if(this.client.comodinNum==0){
                                        this.client.window.resetComodin();
                                    }
                                    String res2="Atack in ("+p.x+","+p.y+") landed, but has shield on";
                                    BitacoraTextPackage BTP=new BitacoraTextPackage(res2);
                                    this.client.enviarPaquete(BTP);
                                    AR.hitLanded=false;
                                }
                                else{
                                    String res2=" Atack in ("+p.x+","+p.y+") failed";
                                    BitacoraTextPackage BTP=new BitacoraTextPackage(res2);
                                    this.client.enviarPaquete(BTP);
                                    AR.hitLanded=false;
                                }
                             }
                        }
                        if(AR.origin==this.client.id){
                            switch (AR.target) {
                                case 1:
                                    this.client.window.getP1().doClick();
                                    break;
                                case 2:
                                    this.client.window.getP2().doClick();
                                    break;
                                case 3:
                                    this.client.window.getP3().doClick();
                                    break;
                                case 4:
                                    this.client.window.getP4().doClick();
                                    break;
                                default:
                                    break;
                            }
                        }
                        
                        this.client.window.setBitacoraText(res);
                        break;
                        
                    case "TurnMessage":
                        TurnMesagePackage T=(TurnMesagePackage) paq;
                        this.client.window.setBitacoraText("Es el turno de Player "+T.turn);
                        break;
                        
                    case "ship":
                        ShipPackage s=(ShipPackage) paq;
                        if(s.origin==this.client.id){
                            for(BoardLabel bl:s.discoveries){
                                //System.out.println("si");
                                if(bl.getIcon()!=null&&bl.j<20&&bl.i<20){
                                    this.client.window.enemyBoard[bl.i][bl.j].setIcon(bl.getIcon());
                                }
                            }
                            this.client.window.enemyBoard[s.point.x][s.point.y].setIcon(new ImageIcon(getClass().getResource("/Images/ship.png")));
                        }
                        break;
                        
                    case "hits":
                        hitsPackage H=(hitsPackage) paq;
                        switch (H.target) {
                            case 1:
                                for(Point hit: H.points){
                                    this.client.hitsP1.add(hit);
                                }
                                break;
                            case 2:
                                System.out.println("entre");
                                for(Point hit: H.points){
                                    this.client.hitsP2.add(hit);
                                }
                                break;
                            case 3:
                                for(Point hit: H.points){
                                    this.client.hitsP3.add(hit);
                                }
                                break;
                            case 4:
                                for(Point hit: H.points){
                                    this.client.hitsP4.add(hit);
                                }
                                break;
                            default:
                                break;
                        }
                        break;

                        
                    case "trade":
                        TradeClass TC=(TradeClass) paq;
                        int input = JOptionPane.showConfirmDialog(this.client.window, TC.weapon+" for $"+TC.price+" from Player "+TC.origin+"?");
                        if(input==0){
                            if(null!=TC.weapon)switch (TC.weapon) {
                                case "torpedo":
                                    this.client.torpedos+=1;
                                    this.client.window.getTorpedoAmount().setText(Integer.toString(this.client.torpedos));
                                    break;
                                case "multi":
                                    this.client.multi+=1;
                                    this.client.window.getMultiAmount().setText(Integer.toString(this.client.multi));
                                    break;
                                case "bomb":
                                    this.client.bombs+=1;
                                    this.client.window.getBombAmount().setText(Integer.toString(this.client.bombs));
                                    break;
                                case "trumpedo":
                                    this.client.trumpedos+=1;
                                    this.client.window.getTrumpedoAmount().setText(Integer.toString(this.client.trumpedos));
                                    break;
                                case "ship":
                                    this.client.ships+=1;
                                    this.client.window.getShipAmount().setText(Integer.toString(this.client.ships));
                                    break;
                                case "acero":
                                    this.client.acero+=1;
                                    this.client.window.getAceroAmount().setText(Integer.toString(this.client.acero));
                                    break;
                                default:
                                    break;
                            }
                            this.client.money-=TC.price;
                            this.client.window.getMoney().setText(Integer.toString(this.client.money));
                            TradeAcceptPackage tap=new TradeAcceptPackage(TC.weapon,TC.price,TC.origin);
                            this.client.enviarPaquete(tap);
                        }
                        break;
                        
                    case "tradeAccept":
                        TradeAcceptPackage TA=(TradeAcceptPackage) paq;
                        this.client.money+=TA.price;
                        System.out.println(TA.price);
                        this.client.window.getMoney().setText(Integer.toString(this.client.money));
                        
                        if(null!=TA.weapon)switch (TA.weapon) {
                                case "torpedo":
                                    this.client.torpedos-=1;
                                    this.client.window.getTorpedoAmount().setText(Integer.toString(this.client.torpedos));
                                    break;
                                case "multi":
                                    this.client.multi-=1;
                                    this.client.window.getMultiAmount().setText(Integer.toString(this.client.multi));
                                    break;
                                case "bomb":
                                    this.client.bombs-=1;
                                    this.client.window.getBombAmount().setText(Integer.toString(this.client.bombs));
                                    break;
                                case "trumpedo":
                                    this.client.trumpedos-=1;
                                    this.client.window.getTrumpedoAmount().setText(Integer.toString(this.client.trumpedos));
                                    break;
                                case "ship":
                                    this.client.ships-=1;
                                    this.client.window.getShipAmount().setText(Integer.toString(this.client.ships));
                                    break;
                                case "acero":
                                    this.client.acero-=1;
                                    this.client.window.getAceroAmount().setText(Integer.toString(this.client.acero));
                                    break;
                                default:
                                    break;
                            }
                        break;
                        
                    case "aristas":
                        AristasPackage AP = (AristasPackage) paq;
                        //ArrayList<Vertice> ve=new ArrayList<>();
                        for(Vertice v:AP.vertices){
                            switch (AP.origin) {
                                case 1:
                                    this.client.disconexosP1.add(v);
                                    break;
                                case 2:
                                    this.client.disconexosP2.add(v);
                                    break;
                                case 3:
                                    this.client.disconexosP3.add(v);
                                    break;
                                case 4:
                                    this.client.disconexosP4.add(v);
                                    break;
                                default:
                                    break;
                            }
                            System.out.println("next");
                            System.out.println(v.dato);
                            System.out.println("yes yes");
                            for(Arista a:v.aristas){
                                addDisconexo(a.dato,AP.origin);
                            }
                        }
                        break;
                        
                    case "bitacora":
                        BitacoraTextPackage BTP =(BitacoraTextPackage) paq;
                        this.client.window.setBitacoraText(BTP.text);
                        break;
                        
                    case "money":
                        MoneyPackage MP = (MoneyPackage) paq;
                        this.client.money+= MP.money;
                        this.client.window.getMoney().setText(Integer.toString(this.client.money));
                        break;
                        
                    case "clear":
                        ClearPackage CLP = (ClearPackage) paq;
                        switch(CLP.origin){
                            case 1:
                                this.client.disconexosP1.clear();
                                break;
                            case 2:
                                this.client.disconexosP2.clear();
                                break;
                            case 3:
                                this.client.disconexosP3.clear();
                                break;
                            case 4:
                                this.client.disconexosP4.clear();
                                break;
                            default:
                                break;    
                        }
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addDisconexo(Vertice v,int origin){
        switch (origin) {
            case 1:
                this.client.disconexosP1.add(v);
                break;
            case 2:
                this.client.disconexosP2.add(v);
                break;
            case 3:
                this.client.disconexosP3.add(v);
                break;
            case 4:
                this.client.disconexosP4.add(v);
                break;
            default:
                break;
        }
        if(v.aristas!=null){
            for(Arista ve:v.aristas){
                addDisconexo(ve.dato,origin);
            }
        }
    }
    
    public ArrayList<Point> addPoints(Point p){
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(p.x,p.y));
        
        if(p.x-1>=0&&p.y+1<20)
            points.add(new Point(p.x-1,p.y+1));
        if(p.y+1<20)
            points.add(new Point(p.x,p.y+1));
        if(p.x-1>=0&&p.y+1<20)
            points.add(new Point(p.x-1,p.y+1));
        if(p.x+1<20)
            points.add(new Point(p.x+1,p.y));
        if(p.x+1<20&&p.y-1>=0)
            points.add(new Point(p.x+1,p.y-1));
        if(p.y-1>=0)
            points.add(new Point(p.x,p.y-1));
        if(p.x-1>=0&&p.y-1>=0)
            points.add(new Point(p.x-1,p.y-1));
        if(p.x-1>=0)
            points.add(new Point(p.x-1,p.y));
        if(p.x+2<20)
            points.add(new Point(p.x+2,p.y));
        if(p.x+2<20&&p.y-1>=0)
            points.add(new Point(p.x+2,p.y-1));
        if(p.x+2<20&&p.y-2>=0)
            points.add(new Point(p.x+2,p.y-2));
        if(p.x+1<20&&p.y-2>=0)
            points.add(new Point(p.x+1,p.y-2));
        if(p.y-2>=0)
            points.add(new Point(p.x,p.y-2));
        if(p.x-1>=0&&p.y-2>=0)
            points.add(new Point(p.x-1,p.y-2));
        if(p.x-2>=0&&p.y-2>=0)
            points.add(new Point(p.x-2,p.y-2));
        if(p.x-2>=0&&p.y-1>=0)
            points.add(new Point(p.x-2,p.y-1));
        if(p.x-2>=0)
            points.add(new Point(p.x-2,p.y));
        if(p.x-2>=0&&p.y+1<20)
            points.add(new Point(p.x-2,p.y+1));
        if(p.x-2>=0&&p.y+2<20)
            points.add(new Point(p.x-2,p.y+2));
        if(p.x-1>=0&&p.y+2<20)
            points.add(new Point(p.x-1,p.y));
        if(p.y+2<20)
            points.add(new Point(p.x,p.y+2));
        if(p.x+1<20&&p.y+2<20)
            points.add(new Point(p.x+1,p.y+2));
        if(p.x+2<20&&p.y+2<20)
            points.add(new Point(p.x+2,p.y+2));
        if(p.x+2<20&&p.y+1<20)
            points.add(new Point(p.x+2,p.y+1));
        
        
        return points;
    }
    
}
