/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.controller;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import uefs.br.ecomp.server.exception.DadoInexistenteException;
import uefs.br.ecomp.server.exception.ReservaExcedidaException;
import uefs.br.ecomp.server.model.Arquivo;
import uefs.br.ecomp.server.model.Help;
import uefs.br.ecomp.server.model.IController;
import uefs.br.ecomp.server.model.Passagem;
import uefs.br.ecomp.server.model.Trecho;
import uefs.br.ecomp.server.util.Aresta;
import uefs.br.ecomp.server.util.Grafo;
import uefs.br.ecomp.server.util.Vertice;
import uefs.br.ecomp.server.view.MainServer;

/**
 *
 * @author marcos
 */
public class Controller extends UnicastRemoteObject implements IController {

    private static Controller controller;
    private String meuEndereco;

    private final List<IController> servidores;
    private final List<Trecho> trechos;
    private Grafo grafo;

    private Controller() throws RemoteException {
        super();
        this.servidores = new ArrayList<>();
        this.trechos = new ArrayList<>();
        this.grafo = null;
        this.meuEndereco = this.getEnderecoIP();
    }

    /**
     * Adiciona um novo servidor RMI servidor RMI;
     *
     * @param rmi
     */
    public void novoServidor(String rmi) {
        String[] str = rmi.split("/");
        try {
            IController cont = (IController) Naming.lookup("rmi://" + rmi);
            this.servidores.add(cont);
//            cont.carregarTrechos();
            System.out.println("Adicionado: " + str[0] + "\nServiço: " + str[1]);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.err.println("ERRO, NÃO ADICIONADO.\nServidor:" + str[0] + "\nServiço: " + str[1] + "\n"
                    + "verifique se o servidor/serviço está disponivel.");
        }
    }

    /**
     * Carregado o arquivo servidores.conf, nele está os servidores RMI.
     */
    @Override
    public void carregarServidores() {
        this.servidores.clear();
        String texto;
        Arquivo arq = new Arquivo();
        System.out.println("Lendo arquivo de servidores...");
        texto = arq.ler("servidores.conf");
        System.out.println("Adicionando servidores e serviços...");
        String[] serv = texto.split(";");
        for (String servidor : serv) {
            System.out.println("SERVIDOR: " + servidor);
            novoServidor(servidor);
        }
//        System.out.println("Todos servidores foram adicionados.");
        Help.sleep(1000);
//        carregarTrechos(); //Carrega os trechos após os servidores;
    }

    /**
     * Carrega os trechos que estão no arquivo trechos.conf.
     */
    @Override
    public void carregarTrechos() {
        this.trechos.clear();
        String texto;
        Arquivo arq = new Arquivo();
        System.out.println("Carregando arquivo de trechos...");
        texto = arq.ler("trechos.conf");
        System.out.println("Adicionando trechos ao servidor...");
        String[] auxTrechos = texto.split(";");
        for (String trecho : auxTrechos) {
            String[] aux = trecho.split(":");
            Trecho aux2 = new Trecho(aux[0], aux[1], "companhia X");
            this.trechos.add(aux2);
            System.out.println(aux[0] + "<->" + aux[1]);
        }
        System.out.println("Todos os trechos foram adicionados.");
    }

