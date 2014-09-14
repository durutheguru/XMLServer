/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlserver;

/**
 *
 * @author Duru Dumebi Julian
 */

import java.io.IOException;

import xmlserver.net.Server;
import xmlserver.ui.ServerFrame;

import code.ui.Utils;

public class Main {

    public final static int GUI = 0;
    public final static int CONSOLE = 1;
            
    private static int mode;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        if (args.length > 0){
            if (!(args.length == 4 && args[0].equals("-port") && args[2].equals("-queue"))){
                System.err.println("Usage: java -jar XMLServer -port p -queue q");
                System.exit(1);
            }
            
            int port = 0, queue = 0;
            try{
                port = Integer.parseInt(args[1]);
                queue = Integer.parseInt(args[3]);
            }
            catch(NumberFormatException n){
                System.err.println("Invalid entry");
                System.exit(1);
            }
            
            try{
                mode = CONSOLE;
                Server.runServer(port, queue, null);
            }
            catch(IOException io){
                System.out.println("Unable to start server...Message: " + io.getMessage());                
            }
            return;
        }
        
        Utils.setNimbusLAF();
        ServerFrame frame = new ServerFrame();
        frame.setVisible(true);
    }
    
    public static int getMode(){
        return mode;
    }
    
}
