/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Game.Arista;
import Game.Grafo;
import Game.Vertice;
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
    
    public Grafo grafo;
    
    public OceanPanel() {
        super();
        setOpaque(true);
        setLayout(new GridLayout(20, 20, 0, 0));
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        int TILE_SIZE = 25;
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(41, 128, 185));
        g2.setStroke(new BasicStroke(4));
        
        // for recorriendo el grafo pintando aristas
        
        for(Vertice vertice:grafo.grafo){
            for(Arista a:vertice.aristas){
                g2.drawLine(
                    a.startX * TILE_SIZE + (TILE_SIZE  / 2),
                    a.startY * TILE_SIZE + (TILE_SIZE  / 2),
                    a.endX * TILE_SIZE + (TILE_SIZE  / 2),
                    a.endY * TILE_SIZE + (TILE_SIZE  / 2)
                );
            }
        }
        
    }
}
