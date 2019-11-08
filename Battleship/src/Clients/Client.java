/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import Packages.Package;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author sebasgamboa
 */
public class Client {
    
    private static Client _instancia = null;
    public static Client instancia() {
        if (_instancia == null) {
            _instancia = new Client();
        }
        return _instancia;
    }
    
    public Socket socket;
    public ClientWindow window = new ClientWindow();
    
    public Client() {
        window.setVisible(true);
    }
    
    public void conectar(String address, int port) throws IOException {
        socket = new Socket(address, port);
        
        ServerListener listener = new ServerListener();
        listener.start();
    }
    
    public void enviarPaquete(Package paq) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(paq);
    }
}
