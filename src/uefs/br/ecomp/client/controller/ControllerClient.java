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
import java.util.List;
import java.util.Stack;
import uefs.br.ecomp.server.model.IController;
import uefs.br.ecomp.server.model.Passagem;
import uefs.br.ecomp.server.util.Vertice;

/**
 *
 * @author marcos
 */
public class ControllerClient {

    private IController servidor;

    public void conectarServidor(String endereco) throws NotBoundException, MalformedURLException, RemoteException {
        this.servidor = (IController) Naming.lookup("rmi://" + endereco);
    }

    public List<Stack> obterCaminho(String cidOrigem, String cidDestino) throws RemoteException {
        return servidor.obterCaminho(cidOrigem, cidDestino);
    }
    
    public Passagem comprarPassagem(Stack<Vertice> pilha) throws RemoteException{
         return this.servidor.comprarTrechos(pilha);
    }
}
