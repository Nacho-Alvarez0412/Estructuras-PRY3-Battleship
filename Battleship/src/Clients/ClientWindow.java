/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Packages.ChatPackage;
import java.awt.datatransfer.Clipboard;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
    public JLabel[][] board=new JLabel[20][20];
    public ImageIcon currentImage=null;
    public ImageIcon currentImage2=null;
    public ImageIcon currentImage3=null;
    public ImageIcon currentImage4=null;

    public ClientWindow(Client c) {
        initComponents();
        this.clientOwner=c;
        //Clipboard clip=new Clipboard();
        MouseListener mouse;
        mouse = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //JComponent jc = (JComponent)e.getSource();
                //TransferHandler th = jc.getTransferHandler();
                //th.exportAsDrag(jc, e, TransferHandler.COPY);
                //th.exportToClipboard(jc, clip, TransferHandler.COPY);
                
                
                if(currentImage==null){
                    JLabel im = (JLabel)e.getSource();
                    if(im.equals(armory2x1)){
                        currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/ArmoryIzquierda.png");
                    }
                    else if(im.equals(mine2x1)){
                        currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/GoldmineIzquierda.jpg");
                    }
                    else if(im.equals(temple2x1)){
                        currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/TempleIzquierda.png");
                    }
                    else if(im.equals(mercado2x1)){
                        currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/MarketDerecha.jpg");
                    }
                    else if(im.equals(energysource2x2)){
                        currentImage2=new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/GoldmineIzquierda.jpg");
                    }
                    currentImage=(ImageIcon) im.getIcon();
                }
                else{
                    JLabel im = (JLabel)e.getSource();
                    im.setIcon(currentImage);
                    System.out.println(e.getXOnScreen());
                    System.out.println(e.getYOnScreen());
                    int posx=e.getXOnScreen()-170;
                    int posy=e.getYOnScreen()-52;
                    if(currentImage.equals(armory2x1.getIcon())){
                        board[(posx/25)+1][posy/25].setIcon(currentImage2);
                    }
                    else if(currentImage.equals(mine2x1.getIcon())){
                        board[(posx/25)+1][posy/25].setIcon(currentImage2);
                    }
                    else if(currentImage.equals(temple2x1.getIcon())){
                        board[(posx/25)+1][posy/25].setIcon(currentImage2);
                    }
                    else if(currentImage.equals(mercado2x1.getIcon())){
                        board[(posx/25)+1][posy/25].setIcon(currentImage2);
                    }
                    else if(currentImage.equals(energysource2x2.getIcon())){
                        board[(posx/25)+1][posy/25].setIcon(currentImage2);
                    }
                    
                    currentImage=null;
                    currentImage2=null;
                    currentImage3=null;
                    currentImage4=null;
                }
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }   
        };
        
        
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                board[i][j]=new JLabel();
                this.BoardField.add(board[i][j]);
                board[i][j].setBounds(i*25, j*25, 25, 25);
                ImageIcon icon = new ImageIcon("/Users/sebasgamboa/Documents/GitHub/Progra Estructuras/Battle Ship/Battleship/Battleship/src/Images/tile.png");
                board[i][j].setIcon(icon);
                board[i][j].addMouseListener(mouse);
                board[i][j].setTransferHandler(new TransferHandler("icon"));
            }
        }
        
        
        this.armory2x1.addMouseListener(mouse);
        this.connector1x1.addMouseListener(mouse);
        this.energysource2x2.addMouseListener(mouse);
        this.mercado2x1.addMouseListener(mouse);
        this.mine2x1.addMouseListener(mouse);
        this.temple2x1.addMouseListener(mouse);
        
        /*this.armory2x1.setTransferHandler(new TransferHandler("icon"));
        this.connector1x1.setTransferHandler(new TransferHandler("icon"));
        this.energysource2x2.setTransferHandler(new TransferHandler("icon"));
        this.mercado2x1.setTransferHandler(new TransferHandler("icon"));
        this.mine2x1.setTransferHandler(new TransferHandler("icon"));
        this.temple2x1.setTransferHandler(new TransferHandler("icon"));*/
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

        connector1x1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/connector1.png"))); // NOI18N

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

        jButton3.setText("Player 3");

        jButton4.setText("Player 4");

        jLabel1.setText("Money:");

        Money.setText("0");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(37, 37, 37)
                        .addComponent(EnemyBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jButton1)
                        .addGap(47, 47, 47)
                        .addComponent(jButton2)
                        .addGap(33, 33, 33)
                        .addComponent(jButton3)
                        .addGap(44, 44, 44)
                        .addComponent(jButton4)))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(EnemyBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BoardField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(77, 77, 77)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton3)
                                        .addComponent(jButton4)
                                        .addComponent(jButton2)
                                        .addComponent(jButton1))
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
                .addGap(0, 0, Short.MAX_VALUE))
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
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                //if(this.board[i][j]){
                    
                //}
            }
        }
    }//GEN-LAST:event_ReadyButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    
   
    
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Armery;
    private javax.swing.JPanel BoardField;
    private javax.swing.JLabel Connector;
    private javax.swing.JPanel Container;
    private javax.swing.JPanel EnemyBoard;
    private javax.swing.JLabel EnergySource;
    private javax.swing.JLabel Mercado;
    private javax.swing.JTextField MessageInput;
    private javax.swing.JTextArea Messages;
    private javax.swing.JLabel Mine;
    private javax.swing.JLabel Money;
    private javax.swing.JButton ReadyButton;
    private javax.swing.JLabel Temple;
    private javax.swing.JLabel armory1x2;
    private javax.swing.JLabel armory2x1;
    private javax.swing.JLabel connector1x1;
    private javax.swing.JLabel energysource2x2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
