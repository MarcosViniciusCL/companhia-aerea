/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author marcos
 */
public interface IController extends Remote{
    
    public String buscarTrecho(String destino) throws RemoteException;
    
    public List<Trecho> getTrechoDisponivel() throws RemoteException;
    
    public List<Stack> obterCaminho(String origem, String destino) throws RemoteException;

    public void carregarDados() throws RemoteException;
   
    public void carregarServidores() throws RemoteException;
    
    public void carregarTrechos() throws RemoteException;
    
    public void comprarTrechos(Stack pilha) throws RemoteException;
}
