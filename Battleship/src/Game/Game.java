/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 *
 * @author sebasgamboa
 */
public class Game {
    
    public int turn=1;
    
    public int[][] LogicBoardPlayer1=new int[20][20];
    public int[][] LogicBoardPlayer2=new int[20][20];
    public int[][] LogicBoardPlayer3=new int[20][20];
    public int[][] LogicBoardPlayer4=new int[20][20];
    
    public Grafo grafoP1;
    public Grafo grafoP2;
    public Grafo grafoP3;
    public Grafo grafoP4;
}
