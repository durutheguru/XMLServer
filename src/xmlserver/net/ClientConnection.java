/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlserver.net;

/**
 *
 * @author Duru Dumebi Julian
 */

import java.net.Socket;

import java.sql.SQLException;

import java.util.Observable;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.w3c.dom.Document;
import org.w3c.dom.DOMException;

import xmlserver.db.XMLProcessor;

public class ClientConnection extends Observable implements Runnable{
    
    private static int ID_COUNTER = 0;
    
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String address;
    private int id;
    
    public ClientConnection(Socket socket) throws IOException{
        this.socket = socket;
        address = socket.getInetAddress().getHostAddress();
        id = ID_COUNTER++;
        initializeStreams();
    }
    
    private void initializeStreams() throws IOException{
        out = new ObjectOutputStream(socket.getOutputStream());        
        out.flush();
        
        in = new ObjectInputStream (socket.getInputStream());
    }
    
    public String getAddress(){
        return address;
    }
    
    public int getID(){
        return id;
    }
    
    @Override
    public void run(){
        logMessage("LOG: Awaiting requests from client host...");
        while (!socket.isClosed()){
            String db, sql = null;
            
            try{
                Document request = (Document) in.readObject();
                
                if (XMLProcessor.isSpecialRequest(request)){
                    logMessage("LOG: Special request received from client. " + XMLProcessor.getSpecialRequestType(request));
                    Document response = XMLProcessor.getSpecialResponse(request);
                    sendResponseDocument(response);
                }
                else{        
                    sql = XMLProcessor.extractSQLFromRequest(request);
                    db = XMLProcessor.extractDBFromRequest(request);
                    logMessage("LOG: Request received...Accessing database: " + db + " - [" + sql + " ]");

                    Document response = XMLProcessor.getResult(db, sql);
                    sendResponseDocument(response);
                }
            }
            catch(IOException io){
                logMessage("ERROR: An error occured while interacting with this client");
                io.printStackTrace();
                
                break;
            }
            catch(ClassNotFoundException ex){
                logMessage("ERROR: " + ex.getMessage());
                ex.printStackTrace();
            }
            catch(SQLException | ParserConfigurationException | DOMException s){
                logMessage("ERROR: An internal error occured while processing the request");
                try{
                    sendResponseDocument(XMLProcessor.generateErrorDocument(sql, s));
                }
                catch(DOMException | ParserConfigurationException | IOException e){ e.printStackTrace(); }                
                s.printStackTrace();
            }
        }        
        
        try{ closeConnection(); }
        catch(IOException e){ e.printStackTrace(); }
    }
    
    private void sendResponseDocument(Document doc) throws IOException{
        out.writeObject(doc);
        out.flush();
        
        logMessage("LOG: Response Sent.");
    }
    
    private void logMessage(String msg){
        setChanged();
        notifyObservers(msg);
    }
    
    public void closeConnection() throws IOException{
        in.close();
        out.close();
        socket.close();
        
        logMessage("CLIENT_DISCONNECTED");
        Server.getInstance().logMessage(String.format("LOG: Connection to User%d@%s was lost.", getID(), getAddress()));
    }
    
}
