/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

/**
 *
 * @author sebasgamboa
 *///
import java.io.*; 
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientMain 
{
    public static void main(String args[]) {             
        try {
            Client.instancia().conectar("127.0.0.1", 5000);
        } catch (IOException ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 

