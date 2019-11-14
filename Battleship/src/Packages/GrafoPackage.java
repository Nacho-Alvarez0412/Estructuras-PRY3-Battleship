/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packages;

import Game.Grafo;

/**
 *
 * @author sebasgamboa
 */
public class GrafoPackage extends Package{
    public Grafo grafo;
    public int id;
    
    public GrafoPackage(Grafo grafo,int id) {
        super("Grafo");
        this.id = id;
        this.grafo=grafo;
    }
}
