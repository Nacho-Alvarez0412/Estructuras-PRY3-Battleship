/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Game.Arista;
import Game.ArmoryThread;
import Game.MineThread;
import Game.Ship;
import Game.TempleThread;
import Game.Vertice;
import Packages.AttackPackage;
import Packages.ChatPackage;
import Packages.ComodinPackage;
import Packages.GrafoPackage;
import Packages.LabelsPackage;
import Packages.LogicBoardPackage;
import Packages.ShipPackage;
import Packages.TradeClass;
import Packages.TurnPackage;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;

/**
 *
 * @author sebasgamboa
 */
public class ClientWindow extends javax.swing.JFrame {

    /**
     * Creates new form ClientWindow
     */
    
    public Client clientOwner;
    public BoardLabel[][] board=new BoardLabel[20][20];
    public BoardLabel[][] enemyBoard=new BoardLabel[20][20];
    public ImageIcon currentImage=null;
    public ImageIcon currentImage2=null;
    public ImageIcon currentImage3=null;
    public ImageIcon currentImage4=null;
    public boolean connectionModeState=false;
    public boolean torpedoState=false;
    public boolean multiState=false;
    public boolean bombState=false;
    public boolean trumpedoState=false;
    public boolean shipState=false;
    public int enemyTarget;

    
    public Random r=new Random();
    public int x1=r.nextInt(20);
    public int y1=r.nextInt(20);
    public int x2=r.nextInt(20);
    public int y2=r.nextInt(20);
        
        
    public ClientWindow(Client c) {
        initComponents();
        this.Sell.setEnabled(false);
        this.EndTurn.setEnabled(false);
        this.Comodin.setEnabled(false);
        this.trade.setEnabled(false);
        this.clientOwner=c;
        
        MouseListener toolsMouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            
            @Override
            public void mouseReleased(MouseEvent e){ }

            @Override
            public void mouseEntered(MouseEvent e){  }

            @Override
            public void mouseExited(MouseEvent e){  }   
            
            @Override
            public void mousePressed(MouseEvent e) {
                toolPressed(e);
            }
        };

        MouseListener boardMouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            
            @Override
            public void mouseReleased(MouseEvent e){ }

            @Override
            public void mouseEntered(MouseEvent e){  }

            @Override
            public void mouseExited(MouseEvent e){  }   
            
