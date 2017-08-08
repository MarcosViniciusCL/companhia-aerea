/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.view;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import uefs.br.ecomp.server.controller.Controller;
import uefs.br.ecomp.server.model.IController;

/**
 *
 * @author marcos
 */
public class MainServer {

    public static void main(String[] args) throws RemoteException {
        Controller c = Controller.getInstance();
        IController rmi = null;
        while (true) {
            System.out.println("1 - Iniciar Serviço\n2 - Carregar Servidores\n3 - Carregar Trechos\n4 - SAIR");
            String resp = new Scanner(System.in).next();
            switch (resp) {
                case "1":
                    c.iniciarServico("ControllerService");
                    rmi = conectarRMI();
                    break;
                case "2":
                    if (rmi != null) {
                        rmi.carregarServidores();
                        break;
                    }
                case "3":
                    if (rmi != null) {
                        rmi.carregarTrechos();
                        break;
                    }
                case "4":
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    public static IController conectarRMI() {
        try {
            return (IController) Naming.lookup("rmi://localhost:1099/ControllerService");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
