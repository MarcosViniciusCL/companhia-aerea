/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author marcos
 */
public interface IController extends Remote{
    
    public String buscarTrecho(String destino) throws RemoteException;
    
    public List<Trecho> getTrechoDisponivel() throws RemoteException;
}
