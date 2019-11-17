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
import Packages.GrafoPackage;
import Packages.LabelsPackage;
import Packages.LogicBoardPackage;
import Packages.ShipPackage;
import Packages.TurnPackage;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        this.EndTurn.setEnabled(false);
        this.Comodin.setEnabled(false);
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
                }
                else if(clientOwner.window.multiState&&clientOwner.multi>0){
                    sendMulti(e);
                }
                else if(clientOwner.window.bombState&&clientOwner.bombs>0){
                    sendBomb(e);
                }
                else if(clientOwner.window.trumpedoState&&clientOwner.trumpedos>0){
                    sendTrumpedo(e);
                }
                else if(clientOwner.window.shipState&&clientOwner.ships>0){
                    try {
                        sendShip(e);
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        
        this.OceanPanel = new OceanPanel(this.clientOwner);
        Rectangle boardRect = this.BoardField.getBounds();
        this.OceanPanel.setBounds(boardRect.x, boardRect.y, boardRect.width, boardRect.height);
        this.BoardField.getParent().add(this.OceanPanel);
        this.BoardField.setBackground(new Color(0, 0, 0, 0));
        this.BoardField.setOpaque(true);
        
        ImageIcon tileIcon = new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/tile.png");
        ImageIcon remIcon = new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/remolino.png");
        
        
 
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                JLabel tileLabel = new JLabel();
                this.OceanPanel.add(tileLabel);
                tileLabel.setBounds(i*25, j*25, 25, 25);
                tileLabel.setTransferHandler(new TransferHandler("icon"));
                if((x1==i&&y1==j)||(x2==i&&y2==j)){
                    tileLabel.setIcon(remIcon);
                    //this.clientOwner.LogicBoard[i][j]=7;
                    //System.out.println("rem added");
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
            currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/ArmoryIzquierda.png");
        }
        else if(label.equals(mine2x1)){
            currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/GoldmineIzquierda.jpg");
        }
        else if(label.equals(temple2x1)){
            currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/TempleIzquierda.png");
        }
        else if(label.equals(mercado2x1)){
            currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/MarketDerecha.jpg");
        }
        else if(label.equals(energysource2x2)){
            currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/energy downL.jpeg");
            currentImage3=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/energy downR.jpeg");
            currentImage4=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/energy upR.jpeg");
        }
        else if(label.equals(mine1x2)){
            currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/MineDown.jpeg");
        }
        else if(label.equals(temple1x2)){
            currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/TempleDown.jpeg");
        }
        else if(label.equals(mercado1x2)){
            currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/MarketUp.jpeg");
        }
        else if(label.equals(armory1x2)){
            currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/Armorydown.jpeg");
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
                    vertice.aristas.add(new Arista(clickedLabel.verticeName.dato,connectionStart.i,
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
            p.add(new Point(i,j));
            p.add(new Point(i+1,j));
            Vertice currentVertice = new Vertice(3,p);
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
            p.add(new Point(i,j));
            p.add(new Point(i+1,j));
            Vertice currentVertice = new Vertice(2,p);
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
            p.add(new Point(i,j));
            p.add(new Point(i+1,j));
            Vertice currentVertice = new Vertice(4,p);
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
            p.add(new Point(i,j));
            p.add(new Point(i+1,j));
            Vertice currentVertice = new Vertice(5,p);
            this.clientOwner.grafo.grafo.add(currentVertice);
            board[i+1][j].verticeName=currentVertice;
            board[i][j].verticeName=currentVertice;
            
            this.clientOwner.money-=2000;
            this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));

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
            p.add(new Point(i,j));
            p.add(new Point(i+1,j));
            p.add(new Point(i+1,j+1));
            p.add(new Point(i,j+1));
            Vertice currentVertice = new Vertice(6,p);
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
            p.add(new Point(i,j));
            Vertice currentVertice = new Vertice(1,p);
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
            p.add(new Point(i,j));
            p.add(new Point(i,j+1));
            Vertice currentVertice = new Vertice(3,p);
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
            p.add(new Point(i,j));
            p.add(new Point(i,j+1));
            Vertice currentVertice = new Vertice(2,p);
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
            p.add(new Point(i,j));
            p.add(new Point(i,j+1));
            Vertice currentVertice = new Vertice(4,p);
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
            p.add(new Point(i,j));
            p.add(new Point(i,j+1));
            Vertice currentVertice = new Vertice(5,p);
            this.clientOwner.grafo.grafo.add(currentVertice);
            board[i][j+1].verticeName=currentVertice;
            board[i][j].verticeName=currentVertice;
            
            this.clientOwner.money-=2000;
            this.clientOwner.window.Money.setText(Integer.toString(this.clientOwner.money));

        }
    }
    
    
    public void sendTorpedo(MouseEvent e){
        BoardLabel clickedLabel = (BoardLabel) e.getSource();
        ArrayList<Point> points=new ArrayList<>();
        Point p=new Point(clickedLabel.i,clickedLabel.j);
        points.add(p);
        AttackPackage paq=new AttackPackage(points,this.enemyTarget,"torpedo",this.clientOwner.id);
        try {
            Client.instancia().enviarPaquete(paq);
            
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
            Client.instancia().enviarPaquete(paq);
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
                Random r=new Random();
                int rand=r.nextInt(4);
                if(rand==0){
                    Point p2=new Point(bl.i+1,bl.j);
                    points.add(p2);
                }
                else if(rand==1){
                    Point p2=new Point(bl.i-1,bl.j);
                    points.add(p2);
                }
                else if(rand==3){
                    Point p2=new Point(bl.i,bl.j+1);
                    points.add(p2);
                }
                else if(rand==4){
                    Point p2=new Point(bl.i,bl.j-1);
                    points.add(p2);
                }
                
            }
            clicks.clear();
            System.out.println("sending bombs");
            AttackPackage paq=new AttackPackage(points,this.enemyTarget,"bomb",this.clientOwner.id);
            try {
                Client.instancia().enviarPaquete(paq);
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
                Client.instancia().enviarPaquete(paq);
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MessageInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MessageInputActionPerformed(evt);
            }
        });

        Messages.setEditable(false);
        Messages.setColumns(20);
        Messages.setRows(5);
        jScrollPane1.setViewportView(Messages);

        Connector.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/connector.png"))); // NOI18N
        Connector.setToolTipText("Cost: 100");

        EnergySource.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/energy display.jpeg"))); // NOI18N
        EnergySource.setToolTipText("Cost: 12000");

        Mine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/GoldmineDisplay.jpg"))); // NOI18N
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

        jLabel11.setText("1x1");

        jLabel12.setText("4x4");

        jLabel13.setText("2x1");

        jLabel14.setText("1x2");

        jLabel15.setText("2x1");

        jLabel16.setText("1x2");

        jLabel17.setText("2x1");

        jLabel18.setText("1x2");

        jLabel19.setText("2x1");

        jLabel20.setText("1x2");

        javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(ContainerLayout);
        ContainerLayout.setHorizontalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContainerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContainerLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(connector1x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContainerLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(energysource2x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContainerLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(2, 2, 2)
                        .addComponent(mercado2x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mercado1x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(ContainerLayout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addGap(3, 3, 3)
                            .addComponent(temple2x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(temple1x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ContainerLayout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addComponent(Mercado, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Armery, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ContainerLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mine2x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mine1x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Mine, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EnergySource, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Connector, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(connector1x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addComponent(EnergySource, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(energysource2x2)
                    .addComponent(jLabel12))
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
                .addContainerGap(70, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BoardFieldLayout = new javax.swing.GroupLayout(BoardField);
        BoardField.setLayout(BoardFieldLayout);
        BoardFieldLayout.setHorizontalGroup(
            BoardFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        BoardFieldLayout.setVerticalGroup(
            BoardFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        ReadyButton.setText("READY");
        ReadyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReadyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EnemyBoardLayout = new javax.swing.GroupLayout(EnemyBoard);
        EnemyBoard.setLayout(EnemyBoardLayout);
        EnemyBoardLayout.setHorizontalGroup(
            EnemyBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        EnemyBoardLayout.setVerticalGroup(
            EnemyBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        jButton1.setText("Player 1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Player 2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Player 3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Player 4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setText("Money:");

        Money.setText("4000");

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

        TorpedoAttack.setText("Torpedo");
        TorpedoAttack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TorpedoAttackActionPerformed(evt);
            }
        });

        jButton5.setText("buy");
        jButton5.setToolTipText("Cost: 500 Kg");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        TorpedoAmount.setText("0");

        Bitacora.setColumns(20);
        Bitacora.setRows(5);
        jScrollPane2.setViewportView(Bitacora);

        jToggleButton1.setText("Multi-shot");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jButton6.setText("buy");
        jButton6.setToolTipText("Cost: 1000 Kg");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        MultiAmount.setText("0");

        jToggleButton2.setText("Bombs");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jButton7.setText("buy");
        jButton7.setToolTipText("Cost: 2000 Kg");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        BombAmount.setText("0");

        jToggleButton3.setText("Trumpedo");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        jButton8.setText("buy");
        jButton8.setToolTipText("Cost: 5000 Kg");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        TrumpedoAmount.setText("0");

        jButton9.setText("Remolinos");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jToggleButton4.setText("Ship");
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });

        jButton10.setText("buy");
        jButton10.setToolTipText("Cost: 2500 Kg");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        ShipAmount.setText("0");

        jLabel2.setText("Acero:");

        AceroAmount.setText("0");

        Comodin.setText("Comodin");
        Comodin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComodinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(BoardField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(ReadyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MessageInput, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ConnectionMode)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Money)))
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(AceroAmount))
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton9)
                                        .addGap(18, 18, 18)
                                        .addComponent(Comodin)))))))
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addComponent(EndTurn)
                        .addGap(36, 36, 36))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(EnemyBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(jButton6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(MultiAmount))
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jToggleButton3)
                                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(panelLayout.createSequentialGroup()
                                                    .addGap(19, 19, 19)
                                                    .addComponent(jButton5)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(TorpedoAmount))
                                                .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(TorpedoAttack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                .addGap(0, 12, Short.MAX_VALUE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelLayout.createSequentialGroup()
                                                    .addComponent(jButton7)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(BombAmount))
                                                .addGroup(panelLayout.createSequentialGroup()
                                                    .addComponent(jButton8)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(TrumpedoAmount)))))
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(jButton10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ShipAmount)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(EnemyBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BoardField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(TorpedoAttack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TorpedoAmount))
                                .addGap(25, 25, 25)
                                .addComponent(jToggleButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MultiAmount))
                                .addGap(18, 18, 18)
                                .addComponent(jToggleButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BombAmount))
                                .addGap(22, 22, 22)
                                .addComponent(jToggleButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TrumpedoAmount))
                                .addGap(18, 18, 18)
                                .addComponent(jToggleButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ShipAmount))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ConnectionMode)
                            .addComponent(jButton9)
                            .addComponent(Comodin))
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton3)
                                    .addComponent(jButton4)
                                    .addComponent(jButton2)
                                    .addComponent(jButton1)
                                    .addComponent(EndTurn))
                                .addGap(83, 83, 83))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(Money)
                                    .addComponent(jLabel2)
                                    .addComponent(AceroAmount))
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ReadyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(MessageInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(72, 72, 72))))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        Container.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panel.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.enemyTarget=1;
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                this.enemyBoard[j][i].setIcon( new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/tile.png"));
            }
        }
        for(Point p:this.clientOwner.hitsP1){
            this.enemyBoard[p.x][p.y].setIcon(new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/explosion2.png"));
            //this.enemyBoard[p.x][p.y].repaint();
            //System.out.println("hit");
        }
        //this.EnemyBoard.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ConnectionModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectionModeActionPerformed
        // TODO add your handling code here:
        this.connectionModeState=!this.connectionModeState;
    }//GEN-LAST:event_ConnectionModeActionPerformed

    private void ConnectionModeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ConnectionModeStateChanged
        // TODO add your handling code here:
        //System.out.println(this.connectionModeState+"h");
    }//GEN-LAST:event_ConnectionModeStateChanged

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

    private void TorpedoAttackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TorpedoAttackActionPerformed
        // TODO add your handling code here:
        this.torpedoState=!this.torpedoState;
    }//GEN-LAST:event_TorpedoAttackActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.enemyTarget=2;
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                this.enemyBoard[j][i].setIcon( new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/tile.png"));
            }
        }
        for(Point p:this.clientOwner.hitsP2){
            this.enemyBoard[p.x][p.y].setIcon(new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/explosion2.png"));
            //this.enemyBoard[p.x][p.y].repaint();
        }
        //this.EnemyBoard.repaint();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.enemyTarget=3;
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                
                this.enemyBoard[j][i].setIcon( new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/tile.png"));
            }
        }
        for(Point p:this.clientOwner.hitsP3){
            this.enemyBoard[p.x][p.y].setIcon(new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/explosion2.png"));
            //this.enemyBoard[p.x][p.y].repaint();
        }
        //this.EnemyBoard.repaint();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.enemyTarget=4;
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                this.enemyBoard[j][i].setIcon( new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/tile.png"));
            }
        }
        for(Point p:this.clientOwner.hitsP4){
            this.enemyBoard[p.x][p.y].setIcon(new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/explosion2.png"));
            //this.enemyBoard[p.x][p.y].repaint();
        }
        //this.EnemyBoard.repaint();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        this.multiState=!this.multiState;
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
        this.bombState=!this.bombState;
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        // TODO add your handling code here:
        this.trumpedoState=!this.trumpedoState;
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        this.setRemolinos();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        // TODO add your handling code here:
        this.shipState=!this.shipState;
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.acero>=500&&this.clientOwner.torpedosE){
            this.clientOwner.torpedos+=1;
            this.TorpedoAmount.setText(Integer.toString(this.clientOwner.torpedos));
            this.clientOwner.acero-=500;
            this.AceroAmount.setText(Integer.toString(this.clientOwner.acero));
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.acero>=1000&&this.clientOwner.multiE){
            this.clientOwner.multi+=1;
            this.MultiAmount.setText(Integer.toString(this.clientOwner.multi));
            this.clientOwner.acero-=1000;
            this.AceroAmount.setText(Integer.toString(this.clientOwner.acero));
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.acero>=2000&&this.clientOwner.bombsE){
            this.clientOwner.bombs+=1;
            this.BombAmount.setText(Integer.toString(this.clientOwner.bombs));
            this.clientOwner.acero-=2000;
            this.AceroAmount.setText(Integer.toString(this.clientOwner.acero));
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.acero>=5000&&this.clientOwner.trumpedosE){
            this.clientOwner.trumpedos+=1;
            this.TrumpedoAmount.setText(Integer.toString(this.clientOwner.trumpedos));
            this.clientOwner.acero-=5000;
            this.AceroAmount.setText(Integer.toString(this.clientOwner.acero));
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.acero>=2500&&this.clientOwner.shipsE){
            this.clientOwner.ships+=1;
            this.ShipAmount.setText(Integer.toString(this.clientOwner.ships));
            this.clientOwner.acero-=2500;
            this.AceroAmount.setText(Integer.toString(this.clientOwner.acero));
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void ComodinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComodinActionPerformed
        // TODO add your handling code here:
        if(this.clientOwner.canComodin){
            this.clientOwner.comodinOn=true;
            this.clientOwner.canComodin=false;
        }
    }//GEN-LAST:event_ComodinActionPerformed

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
    private javax.swing.JButton ReadyButton;
    private javax.swing.JLabel ShipAmount;
    private javax.swing.JLabel Temple;
    private javax.swing.JLabel TorpedoAmount;
    private javax.swing.JToggleButton TorpedoAttack;
    private javax.swing.JLabel TrumpedoAmount;
    private javax.swing.JLabel armory1x2;
    private javax.swing.JLabel armory2x1;
    private javax.swing.JLabel connector1x1;
    private javax.swing.JLabel energysource2x2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
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
    // End of variables declaration//GEN-END:variables
}
