/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlserver;

/**
 *
 * @author Duru Dumebi Julian
 */

import java.util.Date;
import java.util.Observer;
import java.util.Observable;

public class ConsoleLogger implements Observer{
    
    private static ConsoleLogger instance;
    
    private ConsoleLogger(){}
    
    public static synchronized ConsoleLogger getInstance(){
        if (instance == null)
            instance = new ConsoleLogger();
        
        return instance;
    }
    
    @Override
    public void update(Observable o, Object arg){
        System.out.println((String)arg + "\n" + new Date(System.currentTimeMillis()).toString() + "\n");
    }
    
}
