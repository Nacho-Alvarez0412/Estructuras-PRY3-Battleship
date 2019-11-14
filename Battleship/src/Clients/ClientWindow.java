/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Game.Arista;
import Game.Vertice;
import Packages.AttackPackage;
import Packages.ChatPackage;
import Packages.GrafoPackage;
import Packages.LogicBoardPackage;
import Packages.TurnPackage;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    public ImageIcon currentImage=null;
    public ImageIcon currentImage2=null;
    public ImageIcon currentImage3=null;
    public ImageIcon currentImage4=null;
    public boolean connectionModeState=false;
    public boolean torpedoState=false;
    public int enemyTarget;

    public ClientWindow(Client c) {
        initComponents();
        this.EndTurn.setEnabled(false);
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
                if(clientOwner.window.torpedoState){
                    sendTorpedo(e);
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
        
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                JLabel tileLabel = new JLabel();
                this.OceanPanel.add(tileLabel);
                tileLabel.setBounds(i*25, j*25, 25, 25);
                tileLabel.setTransferHandler(new TransferHandler("icon"));
                tileLabel.setIcon(tileIcon);
                
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
            }
        }
        pack();
        
        this.armory2x1.addMouseListener(toolsMouseListener);
        this.connector1x1.addMouseListener(toolsMouseListener);
        this.energysource2x2.addMouseListener(toolsMouseListener);
        this.mercado2x1.addMouseListener(toolsMouseListener);
        this.mine2x1.addMouseListener(toolsMouseListener);
        this.temple2x1.addMouseListener(toolsMouseListener);
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
                if(vertice.dato==(connectionStart.verticeName)){
                    vertice.aristas.add(new Arista(clickedLabel.verticeName,connectionStart.i,
                    connectionStart.j,clickedLabel.i,clickedLabel.j));
                    this.BoardField.getParent().repaint();
                    break;
                }
            }
            
     
            connectionStart = null;
        }
    }
    
    private void addNewUnit(MouseEvent e) {
        if(currentImage == null) return;

        BoardLabel targetLabel = (BoardLabel) e.getSource();
        targetLabel.setIcon(currentImage);
        

        int i = targetLabel.i;
        int j = targetLabel.j;

        if(currentImage.equals(armory2x1.getIcon())){
            board[i+1][j].setIcon(currentImage2);
            board[i+1][j].verticeName=3;
            board[i][j].verticeName=3;
            
            this.clientOwner.LogicBoard[i][j]=3;
            this.clientOwner.LogicBoard[i+1][j]=3;
            
            Vertice currentVertice = new Vertice(3);
            this.clientOwner.grafo.grafo.add(currentVertice);
        }
        else if(currentImage.equals(mine2x1.getIcon())){
            board[i+1][j].setIcon(currentImage2);
            board[i+1][j].verticeName=2;
            board[i][j].verticeName=2;
            
            this.clientOwner.LogicBoard[i][j]=2;
            this.clientOwner.LogicBoard[i+1][j]=2;
            
            Vertice currentVertice = new Vertice(2);
            this.clientOwner.grafo.grafo.add(currentVertice);
        }
        else if(currentImage.equals(temple2x1.getIcon())){
            board[i+1][j].setIcon(currentImage2);
            board[i+1][j].verticeName=4;
            board[i][j].verticeName=4;
            
            this.clientOwner.LogicBoard[i][j]=4;
            this.clientOwner.LogicBoard[i+1][j]=4;
            
            Vertice currentVertice = new Vertice(4);
            this.clientOwner.grafo.grafo.add(currentVertice);
        }
        else if(currentImage.equals(mercado2x1.getIcon())){
            board[i+1][j].setIcon(currentImage2);
            board[i+1][j].verticeName=5;
            board[i][j].verticeName=5;
            
            this.clientOwner.LogicBoard[i][j]=5;
            this.clientOwner.LogicBoard[i+1][j]=5;
            
            Vertice currentVertice = new Vertice(5);
            this.clientOwner.grafo.grafo.add(currentVertice);
        }
        else if(currentImage.equals(energysource2x2.getIcon())){
            
            board[i][j+1].setIcon(currentImage2);
            board[i+1][j+1].setIcon(currentImage3);
            board[i+1][j].setIcon(currentImage4);
            board[i][j+1].verticeName=6;
            board[i+1][j+1].verticeName=6;
            board[i+1][j].verticeName=6;
            board[i][j].verticeName=6;
            
            this.clientOwner.LogicBoard[i][j]=6;
            this.clientOwner.LogicBoard[i][j+1]=6;
            this.clientOwner.LogicBoard[i+1][j+1]=6;
            this.clientOwner.LogicBoard[i+1][j]=6;
            
            Vertice currentVertice = new Vertice(6);
            this.clientOwner.grafo.grafo.add(currentVertice);
        }
        else if(currentImage.equals(connector1x1.getIcon())){
            
            this.clientOwner.LogicBoard[i][j]=1;
            Vertice currentVertice = new Vertice(1);
            this.clientOwner.grafo.grafo.add(currentVertice);
            targetLabel.verticeName=1;
        }
    }
    
    
    public void sendTorpedo(MouseEvent e){
        BoardLabel clickedLabel = (BoardLabel) e.getSource();
        ArrayList<Point> points=new ArrayList<>();
        Point p=new Point(clickedLabel.i,clickedLabel.j);
        points.add(p);
        AttackPackage paq=new AttackPackage(points,this.enemyTarget);
        try {
            Client.instancia().enviarPaquete(paq);
        } catch (IOException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        EnergySource.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/energy display.jpeg"))); // NOI18N

        Mine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/GoldmineDisplay.jpg"))); // NOI18N

        Armery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ArmoryDisplay.png"))); // NOI18N

        Temple.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TempleDisplay.png"))); // NOI18N

        Mercado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/MarketDisplay.jpg"))); // NOI18N

        connector1x1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/connector-25.jpg"))); // NOI18N

        energysource2x2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/energy upL.jpeg"))); // NOI18N

        armory2x1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ArmoryDerecha.png"))); // NOI18N

        mine1x2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Mercado 1.jpeg"))); // NOI18N

        armory1x2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Mercado 1.jpeg"))); // NOI18N

        temple2x1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TempleDerecha.png"))); // NOI18N

        mine2x1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Goldmine Derecha.jpg"))); // NOI18N

        temple1x2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Mercado 1.jpeg"))); // NOI18N

        mercado2x1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/MarketIzquierda.jpg"))); // NOI18N

        mercado1x2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Mercado 1.jpeg"))); // NOI18N

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

        TorpedoAmount.setText("0");

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
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BoardField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(ReadyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Money)
                                        .addGap(32, 32, 32)))
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1)
                                    .addComponent(MessageInput))))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(EndTurn)
                                .addGap(36, 36, 36))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(EnemyBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TorpedoAttack)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jButton5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TorpedoAmount)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(ConnectionMode)
                        .addContainerGap())))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                    .addComponent(TorpedoAmount))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ConnectionMode)
                        .addGap(44, 44, 44)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton3)
                                        .addComponent(jButton4)
                                        .addComponent(jButton2)
                                        .addComponent(jButton1)
                                        .addComponent(EndTurn))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MessageInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Money)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ReadyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        Container.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 34, Short.MAX_VALUE))
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
        try {
            Client.instancia().enviarPaquete(paq);
            Client.instancia().enviarPaquete(paq2);
        } catch (IOException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_ReadyButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.enemyTarget=1;
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
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.enemyTarget=3;
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.enemyTarget=4;
    }//GEN-LAST:event_jButton4ActionPerformed

    public JButton getEndTurn() {
        return EndTurn;
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
    private javax.swing.JLabel Armery;
    private javax.swing.JPanel BoardField;
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
    private javax.swing.JButton ReadyButton;
    private javax.swing.JLabel Temple;
    private javax.swing.JLabel TorpedoAmount;
    private javax.swing.JToggleButton TorpedoAttack;
    private javax.swing.JLabel armory1x2;
    private javax.swing.JLabel armory2x1;
    private javax.swing.JLabel connector1x1;
    private javax.swing.JLabel energysource2x2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mercado1x2;
    private javax.swing.JLabel mercado2x1;
    private javax.swing.JLabel mine1x2;
    private javax.swing.JLabel mine2x1;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel temple1x2;
    private javax.swing.JLabel temple2x1;
    // End of variables declaration//GEN-END:variables
}
