/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Packages.AttackReceivedPackage;
import Packages.Package;
import Packages.ChatPackage;
import Packages.IDPackage;
import Packages.TurnMesagePackage;
import Packages.TurnPackage;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author sebasgamboa
 */
public class ServerListener extends Thread {
    
    public Client client;
    
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
                        if(AR.target==this.client.id){
                            for(Point p:AR.attacks){
                                if(this.client.LogicBoard[p.x][p.y]!=0){
                                    this.client.window.board[p.x][p.y].setIcon(
                                            new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/explosion2.png"));
                                }
                             }
                        }
                        this.client.window.setBitacoraText(AR.message);
                        break;
                        
                    case "TurnMessage":
                        TurnMesagePackage T=(TurnMesagePackage) paq;
                        this.client.window.setBitacoraText("Es el turno de Player "+T.turn);
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
