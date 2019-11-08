/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Packages.Package;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebasgamboa
 */
public class Server {
    
    
    //Singleton
    private static Server _instancia = null;
    public static Server instancia() {
        if (_instancia == null) {
            _instancia = new Server();
        }
        return _instancia;
    }
    
    public ServerWindow window = new ServerWindow();
    
    
    private ArrayList<ClientListener> listeners = new ArrayList();
    
    public Server() {
        ConnectionController controller = new ConnectionController();
        controller.start();
        window.setVisible(true);
        window.getTextArea().append("Servidor activo, esperando clientes...\n");
    
    }
    
    public void addClient(ClientListener listener) {
        listeners.add(listener);
        window.getTextArea().append("Cliente Agregado\n");
    }
    
    public void enviarPaquete(Package paq) {
        for (ClientListener listener: listeners) {
            /*if (listener == clienteExcluido) {
                continue;
            }*/
            try {
                ObjectOutputStream out = new ObjectOutputStream(listener.socket.getOutputStream());
                out.writeObject(paq);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
