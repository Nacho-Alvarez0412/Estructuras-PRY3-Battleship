/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packages;

import java.io.Serializable;

/**
 *
 * @author sebasgamboa
 */
public abstract class Package implements Serializable {
    
    public String tipo;
    
    public Package(String tipo) {
        this.tipo = tipo;
    }
    
}
