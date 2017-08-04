/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.controller;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uefs.br.ecomp.server.model.Arquivo;
import uefs.br.ecomp.server.model.Help;
import uefs.br.ecomp.server.model.IController;
import uefs.br.ecomp.server.model.Trecho;

/**
 *
 * @author marcos
 */
public class Controller extends UnicastRemoteObject implements IController {

    private final List<IController> servidores;
    private final List<Trecho> trechos;
    
    public Controller() throws RemoteException{
        super();
        servidores = new ArrayList<>();
        this.trechos = new ArrayList<>();
    }

    /**
     * Adiciona um novo servidor RMI servidor RMI;
     * @param rmi 
     */
    public void novoServidor(String rmi) {
        String[] str = rmi.split("/");
        try {
            IController cont = (IController) Naming.lookup("rmi://" + rmi);
            servidores.add(cont);
            System.out.println("Adicionado: "+str[0]+"\nServiço: "+str[1]);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.err.println("ERRO, NÃO ADICIONADO.\nServidor:"+str[0]+"\nServiço: "+str[1]+"\n"
                    + "verifique se o servidor/serviço está disponivel.");
        }
    }
    
    /**
     * Carregado o arquivo servidores.conf, nele está os servidores RMI.
     */
    public void carregarServidores(){
        String texto;
        Arquivo arq = new Arquivo();
        System.out.println("Lendo arquivo de servidores...");
        texto = arq.ler("servidores.conf");
        System.out.println("Adicionando servidores e serviços...");
        String[] serv = texto.split(";");
        for (String servidor : serv) {
            System.out.println("SERVIDORES: "+servidor);
            novoServidor(servidor);
        }
//        System.out.println("Todos servidores foram adicionados.");
        Help.sleep(1000);
        carregarTrechos(); //Carrega os trechos após os servidores;
    }
    
    /**
     * Carrega os trechos que estão no arquivo trechos.conf.
     */
    public void carregarTrechos(){
        String texto;
        Arquivo arq = new Arquivo();
        System.out.println("Carregando arquivo de trechos...");
        texto = arq.ler("trechos.conf");
        System.out.println("Adicionando trechos ao servidor...");
        String[] trechos = texto.split(";");
        for (String trecho : trechos) {
            String[] aux = trecho.split(":");
            Trecho aux2 = new Trecho(aux[0], aux[1], "companhia X");
            this.trechos.add(aux2);
            System.out.println(aux[0]+"<->"+aux[1]);
        }
        System.out.println("Todos os trechos foram adicionados.");
    }
    
    /**
     * Solicita a todos os servidores seus trechos.
     */
    public void obterTrechosServidore(){
        
        for (IController servidor : servidores) {
            try {
                servidor.getTrechoDisponivel();
            } catch (RemoteException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Trecho> getTrechoDisponivel() {
        return trechos;
    }

    @Override
    public String buscarTrecho(String destino) {
        return "Não implementado";
    }

    public void iniciarServico(String nomeServico) {
        try {
            System.out.println("iniciando serviço...");
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            LocateRegistry.createRegistry(1099);
            IController c = new Controller();
            Naming.bind(nomeServico, (Remote) c);
            System.out.println("serviço \""+nomeServico+"\" iniciado.");
        } catch (RemoteException | AlreadyBoundException | MalformedURLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
