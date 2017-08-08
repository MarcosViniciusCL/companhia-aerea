/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.client.controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import uefs.br.ecomp.server.model.IController;

/**
 *
 * @author marcos
 */
public class ControllerClient {

    private IController servidor;

    public void conectarServidor(String endereco) throws NotBoundException, MalformedURLException, RemoteException {
        this.servidor = (IController) Naming.lookup("rmi://" + endereco);
    }

    public void obterCaminho(String cidOrigem, String cidDestino) throws RemoteException {
        servidor.obterCaminho(cidOrigem, cidDestino);
    }
}