            @Override
            public void mousePressed(MouseEvent e) {
                
                if (connectionModeState) {
                    addConnector(e);
                } else {
                    addNewUnit(e);
                }
                
            }
        };
        
        MouseListener enemyMouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            
            @Override
            public void mouseReleased(MouseEvent e){ }

            @Override
            public void mouseEntered(MouseEvent e){  }

            @Override
            public void mouseExited(MouseEvent e){  }   
            
            @Override
            public void mousePressed(MouseEvent e) {
                if(clientOwner.window.torpedoState&&clientOwner.torpedos>0){
                    sendTorpedo(e);
                    TurnPackage paq=new TurnPackage(clientOwner.id);
                    try {
                        attackMusic(getClass().getResource("/Music/AirImpact.wav"));
                        clientOwner.enviarPaquete(paq);
                        EndTurn.setEnabled(false);
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                        Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(clientOwner.window.multiState&&clientOwner.multi>0){
                    sendMulti(e);
                    TurnPackage paq=new TurnPackage(clientOwner.id);
                    try {
                        attackMusic(getClass().getResource("/Music/AirImpact.wav"));
                        clientOwner.enviarPaquete(paq);
                        EndTurn.setEnabled(false);
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                        Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(clientOwner.window.bombState&&clientOwner.bombs>0){
                    sendBomb(e);
                    TurnPackage paq=new TurnPackage(clientOwner.id);
                    try {
                        attackMusic(getClass().getResource("/Music/AirImpact.wav"));
                        clientOwner.enviarPaquete(paq);
                        EndTurn.setEnabled(false);
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                        Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(clientOwner.window.trumpedoState&&clientOwner.trumpedos>0){
                    sendTrumpedo(e);
                    TurnPackage paq=new TurnPackage(clientOwner.id);
                    try {
                        attackMusic(getClass().getResource("/Music/AirImpact.wav"));
                        clientOwner.enviarPaquete(paq);
                        EndTurn.setEnabled(false);
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                        Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if(clientOwner.window.shipState&&clientOwner.ships>0){
                    try {
                        sendShip(e);
                        TurnPackage paq=new TurnPackage(clientOwner.id);
                        clientOwner.enviarPaquete(paq);
                        EndTurn.setEnabled(false);
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        
        this.OceanPanel = new OceanPanel(this.clientOwner);
        Rectangle boardRect = this.BoardField.getBounds();
        this.OceanPanel.setBounds(boardRect.x, boardRect.y, 500,500);
        this.BoardField.getParent().add(this.OceanPanel);
        this.BoardField.setBackground(new Color(0, 0, 0, 0));
        this.BoardField.setOpaque(true);
        
        ImageIcon tileIcon = new ImageIcon(getClass().getResource("/Images/tile.png"));
        ImageIcon remIcon = new ImageIcon(getClass().getResource("/Images/remolino.png"));
        
        
 
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                JLabel tileLabel = new JLabel();
                this.OceanPanel.add(tileLabel);
                tileLabel.setBounds(i*25, j*25, 25, 25);
                tileLabel.setTransferHandler(new TransferHandler("icon"));
                if((x1==i&&y1==j)||(x2==i&&y2==j)){
                    tileLabel.setIcon(remIcon);
                }else{
                    tileLabel.setIcon(tileIcon);
                }
                
                BoardLabel boardLabel = new BoardLabel(i, j);
                this.BoardField.add(boardLabel);
                boardLabel.setBounds(i*25, j*25, 25, 25);
                boardLabel.addMouseListener(boardMouseListener);
                boardLabel.setTransferHandler(new TransferHandler("icon"));
                board[i][j] = boardLabel;
                
                BoardLabel enemyLabel = new BoardLabel(i, j);
                this.EnemyBoard.add(enemyLabel);
                enemyLabel.setBounds(i*25, j*25, 25, 25);
                enemyLabel.setIcon(tileIcon);
                enemyLabel.addMouseListener(enemyMouseListener);
                enemyBoard[i][j]=enemyLabel;
            }
        }
        pack();
        
        this.armory2x1.addMouseListener(toolsMouseListener);
        this.connector1x1.addMouseListener(toolsMouseListener);
        this.energysource2x2.addMouseListener(toolsMouseListener);
        this.mercado2x1.addMouseListener(toolsMouseListener);
        this.mine2x1.addMouseListener(toolsMouseListener);
        this.temple2x1.addMouseListener(toolsMouseListener);
        
        this.armory1x2.addMouseListener(toolsMouseListener);
        this.mercado1x2.addMouseListener(toolsMouseListener);
        this.mine1x2.addMouseListener(toolsMouseListener);
        this.temple1x2.addMouseListener(toolsMouseListener);
    }
        
        
    public void setRemolinos(){
        this.clientOwner.LogicBoard[this.y1][this.x1]=7;
        this.clientOwner.LogicBoard[this.y2][this.x2]=7;
    }
    
    private void toolPressed(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        currentImage = (ImageIcon) label.getIcon();

        if(label.equals(armory2x1)){
            currentImage2=new ImageIcon(getClass().getResource("/Images/ArmoryIzquierda.png"));
        }
        else if(label.equals(mine2x1)){
            currentImage2=new ImageIcon(getClass().getResource("/Images/GoldmineIzquierda.jpg"));
        }
        else if(label.equals(temple2x1)){
            currentImage2=new ImageIcon(getClass().getResource("/Images/TempleIzquierda.png"));
        }
        else if(label.equals(mercado2x1)){
            currentImage2=new ImageIcon(getClass().getResource("/Images/MarketDerecha.jpg"));
        }
        else if(label.equals(energysource2x2)){
            currentImage2=new ImageIcon(getClass().getResource("/Images/energy downL.jpeg"));
            currentImage3=new ImageIcon(getClass().getResource("/Images/energy downR.jpeg"));
            currentImage4=new ImageIcon(getClass().getResource("/Images/energy upR.jpeg"));
        }
        else if(label.equals(mine1x2)){
            currentImage2=new ImageIcon(getClass().getResource("/Images/MineDown.jpeg"));
        }
        else if(label.equals(temple1x2)){
            currentImage2=new ImageIcon(getClass().getResource("/Images/TempleDown.jpeg"));
        }
        else if(label.equals(mercado1x2)){
            currentImage2=new ImageIcon(getClass().getResource("/Images/MarketUp.jpeg"));
        }
        else if(label.equals(armory1x2)){
            currentImage2=new ImageIcon(getClass().getResource("/Images/Armorydown.jpeg"));
        }
        
        connectionStart = null;
    }
    
    
    private BoardLabel connectionStart = null;
    
    private void addConnector(MouseEvent e) {
        
        BoardLabel clickedLabel = (BoardLabel) e.getSource();
        
        if (clickedLabel.getIcon()==null) return;
        
        if (connectionStart == null) {
            connectionStart = clickedLabel;
        } else if (clickedLabel.getIcon().equals(connector1x1.getIcon())
                || connectionStart.getIcon().equals(connector1x1.getIcon())) {
            
            // Hacer conexion
            for(Vertice vertice: this.clientOwner.grafo.grafo){
                System.out.println(vertice.dato);
                if(vertice==(connectionStart.verticeName)){
                    vertice.aristas.add(new Arista(clickedLabel.verticeName,connectionStart.i,
                    connectionStart.j,clickedLabel.i,clickedLabel.j));
                    this.BoardField.getParent().repaint();
                    break;
                }
            }
            
     
            connectionStart = null;
        }
    }
    
    public boolean firstEnergy=true;
    
    private void addNewUnit(MouseEvent e) {
        if(currentImage == null) return;

        BoardLabel targetLabel = (BoardLabel) e.getSource();
        

        int i = targetLabel.i;
        int j = targetLabel.j;
        
        if(i+1==20) return;

        if(currentImage.equals(armory2x1.getIcon())&&this.clientOwner.money>=1500){
            String Weapon=JOptionPane.showInputDialog("Enter a weapon to fabricate:");
            System.out.println(Weapon);
            board[i+1][j].setIcon(currentImage2);
            targetLabel.setIcon(currentImage);
            
            
            ArmoryThread aThread=new ArmoryThread(this.clientOwner,Weapon);
            this.clientOwner.armory.add(aThread);
            board[i+1][j].armory=this.clientOwner.armory.size()-1;
            board[i][j].armory=this.clientOwner.armory.size()-1;
            aThread.start();
            
            this.clientOwner.LogicBoard[i][j]=3;
            this.clientOwner.LogicBoard[i+1][j]=3;
            
            ArrayList<Point> p=new ArrayList<>();
            ArrayList<ImageIcon> I=new ArrayList<>();
            p.add(new Point(i,j));
            p.add(new Point(i+1,j));
            I.add(currentImage);
            I.add(currentImage2);
            Vertice currentVertice = new Vertice(3,p,I);
            this.clientOwner.grafo.grafo.add(currentVertice);
            board[i+1][j].verticeName=currentVertice;
            board[i][j].verticeName=currentVertice;
            
            this.clientOwner.money-=1500;
            this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));
        }
        else if(currentImage.equals(mine2x1.getIcon())&&this.clientOwner.money>=1000){
            board[i+1][j].setIcon(currentImage2);
            targetLabel.setIcon(currentImage);
            
            
            MineThread aThread=new MineThread(this.clientOwner);
            this.clientOwner.mine.add(aThread);
            board[i+1][j].mine=this.clientOwner.mine.size()-1;
            board[i][j].mine=this.clientOwner.mine.size()-1;
            aThread.start();
            
            this.clientOwner.LogicBoard[i][j]=2;
            this.clientOwner.LogicBoard[i+1][j]=2;
            
            ArrayList<Point> p=new ArrayList<>();
            ArrayList<ImageIcon> I=new ArrayList<>();
            p.add(new Point(i,j));
            p.add(new Point(i+1,j));
            I.add(currentImage);
            I.add(currentImage2);
            Vertice currentVertice = new Vertice(2,p,I);
            this.clientOwner.grafo.grafo.add(currentVertice);
            board[i+1][j].verticeName=currentVertice;
            board[i][j].verticeName=currentVertice;
            
            this.clientOwner.money-=1000;
            this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));
        }
        else if(currentImage.equals(temple2x1.getIcon())&&this.clientOwner.money>=2500){
            board[i+1][j].setIcon(currentImage2);
            targetLabel.setIcon(currentImage);
            
            
            TempleThread TThread=new TempleThread(this.clientOwner);
            this.clientOwner.temple.add(TThread);
            board[i+1][j].temple=this.clientOwner.temple.size()-1;
            board[i][j].temple=this.clientOwner.temple.size()-1;
            TThread.start();
            
            this.clientOwner.LogicBoard[i][j]=4;
            this.clientOwner.LogicBoard[i+1][j]=4;
            
            ArrayList<Point> p=new ArrayList<>();
            ArrayList<ImageIcon> I=new ArrayList<>();
            p.add(new Point(i,j));
            p.add(new Point(i+1,j));
            I.add(currentImage);
            I.add(currentImage2);
            Vertice currentVertice = new Vertice(4,p,I);
            this.clientOwner.grafo.grafo.add(currentVertice);
            board[i+1][j].verticeName=currentVertice;
            board[i][j].verticeName=currentVertice;
            
            this.clientOwner.money-=1500;
            this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));

        }
        else if(currentImage.equals(mercado2x1.getIcon())&&this.clientOwner.money>=2000){
            board[i+1][j].setIcon(currentImage2);
            targetLabel.setIcon(currentImage);
            
            
            this.clientOwner.LogicBoard[i][j]=5;
            this.clientOwner.LogicBoard[i+1][j]=5;
            
            ArrayList<Point> p=new ArrayList<>();
            ArrayList<ImageIcon> I=new ArrayList<>();
            p.add(new Point(i,j));
            p.add(new Point(i+1,j));
            I.add(currentImage);
            I.add(currentImage2);
            Vertice currentVertice = new Vertice(5,p,I);
            this.clientOwner.grafo.grafo.add(currentVertice);
            board[i+1][j].verticeName=currentVertice;
            board[i][j].verticeName=currentVertice;
            
            this.clientOwner.money-=2000;
            this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));
            this.trade.setEnabled(true);
            this.Sell.setEnabled(true);
        }
        else if(currentImage.equals(energysource2x2.getIcon())&&(this.clientOwner.money>=12000||this.firstEnergy)){
            
            board[i][j+1].setIcon(currentImage2);
            board[i+1][j+1].setIcon(currentImage3);
            board[i+1][j].setIcon(currentImage4);
            targetLabel.setIcon(currentImage);
            
            
            this.clientOwner.LogicBoard[i][j]=6;
            this.clientOwner.LogicBoard[i][j+1]=6;
            this.clientOwner.LogicBoard[i+1][j+1]=6;
            this.clientOwner.LogicBoard[i+1][j]=6;
            
            ArrayList<Point> p=new ArrayList<>();
            ArrayList<ImageIcon> I=new ArrayList<>();
            p.add(new Point(i,j));
            p.add(new Point(i+1,j));
            p.add(new Point(i+1,j+1));
            p.add(new Point(i,j+1));
            I.add(currentImage);
            I.add(currentImage4);
            I.add(currentImage3);
            I.add(currentImage2);
            Vertice currentVertice = new Vertice(6,p,I);
            this.clientOwner.grafo.grafo.add(currentVertice);
            board[i][j+1].verticeName=currentVertice;
            board[i+1][j+1].verticeName=currentVertice;
            board[i+1][j].verticeName=currentVertice;
            board[i][j].verticeName=currentVertice;
            
            if(!this.firstEnergy){
                this.clientOwner.money-=12000;
                this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));

            }
            this.firstEnergy=false;
        }
        else if(currentImage.equals(connector1x1.getIcon())&&this.clientOwner.money>=100){
            
            this.clientOwner.LogicBoard[i][j]=1;
            targetLabel.setIcon(currentImage);
            
            ArrayList<Point> p=new ArrayList<>();
            ArrayList<ImageIcon> I=new ArrayList<>();
            p.add(new Point(i,j));
            I.add(currentImage);
            Vertice currentVertice = new Vertice(1,p,I);
            this.clientOwner.grafo.grafo.add(currentVertice);
            targetLabel.verticeName=currentVertice;
            
            this.clientOwner.money-=100;
            this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));

        }else if(currentImage.equals(armory1x2.getIcon())&&this.clientOwner.money>=1500){
            String Weapon=JOptionPane.showInputDialog(this, "Enter a weapon to fabricate:");
            System.out.println(Weapon);
            board[i][j+1].setIcon(currentImage2);
            targetLabel.setIcon(currentImage);
            
            
            ArmoryThread aThread=new ArmoryThread(this.clientOwner,Weapon);
            this.clientOwner.armory.add(aThread);
            board[i][j+1].armory=this.clientOwner.armory.size()-1;
            board[i][j].armory=this.clientOwner.armory.size()-1;
            aThread.start();
            
            this.clientOwner.LogicBoard[i][j]=3;
            this.clientOwner.LogicBoard[i][j+1]=3;
            
            ArrayList<Point> p=new ArrayList<>();
            ArrayList<ImageIcon> I=new ArrayList<>();
            I.add(currentImage);
            I.add(currentImage2);
            p.add(new Point(i,j));
            p.add(new Point(i,j+1));
            Vertice currentVertice = new Vertice(3,p,I);
            this.clientOwner.grafo.grafo.add(currentVertice);
            board[i][j+1].verticeName=currentVertice;
            board[i][j].verticeName=currentVertice;
            
            this.clientOwner.money-=1500;
            this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));
        }
        else if(currentImage.equals(mine1x2.getIcon())&&this.clientOwner.money>=1000){
            board[i][j+1].setIcon(currentImage2);
            targetLabel.setIcon(currentImage);
            
            
            MineThread aThread=new MineThread(this.clientOwner);
            this.clientOwner.mine.add(aThread);
            board[i][j+1].mine=this.clientOwner.mine.size()-1;
            board[i][j].mine=this.clientOwner.mine.size()-1;
            aThread.start();
            
            this.clientOwner.LogicBoard[i][j]=2;
            this.clientOwner.LogicBoard[i][j+1]=2;
            
            ArrayList<Point> p=new ArrayList<>();
            ArrayList<ImageIcon> I=new ArrayList<>();
            I.add(currentImage);
            I.add(currentImage2);
            p.add(new Point(i,j));
            p.add(new Point(i,j+1));
            Vertice currentVertice = new Vertice(2,p,I);
            this.clientOwner.grafo.grafo.add(currentVertice);
            board[i][j+1].verticeName=currentVertice;
            board[i][j].verticeName=currentVertice;
            
            this.clientOwner.money-=1000;
            this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));
        }
        else if(currentImage.equals(temple1x2.getIcon())&&this.clientOwner.money>=2500){
            board[i][j+1].setIcon(currentImage2);
            targetLabel.setIcon(currentImage);
            
            
            TempleThread TThread=new TempleThread(this.clientOwner);
            this.clientOwner.temple.add(TThread);
            board[i][j+1].temple=this.clientOwner.temple.size()-1;
            board[i][j].temple=this.clientOwner.temple.size()-1;
            TThread.start();
            
            this.clientOwner.LogicBoard[i][j]=4;
            this.clientOwner.LogicBoard[i][j+1]=4;
            
            ArrayList<Point> p=new ArrayList<>();
            ArrayList<ImageIcon> I=new ArrayList<>();
            I.add(currentImage);
            I.add(currentImage2);

            p.add(new Point(i,j));
            p.add(new Point(i,j+1));
            Vertice currentVertice = new Vertice(4,p,I);
            this.clientOwner.grafo.grafo.add(currentVertice);
            board[i][j+1].verticeName=currentVertice;
            board[i][j].verticeName=currentVertice;
            
            this.clientOwner.money-=1500;
            this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));

        }
        else if(currentImage.equals(mercado1x2.getIcon())&&this.clientOwner.money>=2000){
            
            board[i][j+1].setIcon(currentImage2);
            targetLabel.setIcon(currentImage);
            
            
            this.clientOwner.LogicBoard[i][j]=5;
            this.clientOwner.LogicBoard[i][j+1]=5;
            
            ArrayList<Point> p=new ArrayList<>();
            ArrayList<ImageIcon> I=new ArrayList<>();
            I.add(currentImage);
            I.add(currentImage2);
            p.add(new Point(i,j));
            p.add(new Point(i,j+1));
            Vertice currentVertice = new Vertice(5,p,I);
            this.clientOwner.grafo.grafo.add(currentVertice);
            board[i][j+1].verticeName=currentVertice;
            board[i][j].verticeName=currentVertice;
            
            this.clientOwner.money-=2000;
            this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));
            this.trade.setEnabled(true);
            this.Sell.setEnabled(true);

        }
    }
    
    
    public void sendTorpedo(MouseEvent e){
        BoardLabel clickedLabel = (BoardLabel) e.getSource();
        ArrayList<Point> points=new ArrayList<>();
        Point p=new Point(clickedLabel.i,clickedLabel.j);
        points.add(p);
        AttackPackage paq=new AttackPackage(points,this.enemyTarget,"torpedo",this.clientOwner.id);
        try {
            this.clientOwner.enviarPaquete(paq);
            this.clientOwner.torpedos-=1;
            this.clientOwner.window.TorpedoAmount.setText(Integer.toString(this.clientOwner.torpedos));
            
        } catch (IOException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMulti(MouseEvent e){
        BoardLabel clickedLabel = (BoardLabel) e.getSource();
        ArrayList<Point> points=new ArrayList<>();
        Point p=new Point(clickedLabel.i,clickedLabel.j);
        points.add(p);
        AttackPackage paq=new AttackPackage(points,this.enemyTarget,"multi",this.clientOwner.id);
        try {
            this.clientOwner.enviarPaquete(paq);
            this.clientOwner.multi-=1;
            this.clientOwner.window.MultiAmount.setText(Integer.toString(this.clientOwner.multi));
        } catch (IOException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public ArrayList<BoardLabel> clicks = new ArrayList<>();
    
    
    public void sendBomb(MouseEvent e){
        BoardLabel clickedLabel = (BoardLabel) e.getSource();
        clicks.add(clickedLabel);
        if(clicks.size()==3){
            ArrayList<Point> points=new ArrayList<>();
            for(BoardLabel bl:clicks){
                Point p=new Point(bl.i,bl.j);
                points.add(p);
                Random randomGenerator=new Random();
                int rand=randomGenerator.nextInt(4);
                switch (rand) {
                    case 0:
                        if(bl.i+1<20){
                            Point p2=new Point(bl.i+1,bl.j);
                            points.add(p2);
                        }   break;
                    case 1:
                        if(bl.i-1>=0){
                            Point p2=new Point(bl.i-1,bl.j);
                            points.add(p2);
                        }   break;
                    case 3:
                        if(bl.j+1<20){
                            Point p2=new Point(bl.i,bl.j+1);
                            points.add(p2);}
                        break;
                    case 4:
                        if(bl.j-1>=0){
                            Point p2=new Point(bl.i,bl.j-1);
                            points.add(p2);
                        }   break;
                    default:
                        break;
                }
            }
            clicks.clear();
            System.out.println("sending bombs");
            AttackPackage paq=new AttackPackage(points,this.enemyTarget,"bomb",this.clientOwner.id);
            try {
                this.clientOwner.enviarPaquete(paq);
                this.clientOwner.bombs-=1;
            this.clientOwner.window.BombAmount.setText(Integer.toString(this.clientOwner.bombs));
            } catch (IOException ex) {
                Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<BoardLabel> clicksT = new ArrayList<>();
    
    public void sendTrumpedo(MouseEvent e){
        BoardLabel clickedLabel = (BoardLabel) e.getSource();
        clicksT.add(clickedLabel);
        if(clicksT.size()==10){
            ArrayList<Point> points=new ArrayList<>();
            for(BoardLabel bl:clicksT){
                Point p=new Point(bl.i,bl.j);
                points.add(p);
            }
            clicksT.clear();
            System.out.println("sending Trumpedos");
            AttackPackage paq=new AttackPackage(points,this.enemyTarget,"Trumpedo",this.clientOwner.id);
            try {
                this.clientOwner.enviarPaquete(paq);
                this.clientOwner.trumpedos-=1;
                this.clientOwner.window.TrumpedoAmount.setText(Integer.toString(this.clientOwner.trumpedos));
            } catch (IOException ex) {
                Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean shipTimer=true;
    
    public void sendShip(MouseEvent e) throws IOException, InterruptedException{
        BoardLabel clickedLabel = (BoardLabel) e.getSource();
        Ship s=new Ship(this.shipTimer);
        //s.start();
        sleep(3000);
        ShipPackage paq=new ShipPackage(this.enemyTarget,this.clientOwner.id,new Point(clickedLabel.i,clickedLabel.j),null,s);
        this.clientOwner.enviarPaquete(paq);
        this.clientOwner.ships-=1;
        this.clientOwner.window.ShipAmount.setText(Integer.toString(this.clientOwner.ships));
    }
    
    public void setAcero(){
        this.AceroAmount.setText(Integer.toString(this.clientOwner.acero));
    }

    public JButton getComodin() {
        return Comodin;
    }
    
    
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        MessageInput = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Messages = new javax.swing.JTextArea();
        Container = new javax.swing.JPanel();
        Connector = new javax.swing.JLabel();
        EnergySource = new javax.swing.JLabel();
        Mine = new javax.swing.JLabel();
        Armery = new javax.swing.JLabel();
        Temple = new javax.swing.JLabel();
        Mercado = new javax.swing.JLabel();
        connector1x1 = new javax.swing.JLabel();
        energysource2x2 = new javax.swing.JLabel();
        armory2x1 = new javax.swing.JLabel();
        mine1x2 = new javax.swing.JLabel();
        armory1x2 = new javax.swing.JLabel();
        temple2x1 = new javax.swing.JLabel();
        mine2x1 = new javax.swing.JLabel();
        temple1x2 = new javax.swing.JLabel();
        mercado2x1 = new javax.swing.JLabel();
        mercado1x2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        BoardField = new javax.swing.JPanel();
        ReadyButton = new javax.swing.JButton();
        EnemyBoard = new javax.swing.JPanel();
        P1 = new javax.swing.JButton();
        P2 = new javax.swing.JButton();
        P3 = new javax.swing.JButton();
        P4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Money = new javax.swing.JLabel();
        ConnectionMode = new javax.swing.JToggleButton();
        EndTurn = new javax.swing.JButton();
        TorpedoAttack = new javax.swing.JToggleButton();
        jButton5 = new javax.swing.JButton();
        TorpedoAmount = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Bitacora = new javax.swing.JTextArea();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton6 = new javax.swing.JButton();
        MultiAmount = new javax.swing.JLabel();
        jToggleButton2 = new javax.swing.JToggleButton();
        jButton7 = new javax.swing.JButton();
        BombAmount = new javax.swing.JLabel();
        jToggleButton3 = new javax.swing.JToggleButton();
        jButton8 = new javax.swing.JButton();
        TrumpedoAmount = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jButton10 = new javax.swing.JButton();
        ShipAmount = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        AceroAmount = new javax.swing.JLabel();
        Comodin = new javax.swing.JButton();
        trade = new javax.swing.JButton();
        Sell = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setBackground(new java.awt.Color(83, 83, 83));
        panel.setPreferredSize(new java.awt.Dimension(1400, 962));

        MessageInput.setBackground(new java.awt.Color(0, 35, 0));
        MessageInput.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        MessageInput.setForeground(new java.awt.Color(255, 255, 255));
        MessageInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        MessageInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MessageInputActionPerformed(evt);
            }
        });

        Messages.setEditable(false);
        Messages.setBackground(new java.awt.Color(0, 35, 0));
        Messages.setColumns(20);
        Messages.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        Messages.setForeground(new java.awt.Color(255, 255, 255));
        Messages.setRows(5);
        Messages.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        jScrollPane1.setViewportView(Messages);

        Container.setBackground(new java.awt.Color(0, 35, 0));
        Container.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));

        Connector.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/connector.png"))); // NOI18N
        Connector.setToolTipText("Cost: 100");

        EnergySource.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/energy display.png"))); // NOI18N
        EnergySource.setToolTipText("Cost: 12000");

        Mine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/GoldmineDisplay.png"))); // NOI18N
        Mine.setToolTipText("Cost: 1000");

        Armery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ArmoryDisplay.png"))); // NOI18N
        Armery.setToolTipText("Cost: 1500");

        Temple.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TempleDisplay.png"))); // NOI18N
        Temple.setToolTipText("Cost: 2500");

        Mercado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/MarketDisplay.jpg"))); // NOI18N
        Mercado.setToolTipText("Cost: 2000");

        connector1x1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/connector-25.jpg"))); // NOI18N

        energysource2x2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/energy upL.jpeg"))); // NOI18N

        armory2x1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ArmoryDerecha.png"))); // NOI18N

        mine1x2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/MineUp.jpeg"))); // NOI18N

        armory1x2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Armoryup.jpeg"))); // NOI18N

        temple2x1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TempleDerecha.png"))); // NOI18N

        mine2x1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Goldmine Derecha.jpg"))); // NOI18N

        temple1x2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TempleUp.jpeg"))); // NOI18N

        mercado2x1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/MarketIzquierda.jpg"))); // NOI18N

        mercado1x2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/MarketDown.jpeg"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("1x1");

        jLabel12.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("4x4");

        jLabel13.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("2x1");

        jLabel14.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("1x2");

        jLabel15.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("2x1");

        jLabel16.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("1x2");

        jLabel17.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("2x1");

        jLabel18.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("1x2");

        jLabel19.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("2x1");

        jLabel20.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("1x2");

        javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(ContainerLayout);
        ContainerLayout.setHorizontalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContainerLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(2, 2, 2)
                        .addComponent(mercado2x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mercado1x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(ContainerLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(3, 3, 3)
                                .addComponent(temple2x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(temple1x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ContainerLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(Mercado, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ContainerLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mine2x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mine1x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ContainerLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(Armery, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContainerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContainerLayout.createSequentialGroup()
                                .addComponent(EnergySource, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContainerLayout.createSequentialGroup()
                                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ContainerLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(connector1x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ContainerLayout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(energysource2x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(Connector, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Mine, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40))))))
            .addGroup(ContainerLayout.createSequentialGroup()
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(armory2x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(armory1x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(Temple, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ContainerLayout.setVerticalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(Connector, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(EnergySource, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(connector1x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(95, 95, 95)
                        .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(energysource2x2)
                            .addComponent(jLabel12))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Mine, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(mine2x1)
                    .addComponent(jLabel14)
                    .addComponent(mine1x2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Armery, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(armory1x2)
                    .addComponent(armory2x1)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Temple, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(temple1x2)
                    .addComponent(temple2x1)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Mercado, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mercado1x2)
                    .addComponent(mercado2x1)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        BoardField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        BoardField.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout BoardFieldLayout = new javax.swing.GroupLayout(BoardField);
        BoardField.setLayout(BoardFieldLayout);
        BoardFieldLayout.setHorizontalGroup(
            BoardFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );
        BoardFieldLayout.setVerticalGroup(
            BoardFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );

        ReadyButton.setText("READY");
        ReadyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReadyButtonActionPerformed(evt);
            }
        });

        EnemyBoard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        EnemyBoard.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout EnemyBoardLayout = new javax.swing.GroupLayout(EnemyBoard);
        EnemyBoard.setLayout(EnemyBoardLayout);
        EnemyBoardLayout.setHorizontalGroup(
            EnemyBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );
        EnemyBoardLayout.setVerticalGroup(
            EnemyBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        P1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        P1.setForeground(new java.awt.Color(255, 255, 255));
        P1.setText("Player 1");
        P1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                P1ActionPerformed(evt);
            }
        });

        P2.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        P2.setForeground(new java.awt.Color(255, 255, 255));
        P2.setText("Player 2");
        P2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                P2ActionPerformed(evt);
            }
        });

        P3.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        P3.setForeground(new java.awt.Color(255, 255, 255));
        P3.setText("Player 3");
        P3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                P3ActionPerformed(evt);
            }
        });

        P4.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        P4.setForeground(new java.awt.Color(255, 255, 255));
        P4.setText("Player 4");
        P4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                P4ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Money:");

        Money.setFont(new java.awt.Font("Arial Black", 1, 22)); // NOI18N
        Money.setForeground(new java.awt.Color(255, 255, 255));
        Money.setText("4000");

        ConnectionMode.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        ConnectionMode.setText("Connections");
        ConnectionMode.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ConnectionModeStateChanged(evt);
            }
        });
        ConnectionMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectionModeActionPerformed(evt);
            }
        });

        EndTurn.setText("End Turn");
        EndTurn.setDefaultCapable(false);
        EndTurn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EndTurnActionPerformed(evt);
            }
        });

        TorpedoAttack.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        TorpedoAttack.setForeground(new java.awt.Color(255, 255, 255));
        TorpedoAttack.setText("Torpedo");
        TorpedoAttack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TorpedoAttackActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("buy");
        jButton5.setToolTipText("Cost: 500 Kg");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        TorpedoAmount.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        TorpedoAmount.setForeground(new java.awt.Color(255, 255, 255));
        TorpedoAmount.setText("0");

        Bitacora.setBackground(new java.awt.Color(0, 35, 0));
        Bitacora.setColumns(20);
        Bitacora.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        Bitacora.setForeground(new java.awt.Color(255, 255, 255));
        Bitacora.setRows(5);
        Bitacora.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        jScrollPane2.setViewportView(Bitacora);

        jToggleButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jToggleButton1.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton1.setText("Multi-shot");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("buy");
        jButton6.setToolTipText("Cost: 1000 Kg");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        MultiAmount.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        MultiAmount.setForeground(new java.awt.Color(255, 255, 255));
        MultiAmount.setText("0");

        jToggleButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jToggleButton2.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton2.setText("Bombs");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("buy");
        jButton7.setToolTipText("Cost: 2000 Kg");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        BombAmount.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        BombAmount.setForeground(new java.awt.Color(255, 255, 255));
        BombAmount.setText("0");

        jToggleButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jToggleButton3.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton3.setText("Trumpedo");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("buy");
        jButton8.setToolTipText("Cost: 5000 Kg");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        TrumpedoAmount.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        TrumpedoAmount.setForeground(new java.awt.Color(255, 255, 255));
        TrumpedoAmount.setText("0");

        jButton9.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jButton9.setText("Whirpools");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jToggleButton4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jToggleButton4.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton4.setText("Ship");
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("buy");
        jButton10.setToolTipText("Cost: 2500 Kg");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        ShipAmount.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        ShipAmount.setForeground(new java.awt.Color(255, 255, 255));
        ShipAmount.setText("0");

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Steel:");

        AceroAmount.setFont(new java.awt.Font("Arial Black", 1, 22)); // NOI18N
        AceroAmount.setForeground(new java.awt.Color(255, 255, 255));
        AceroAmount.setText("0");

        Comodin.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        Comodin.setText("Wildcard");
        Comodin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComodinActionPerformed(evt);
            }
        });

        trade.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        trade.setText("Trade");
        trade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tradeActionPerformed(evt);
            }
        });

        Sell.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        Sell.setText("Sell");
        Sell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SellActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Control Room");

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Armory");

        jLabel5.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Edifications");

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Enemies");

        jLabel7.setFont(new java.awt.Font("Arial Black", 1, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Your Radar");

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Quantity");

        jLabel9.setFont(new java.awt.Font("Arial Black", 1, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Type");

        jLabel10.setFont(new java.awt.Font("Arial Black", 1, 22)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Enemy Radar");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ReadyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MessageInput, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(121, 121, 121)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(EndTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(227, 227, 227)
                                .addComponent(jLabel7)
                                .addGap(367, 367, 367)
                                .addComponent(jLabel10))))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelLayout.createSequentialGroup()
                                                .addGap(24, 24, 24)
                                                .addComponent(BoardField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panelLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(trade, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(38, 38, 38))
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(ConnectionMode)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Comodin, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Sell, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(EnemyBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelLayout.createSequentialGroup()
                                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(panelLayout.createSequentialGroup()
                                                        .addGap(6, 6, 6)
                                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGap(18, 18, 18)
                                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(BombAmount)
                                                    .addComponent(MultiAmount)))
                                            .addComponent(jLabel4)
                                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panelLayout.createSequentialGroup()
                                                .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(TrumpedoAmount))
                                            .addGroup(panelLayout.createSequentialGroup()
                                                .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(ShipAmount))
                                            .addGroup(panelLayout.createSequentialGroup()
                                                .addComponent(TorpedoAttack, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(TorpedoAmount))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                        .addComponent(P1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(P2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(P3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(P4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(562, 562, 562))
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(504, 504, 504)
                                        .addComponent(jLabel9)
                                        .addGap(31, 31, 31)
                                        .addComponent(jLabel8))))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Money)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(AceroAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(BoardField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ConnectionMode)
                                    .addComponent(Comodin)
                                    .addComponent(Sell))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(trade, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(TorpedoAttack)
                                            .addComponent(TorpedoAmount))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jToggleButton1)
                                            .addComponent(MultiAmount))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jToggleButton2)
                                            .addComponent(BombAmount))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jToggleButton3)
                                            .addComponent(TrumpedoAmount))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jToggleButton4)
                                            .addComponent(ShipAmount))
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(132, 132, 132))
                                    .addComponent(EnemyBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(P1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(P2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(P3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(P4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addComponent(EndTurn, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addComponent(Money)
                            .addComponent(jLabel2)
                            .addComponent(AceroAmount))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ReadyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MessageInput, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Container.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 1432, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panel.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SellActionPerformed
        // TODO add your handling code here:
        int price=0;
        String weapon=JOptionPane.showInputDialog("What do you want to sell?");
        if(null!=weapon)switch (weapon) {
            case "torpedo":
            price=100;
            break;
            case "multi":
            price=250;
            break;
            case "bomb":
            price=300;
            break;
            case "trumpedo":
            price=500;
            break;
            case "ship":
            price=400;
            break;
            case "acero":
            String amount=JOptionPane.showInputDialog("How much steel do you want to sell?");
            int am=Integer.parseInt(amount);
            price=am;
            break;
            default:
            break;
        }

        int input = JOptionPane.showConfirmDialog(this, weapon+" for $"+price+"?");
        if(input==0){
            System.out.println("si");
            if(null!=weapon)switch (weapon) {
                case "torpedo":
                this.clientOwner.torpedos-=1;
                this.TorpedoAmount.setText(Integer.toString(this.clientOwner.torpedos));
                this.clientOwner.money+=price;
                this.getMoney().setText(Integer.toString(this.clientOwner.money));
                break;
                case "multi":
                this.clientOwner.multi-=1;
                this.MultiAmount.setText(Integer.toString(this.clientOwner.multi));
                this.clientOwner.money+=price;
                this.getMoney().setText(Integer.toString(this.clientOwner.money));
                break;
                case "bomb":
                this.clientOwner.bombs-=1;
                this.BombAmount.setText(Integer.toString(this.clientOwner.bombs));
                this.clientOwner.money+=price;
                this.getMoney().setText(Integer.toString(this.clientOwner.money));
                break;
                case "trumpedo":
                this.clientOwner.trumpedos-=1;
                this.TrumpedoAmount.setText(Integer.toString(this.clientOwner.trumpedos));
                this.clientOwner.money+=price;
                this.getMoney().setText(Integer.toString(this.clientOwner.money));
                break;
                case "ship":
                this.clientOwner.ships-=1;
                this.ShipAmount.setText(Integer.toString(this.clientOwner.ships));
                this.clientOwner.money+=price;
                this.getMoney().setText(Integer.toString(this.clientOwner.money));
                break;
                case "acero":
                this.clientOwner.acero-=1;
                this.getAceroAmount().setText(Integer.toString(this.clientOwner.acero));
                this.clientOwner.money+=price;
                this.getMoney().setText(Integer.toString(this.clientOwner.money));
                break;
                default:
                break;
            }
        }

    }//GEN-LAST:event_SellActionPerformed

    private void tradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tradeActionPerformed
        // TODO add your handling code here:
        String[] options = {"Player 4", "Player 3", "Player 2", "Player 1"};
        int x = JOptionPane.showOptionDialog(null, "Pick the player for the negotiation",
            "Click a button",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[3]);
        switch (x) {
            case 3:
            x=1;
            break;
            case 2:
            x=2;
            break;
            case 1:
            x=3;
            break;
            case 0:
            x=4;
            break;
            default:
            break;
        }
        System.out.println(x);
        String weapon=JOptionPane.showInputDialog("What do you want to sell?");
        String price=JOptionPane.showInputDialog("How much do you want for this?");
        int input = JOptionPane.showConfirmDialog(this, weapon+" for $"+price+" to Player "+x+"?");
        if(input==0){
            System.out.println("si");
            TradeClass t=new TradeClass(weapon,Integer.parseInt(price),x,this.clientOwner.id);
            try {
                this.clientOwner.enviarPaquete(t);
            } catch (IOException ex) {
                Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_tradeActionPerformed

    private void ComodinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComodinActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.canComodin){
            this.clientOwner.comodinOn=true;
            this.clientOwner.canComodin=false;
            this.clientOwner.comodinNum=5;
            ComodinPackage paq=new ComodinPackage(this.clientOwner.id);
            try {
                this.clientOwner.enviarPaquete(paq);
            } catch (IOException ex) {
                Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ComodinActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.acero>=2500&&this.clientOwner.shipsE){
            this.clientOwner.ships+=1;
            this.ShipAmount.setText(Integer.toString(this.clientOwner.ships));
            this.clientOwner.acero-=2500;
            this.AceroAmount.setText(Integer.toString(this.clientOwner.acero));
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        // TODO add your handling code here:
        this.shipState=!this.shipState;
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        this.setRemolinos();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.acero>=5000&&this.clientOwner.trumpedosE){
            this.clientOwner.trumpedos+=1;
            this.TrumpedoAmount.setText(Integer.toString(this.clientOwner.trumpedos));
            this.clientOwner.acero-=5000;
            this.AceroAmount.setText(Integer.toString(this.clientOwner.acero));
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        // TODO add your handling code here:
        this.trumpedoState=!this.trumpedoState;
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.acero>=2000&&this.clientOwner.bombsE){
            this.clientOwner.bombs+=1;
            this.BombAmount.setText(Integer.toString(this.clientOwner.bombs));
            this.clientOwner.acero-=2000;
            this.AceroAmount.setText(Integer.toString(this.clientOwner.acero));
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
        this.bombState=!this.bombState;
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.acero>=1000&&this.clientOwner.multiE){
            this.clientOwner.multi+=1;
            this.MultiAmount.setText(Integer.toString(this.clientOwner.multi));
            this.clientOwner.acero-=1000;
            this.AceroAmount.setText(Integer.toString(this.clientOwner.acero));
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        this.multiState=!this.multiState;
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.acero>=500&&this.clientOwner.torpedosE){
            this.clientOwner.torpedos+=1;
            this.TorpedoAmount.setText(Integer.toString(this.clientOwner.torpedos));
            this.clientOwner.acero-=500;
            this.AceroAmount.setText(Integer.toString(this.clientOwner.acero));
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void TorpedoAttackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TorpedoAttackActionPerformed
        // TODO add your handling code here:
        this.torpedoState=!this.torpedoState;
    }//GEN-LAST:event_TorpedoAttackActionPerformed

    private void EndTurnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EndTurnActionPerformed
        // TODO add your handling code here:
        TurnPackage paq=new TurnPackage(this.clientOwner.id);
        try {
            Client.instancia().enviarPaquete(paq);
        } catch (IOException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.EndTurn.setEnabled(false);
    }//GEN-LAST:event_EndTurnActionPerformed

    private void ConnectionModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectionModeActionPerformed
        // TODO add your handling code here:
        this.connectionModeState=!this.connectionModeState;
    }//GEN-LAST:event_ConnectionModeActionPerformed

    private void ConnectionModeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ConnectionModeStateChanged
        // TODO add your handling code here:
        //System.out.println(this.connectionModeState+"h");
    }//GEN-LAST:event_ConnectionModeStateChanged

    private void P4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_P4ActionPerformed
        // TODO add your handling code here:
        this.enemyTarget=4;
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                this.enemyBoard[j][i].setIcon( new ImageIcon(getClass().getResource("/Images/tile.png")));
            }
        }

        for(Vertice v:this.clientOwner.disconexosP4){
            switch (v.dato) {
                case 1:
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(0));
                }
                break;
                case 2:
                int cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 3:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 4:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 5:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 6:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                }
                break;
                default:
                break;
            }

        }

        for(Point p:this.clientOwner.hitsP4){
            this.enemyBoard[p.x][p.y].setIcon(new ImageIcon(getClass().getResource("/Images/explosion2.png")));
            //this.enemyBoard[p.x][p.y].repaint();
        }
        //this.EnemyBoard.repaint();
    }//GEN-LAST:event_P4ActionPerformed

    private void P3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_P3ActionPerformed
        // TODO add your handling code here:
        this.enemyTarget=3;
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){

                this.enemyBoard[j][i].setIcon( new ImageIcon(getClass().getResource("/Images/tile.png")));
            }
        }

        for(Vertice v:this.clientOwner.disconexosP3){
            switch (v.dato) {
                case 1:
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(0));
                }
                break;
                case 2:
                int cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 3:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 4:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 5:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 6:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                }
                break;
                default:
                break;
            }

        }

        for(Point p:this.clientOwner.hitsP3){
            this.enemyBoard[p.x][p.y].setIcon(new ImageIcon(getClass().getResource("/Images/explosion2.png")));
        }

    }//GEN-LAST:event_P3ActionPerformed

    private void P2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_P2ActionPerformed
        // TODO add your handling code here:
        this.enemyTarget=2;
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                this.enemyBoard[j][i].setIcon( new ImageIcon(getClass().getResource("/Images/tile.png")));
            }
        }

        for(Vertice v:this.clientOwner.disconexosP2){
            switch (v.dato) {
                case 1:
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(0));
                }
                break;
                case 2:
                int cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 3:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 4:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 5:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 6:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                }
                break;
                default:
                break;
            }

        }

        for(Point p:this.clientOwner.hitsP2){
            this.enemyBoard[p.x][p.y].setIcon(new ImageIcon(getClass().getResource("/Images/explosion2.png")));
        }

    }//GEN-LAST:event_P2ActionPerformed

    private void P1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_P1ActionPerformed
        // TODO add your handling code here:
        this.enemyTarget=1;
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                this.enemyBoard[j][i].setIcon( new ImageIcon(getClass().getResource("/Images/tile.png")));
            }
        }

        for(Vertice v:this.clientOwner.disconexosP1){
            switch (v.dato) {
                case 1:
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(0));
                }
                break;
                case 2:
                int cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 3:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 4:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 5:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                    cont++;
                }
                break;
                case 6:
                cont=0;
                for(Point p2:v.point){
                    this.clientOwner.window.enemyBoard[p2.x][p2.y].setIcon(v.images.get(cont));
                }
                break;
                default:
                break;
            }

        }

        for(Point p:this.clientOwner.hitsP1){
            this.enemyBoard[p.x][p.y].setIcon(new ImageIcon(getClass().getResource("/Images/explosion2.png")));
        }

    }//GEN-LAST:event_P1ActionPerformed

    private void ReadyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReadyButtonActionPerformed
        // TODO add your handling code here:
        GrafoPackage paq=new GrafoPackage(this.clientOwner.grafo,this.clientOwner.id);
        LogicBoardPackage paq2=new LogicBoardPackage(this.clientOwner.LogicBoard,this.clientOwner.id);
        LabelsPackage paq3=new LabelsPackage(this.clientOwner.id,this.board);
        try {
            Client.instancia().enviarPaquete(paq);
            Client.instancia().enviarPaquete(paq2);
            Client.instancia().enviarPaquete(paq3);
        } catch (IOException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_ReadyButtonActionPerformed

    private void MessageInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MessageInputActionPerformed
        // TODO add your handling code here:
        String msj = this.MessageInput.getText();
        this.MessageInput.setText("");

        ChatPackage chat = new ChatPackage("Player "+this.clientOwner.id+": "+msj);
        try {
            Client.instancia().enviarPaquete(chat);
        } catch (IOException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_MessageInputActionPerformed

    
    public void resetComodin(){
        this.clientOwner.comodinOn=false;
        this.Comodin.setEnabled(false);
        ComodinPackage paq=new ComodinPackage(this.clientOwner.id);
        try {
            this.clientOwner.enviarPaquete(paq);
        } catch (IOException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public JButton getEndTurn() {
        return EndTurn;
    }
    
    public void setBitacoraText(String s){
        this.Bitacora.append(s+"\n");
    }
   
    
    public void addMessage(String mes){
        this.Messages.append(mes+"\n");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ClientWindow().setVisible(true);
            }
        });
    }

    public JLabel getAceroAmount() {
        return AceroAmount;
    }

    public JLabel getBombAmount() {
        return BombAmount;
    }

    public JLabel getMultiAmount() {
        return MultiAmount;
    }

    public JLabel getShipAmount() {
        return ShipAmount;
    }

    public JLabel getTorpedoAmount() {
        return TorpedoAmount;
    }

    public JLabel getTrumpedoAmount() {
        return TrumpedoAmount;
    }

    public JLabel getMoney() {
        return Money;
    }

    public JButton getP1() {
        return P1;
    }

    public JButton getP2() {
        return P2;
    }

    public JButton getP3() {
        return P3;
    }

    public JButton getP4() {
        return P4;
    }
    
    
    public void attackMusic(URL path) throws IOException, UnsupportedAudioFileException, LineUnavailableException{
             
        AudioInputStream audioIn = null;  
        audioIn = AudioSystem.getAudioInputStream(path);

        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();                                                                                                     
    }
    
    
    private javax.swing.JPanel OceanPanel;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AceroAmount;
    private javax.swing.JLabel Armery;
    private javax.swing.JTextArea Bitacora;
    private javax.swing.JPanel BoardField;
    private javax.swing.JLabel BombAmount;
    private javax.swing.JButton Comodin;
    private javax.swing.JToggleButton ConnectionMode;
    private javax.swing.JLabel Connector;
    private javax.swing.JPanel Container;
    private javax.swing.JButton EndTurn;
    private javax.swing.JPanel EnemyBoard;
    private javax.swing.JLabel EnergySource;
    private javax.swing.JLabel Mercado;
    private javax.swing.JTextField MessageInput;
    private javax.swing.JTextArea Messages;
    private javax.swing.JLabel Mine;
    private javax.swing.JLabel Money;
    private javax.swing.JLabel MultiAmount;
    private javax.swing.JButton P1;
    private javax.swing.JButton P2;
    private javax.swing.JButton P3;
    private javax.swing.JButton P4;
    private javax.swing.JButton ReadyButton;
    private javax.swing.JButton Sell;
    private javax.swing.JLabel ShipAmount;
    private javax.swing.JLabel Temple;
    private javax.swing.JLabel TorpedoAmount;
    private javax.swing.JToggleButton TorpedoAttack;
    private javax.swing.JLabel TrumpedoAmount;
    private javax.swing.JLabel armory1x2;
    private javax.swing.JLabel armory2x1;
    private javax.swing.JLabel connector1x1;
    private javax.swing.JLabel energysource2x2;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JLabel mercado1x2;
    private javax.swing.JLabel mercado2x1;
    private javax.swing.JLabel mine1x2;
    private javax.swing.JLabel mine2x1;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel temple1x2;
    private javax.swing.JLabel temple2x1;
    private javax.swing.JButton trade;
    // End of variables declaration//GEN-END:variables
}
