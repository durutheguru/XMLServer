/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlserver.net;

/**
 *
 * @author Duru Dumebi Julian
 */

import java.io.IOException;

import java.net.Socket;
import java.net.ServerSocket;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Observable;

import code.concurrent.ThreadExecutor;
import java.net.InetAddress;
import java.net.UnknownHostException;
import xmlserver.ConsoleLogger;
import xmlserver.Main;

import xmlserver.ui.ServerFrame;
import xmlserver.ui.ClientConnectionFrame;

public class Server extends Observable{
    
    private static Server instance;
    private ServerSocket serverSocket;
    private ServerFrame serverFrame;
    private ArrayList<ClientConnection> connections;
    
    private Server(int port, int queue, ServerFrame sf) throws IOException{
        serverSocket = new ServerSocket(port, queue);
        connections = new ArrayList<>();
        
        if (sf != null){
            serverFrame = sf;
            addObserver(serverFrame);
        }
        addObserver(ConsoleLogger.getInstance());
    }
    
    private void awaitConnections(){
        ThreadExecutor.run(new Runnable(){
            @Override
            public void run(){
                while (!serverSocket.isClosed()){
                    try{
                        Socket socket = serverSocket.accept();
                        logMessage("LOG: Connection received from " + socket.getInetAddress().getHostAddress());
                        
                        newClientConnection(socket);
                    }
                    catch(IOException io){
                        logMessage("ERROR: " + io.getMessage());
                        io.printStackTrace();
                    }
                }
            }
        });
        logMessage("LOG: Server is live and is awaiting connections.");
    }
    
    private void newClientConnection(Socket socket) throws IOException{
        ClientConnection c = new ClientConnection(socket);
        
        if (Main.getMode() == Main.GUI){
            ClientConnectionFrame cf = new ClientConnectionFrame(c);
            c.addObserver(cf);
            serverFrame.addClientConnectionFrame(cf);
        }

        c.addObserver(ConsoleLogger.getInstance());
        connections.add(c);
        ThreadExecutor.run(c);
    }
    
    public void logMessage(String msg){
        setChanged();
        notifyObservers(msg);
    }
    
    public synchronized static Server getInstance(){
        if (instance == null)
            throw new NullPointerException("Server has not been initialized");
        
        return instance;
    }
    
    public synchronized static void runServer(int port, int queue, ServerFrame s) throws IOException{
        if (instance != null)
            return;
        
        instance = new Server(port, queue, s);
        instance.publishServerAddress();
        instance.awaitConnections();
    }
    
    private void publishServerAddress(){
        String ip = "localhost";
        
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
        }
        catch(UnknownHostException uh){}
        
        int port = serverSocket.getLocalPort();

        logMessage(String.format("LOG: Server Address details\nIP: %s\nPort: %d", ip, port));
    }
    
    public static void stopServer() throws IOException{
        instance.serverSocket.close();      
        closeAllConnections();
        instance = null;
    }
    
    private static void closeAllConnections() {
        if (instance == null)
            return;
        
        Iterator<ClientConnection> iterator = instance.connections.iterator();
        while (iterator.hasNext()){
            try{
                iterator.next().closeConnection();
            }
            catch(IOException io){}
        }
    }
    
}