    /**
     * Solicita a todos os servidores seus trechos.
     */
    public void obterTrechosServidore() {

        for (IController servidor : this.servidores) {
            try {
                servidor.getTrechoDisponivel();
            } catch (RemoteException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Trecho> getTrechoDisponivel() {
        this.carregarTrechos();
        return trechos;
    }

    @Override
    public Passagem comprarTrechoServer(Passagem passagem) {
        if (passagem.rotaComprada()) {
            return passagem;
        }
        if (trechoLocal(passagem.getTrechoParaComprar())) {
            try {
                marcarTrechos(passagem);
            } catch (ReservaExcedidaException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return passagem;
    }

    public void iniciarServico(String nomeServico) {
        try {
            System.out.println("iniciando serviço...");
            Registry reg = LocateRegistry.createRegistry(1099);
            IController c = new Controller();
            Naming.bind(nomeServico, c);
            System.out.println("serviço \"" + nomeServico + "\" iniciado.");
        } catch (RemoteException | MalformedURLException | AlreadyBoundException ex) {
            System.out.println("Não foi possivel iniciar.");
        }
    }

    /**
     * Metodo usado pelo cliente para solicitar o caminho para o servidor.
     *
     * @param origem - Cidade de origem da viagem.
     * @param destino - Cideda destino da viagem
     * @return List - Retorna uma lista de possiveis caminhos.
     */
    @Override
    public List<Stack> obterCaminho(String origem, String destino) {

        try {
            gerarGrafo();
            return this.grafo.buscarCaminhos(origem, destino);
        } catch (DadoInexistenteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void gerarGrafo() {
        List<Trecho> listTrechos = new ArrayList<>();
        List<Trecho> obj;

        for (Trecho trecho : this.trechos) {
            listTrechos.add(trecho);
        }

        for (IController servidor : this.servidores) {
            try {
                obj = servidor.getTrechoDisponivel(); //O servidor retorna os seus trechos disponiveis.
                for (Trecho trecho : obj) {
                    listTrechos.add(trecho);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Gerando lista com os nomes das cidades.
        List<String> nomesCidades = new ArrayList<>();
        for (Trecho trecho : listTrechos) {
            String cidade1 = trecho.getCidOrigem();
            String cidade2 = trecho.getCidDestino();
            if (!nomesCidades.contains(cidade1)) {
                nomesCidades.add(cidade1);
            }
            if (!nomesCidades.contains(cidade2)) {
                nomesCidades.add(cidade2);
            }
        }

        //Criando o grafo
        this.grafo = new Grafo();
        for (String cidade : nomesCidades) { //Adicionando vertices no grafo
            this.grafo.inserirVertice(cidade);
        }
        for (Trecho trecho : listTrechos) { //adicionando arestas.
            this.grafo.inserirAresta(new Vertice(trecho.getCidOrigem()), new Vertice(trecho.getCidDestino()), 5);
        }

    }

    public static Controller getInstance() {
        if (Controller.controller == null) {
            try {
                Controller.controller = new Controller();
            } catch (RemoteException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Controller.controller;
    }

    @Override
    public void carregarDados() throws RemoteException {
        this.carregarServidores();
        this.carregarTrechos();
    }

    @Override
    public Passagem comprarTrechos(Stack<Vertice> pilha) throws RemoteException {
        List<Trecho> tc = obterTrechos(pilha); //Converte a pilha de vertices em trechos;
        Passagem pas = new Passagem("Comprador", tc);
        if(trechoLocal(tc)){
            comprarTrechoServer(pas);
        }
        for (IController servidor : this.servidores) {
            pas = servidor.comprarTrechoServer(pas);
        }
        return pas;
    }

    private List<Trecho> obterTrechos(Stack<Vertice> pilha) {
        List<Trecho> tc = new ArrayList<>();
        Vertice[] v = pilhaParaVetor(pilha);
        int i = 0, j = i + 1;
        Aresta obj;
        for (int k = 0; k < v.length; k++) {
            obj = this.grafo.buscarAresta(v[i], v[j]);
            if (obj != null) {
                Trecho trecho = new Trecho(obj.getOrigem().getNome(), obj.getDestino().getNome(), "");
                tc.add(trecho);
            }
            i++;
            j++;
        }
        return tc;
    }
    
    private Vertice[] pilhaParaVetor(Stack<Vertice> pilha){
        int tamanho = pilha.size();
        Vertice[] v = new Vertice[tamanho];
        for (int i = 0; i < tamanho; i++) {
            v[i] = pilha.pop();
        }
        return v;
    }

    private boolean trechoLocal(List<Trecho> la) {
        for (Trecho trecho : la) {
            if (this.trechos.contains(trecho)) {
                return true;
            }
        }
        return false;

    }

    private boolean marcarTrechos(Passagem ps) throws ReservaExcedidaException {
        List<Trecho> la = ps.getTrechoParaComprar();
        int index;
        Trecho tc;
        for (Trecho trecho : la) {
            index = this.trechos.indexOf(la);
            if (index > -1) {
                tc = this.trechos.get(index);
                tc.resevarTrecho(ps);
                ps.trechoResevado(tc);
            }
        }
        return true;
    }

    public String getEnderecoIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
