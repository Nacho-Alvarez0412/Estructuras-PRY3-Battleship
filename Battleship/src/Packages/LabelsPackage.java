/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packages;

import Clients.BoardLabel;

/**
 *
 * @author sebasgamboa
 */
public class LabelsPackage extends Package{
    public int id;
    public BoardLabel[][] board;
    
    public LabelsPackage(int i,BoardLabel[][] b){
        super("labels");
        this.id=i;
        this.board=b;
    }
}
