/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.view;

import java.rmi.RemoteException;
import uefs.br.ecomp.server.controller.Controller;

/**
 *
 * @author marcos
 */
public class Main {
    public static void main(String[] args) throws RemoteException{
        Controller c = new Controller();
//        c.iniciarServico("ControllerService");
        c.carregarServidores();
    }
}
