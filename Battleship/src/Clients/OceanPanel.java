/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Stroke;
import javax.swing.JPanel;

/**
 *
 * @author sebasgamboa
 */
public class OceanPanel extends JPanel {
    
    public OceanPanel() {
        super();
        setOpaque(true);
        setLayout(new GridLayout(20, 20, 0, 0));
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        int TILE_SIZE = 25;
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(41, 128, 185));
        g2.setStroke(new BasicStroke(4));
        
        // for recorriendo el grafo pintando aristas
        
        int x1 = 4;
        int y1 = 5;
        
        int x2 = 7;
        int y2 = 8;
       
        g2.drawLine(
            x1 * TILE_SIZE + (TILE_SIZE  / 2),
            y1 * TILE_SIZE + (TILE_SIZE  / 2),
            x2 * TILE_SIZE + (TILE_SIZE  / 2),
            y2 * TILE_SIZE + (TILE_SIZE  / 2)
        );
    }
}
