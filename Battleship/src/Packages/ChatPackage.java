/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packages;

/**
 *
 * @author sebasgamboa
 */
public class ChatPackage extends Package {
    
    public String mensaje;
    
    public ChatPackage(String mensaje) {
        super("chat");
        this.mensaje = mensaje;
    }
    
}
