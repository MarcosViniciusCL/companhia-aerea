/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.view;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import uefs.br.ecomp.client.controller.ControllerClient;
import uefs.br.ecomp.server.model.Passagem;
import uefs.br.ecomp.server.model.Trecho;

/**
 *
 * @author marcos
 */
public class MainClient {
    public static void main(String[] args){
        ControllerClient cc = new ControllerClient();
        try {
            cc.conectarServidor("127.0.0.1:1099/ControllerService");
            System.out.println("Conectado.");
            List<Stack> ls = cc.obterCaminho("A", "D");
            for (Stack l : ls) {
                System.out.println("Caminho: "+l.toString());
            }
            Passagem pa = cc.comprarPassagem(ls.get(0));
            System.out.println("TAMANHO: "+pa.getTrechoComprados().size());
            for (Trecho t  : pa.getTrechoComprados()) {
                System.out.println("Origem: "+t.getCidOrigem()+" Destino: "+t.getCidDestino());
            }
            
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(MainClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
