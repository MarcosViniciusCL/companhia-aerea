/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcos
 */
public abstract class Help {
    
    public static void sleep(long v){
        try {
            Thread.sleep(v);
        } catch (InterruptedException ex) {
            Logger.getLogger(Help.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
