/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import uefs.br.ecomp.server.exception.DadoInexistenteException;

/**
 *
 * @author User
 */
public class Grafo implements IGrafo {

    private int id;   //Atribuído aos vértices.
    private final double infinito;
    private final List<Vertice> listaVertice;   //Todos os vértices.
    private final List<Aresta> listaAresta;   //Todos os vértices.

    public Grafo() {
        this.id = 0;
        this.infinito = Double.POSITIVE_INFINITY;   //Representa o infinito positivo double.
        listaVertice = new ArrayList();
        listaAresta = new ArrayList();
    }

    /**
     * Método que retorna a lista de vértices do grafo.
     *
     * @return listaVertice List
     */
    public List getListaVertice() {
        return listaVertice;
    }

    /**
     * Método que retorna a lista de arestas do grafo.
     *
     * @return listaAresta List
     */
    public List getListaAresta() {
        return listaAresta;
    }

    /**
     * Método que verifica se o grafo está vazio, a condição já é feita no
     * retorno; se a lista de vértice está vazia, retorna "true" (valor lógico,
     * verdadeiro) portanto a lista está vazia, caso não, retorna "false" e há
     * pelo menos um elemento na lista.
     *
     * @return boolean (true ou false)
     */
    @Override
    public boolean estaVazio() {
        return (listaVertice.isEmpty());
    }

    /**
     * Método que verifica o tamanho da lista de vértices, o tamanho obtido é
     * retornado.
     *
     * @return int size
     */
    @Override
    public int obterTamanho() {
        return (listaVertice.size());
    }

    /**
     * Método que verifica o tamanho da lista de arestas, o tamanho obtido é
     * retornado.
     *
     * @return int size
     */
    public int quantAresta() {
        return (listaAresta.size());
    }

    /**
     * Método que recebe um nome, caso não haja igual, o vértice é criado e
     * adicionado na lista, recebendo um id para sua identificação.
     *
     * @param nome
     * @return Vertice v
     */
    @Override
    public Vertice inserirVertice(String nome) {

        try {
            //Se o vértice a ser inserido ainda não existe:
            Vertice v = buscarVertice(nome);
            return v;
        } catch (DadoInexistenteException ex) {
            Vertice v = new Vertice(nome);
            v.setId(id++);
            listaVertice.add(v);
            return v;
        }

    }

    /**
     * Método que recebe um nome, caso tal vértice exista, ele é removido da
     * lista e todas suas ligações são removidas.
     *
     * @param nome
     * @throws DadoInexistenteException
     */
    @Override
    public void removerVertice(String nome) throws DadoInexistenteException {

        Vertice v = this.buscarVertice(nome);
        listaVertice.remove(v);
        Iterator<Aresta> it = this.listarArestas();

        while (it.hasNext()) {
            Aresta a = it.next();

            if ((v.equals(a.getOrigem())) || v.equals(a.getDestino())) {
                it.remove();
            }
        }
    }

    /**
     * Método que recebe um nome, caso tal vértice exista, ele é retornado.
     *
     * @param nome
     * @return v Vertice
     * @throws DadoInexistenteException
     */
    @Override
    public Vertice buscarVertice(String nome) throws DadoInexistenteException {
        Iterator<Vertice> it = this.listarVertices();

        while (it.hasNext()) {
            Vertice v = it.next();

            if (nome.equals(v.getNome())) {
                return v;
            }
        }
        throw new DadoInexistenteException("Vértice inexistente!");
    }

    /**
     * Método que recebe um id, caso tal vértice exista, ele é retornado.
     *
     * @param id
     * @return v Vertice
     */
    public Vertice buscarVertice(int id) {
        Iterator<Vertice> it = this.listarVertices();

        while (it.hasNext()) {
            Vertice v = it.next();

            if (id == v.getId()) {
                return v;
            }
        }
        return null;
    }

    /**
     * Método que recebe itera sobre a lista de vértices.
     *
     * @return Iterator
     */
    @Override
    public Iterator listarVertices() {
        return (listaVertice.iterator());
    }

    /**
     * Método que recebe origem, destino e distância, caso não haja aresta
     * igual, ela é criada e adicionada na lista e o grau dos vértices
     * envolvidos são incrementados.
     *
     * @param origem
     * @param destino
     * @param peso
     */
    @Override
    public void inserirAresta(Vertice origem, Vertice destino, double peso) {

        if (this.buscarAresta(origem, destino) == null) {
            listaAresta.add(new Aresta(origem, destino, peso));
            //Grau é qualquer ligação, tanto de entrada como de saída:
            origem.setGrau(origem.getGrau() + 1);
            destino.setGrau(destino.getGrau() + 1);
        }
    }

    /**
     * Método que recebe origem e destino, caso tal aresta exista, ela é
     * removida da lista e o grau dos vértices envolvidos são decrementados.
     *
     * @param origem
     * @param destino
     * @throws DadoInexistenteException
     */
    @Override
    public void removerAresta(Vertice origem, Vertice destino) throws DadoInexistenteException {
        Aresta a = this.buscarAresta(origem, destino);
        listaAresta.remove(a);
        origem.setGrau(origem.getGrau() - 1);
        destino.setGrau(destino.getGrau() - 1);

    }

    /**
     * Método que recebe origem e destino, caso tal aresta exista, ela é
     * retornado.
     *
     * @param origem
     * @param destino
     * @return a Aresta
     * @throws DadoInexistenteException
     */
    @Override
    public Aresta buscarAresta(Vertice origem, Vertice destino) {
        Iterator<Aresta> it = this.listarArestas();

        while (it.hasNext()) {
            Aresta a = it.next();

            if ((origem.equals(a.getOrigem())) && (destino.equals(a.getDestino()))) {
                return a;
            }
        }
        return null;
    }

    /**
     * Método que recebe itera sobre a lista de arestas.
     *
     * @return Iterator
     */
    @Override
    public Iterator listarArestas() {
        return (listaAresta.iterator());
    }

    /**
     * Método que modifica o peso de determinada aresta.
     *
     * @param origem
     * @param destino
     * @param peso
     * @throws DadoInexistenteException
     */
    @Override
    public void modificarPesoAresta(Vertice origem, Vertice destino, double peso) throws DadoInexistenteException {
        Aresta a = this.buscarAresta(origem, destino);
        a.setPeso(peso);
    }

    /**
     * Método que calcula a distância do menor caminho de determinada origem até
     * o destino especificado e retorna sua quilometragem.
     *
     * @param origem
     * @param destino
     * @return peso double
     */
    @Override
    public double menorPeso(int origem, int destino) {
        origem = origem - 1;
        destino = destino - 1;   //Adequa id ao nome dos vértices.
        AuxDjikstra[] vetor = Djikstra(origem, false);

        for (AuxDjikstra vetor1 : vetor) {

            if (vetor1.getThisId() == destino) {
                return vetor1.getPesos();
            }
        }
        return 0;
    }

    /**
     * Método Djikstra, identifica o menor caminho de determinado vértice até
     * todos os outros.
     *
     * @param origem origem.
     * @param sensorMenorDistancia espaço a ser marcado como verdadeiro ou falso
     * @return
     */
    private AuxDjikstra[] Djikstra(int origem, boolean menor) {
        Vertice v = this.buscarVertice(origem);
        AuxDjikstra[] menorCaminho = new AuxDjikstra[listaVertice.size()];
        Iterator<Vertice> it = listaVertice.iterator();

        for (int i = 0; i < menorCaminho.length; i++) {
            Vertice vertice = it.next();
            AuxDjikstra novo = new AuxDjikstra();
            novo.setThisId(vertice.getId());
            novo.setPesos(infinito);
            menorCaminho[i] = novo;
        }
        menorCaminho[v.getId()].setThisId(0);
        menorCaminho[v.getId()].setVisitado(true);
        menorCaminho[v.getId()].setPesos(0);
        menorCaminho[v.getId()].setAntecessor(0);
        double peso = 0;

        while (this.continuar(menorCaminho)) {

            for (AuxDjikstra aux : menorCaminho) {
                peso = this.pesoAresta(v, aux.getThisId());

                if (peso != -333.333) {
                    if (menor) {
                        peso = 1;
                    }
                    if ((menorCaminho[v.getId()].getPesos() + peso) < (aux.getPesos())) {
                        aux.setPesos(menorCaminho[v.getId()].getPesos() + peso);
                        aux.setAntecessor(v.getId());
                    }
                }
            }
            v = this.menorPesoVertice(menorCaminho);
        }
        return menorCaminho;
    }

    /**
     * Método que verifica se há vértices não-visitados.
     *
     * @param menorCaminho
     * @return boolean (true ou false)
     */
    private boolean continuar(AuxDjikstra[] menorCaminho) {

        for (AuxDjikstra menorCaminho1 : menorCaminho) {

            if (menorCaminho1.getVisitado() == false) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que busca aresta e retorna seu peso.
     *
     * @param v
     * @param id
     * @return peso double
     */
    private double pesoAresta(Vertice v, int id) {
        Iterator<Aresta> it = listaAresta.iterator();

        while (it.hasNext()) {
            Aresta a = it.next();

            if (a.equals(new Aresta(v, this.buscarVertice(id), 0))) {
                return a.getPeso();
            }
        }
        return -333.333;
    }

    /**
     * Método que identifica o vértice de menor peso e o retorna.
     *
     * @param menorCaminho
     * @return v Vertice
     */
    private Vertice menorPesoVertice(AuxDjikstra[] menorCaminho) {
        int index = -1;
        double menor = infinito;

        for (int i = 0; i < menorCaminho.length; i++) {

            if ((menorCaminho[i].getVisitado() == false) && (menor >= menorCaminho[i].getPesos()) && (menorCaminho[i].getPesos() != 0)) {
                menor = menorCaminho[i].getPesos();
                index = i;
            }
        }

        if (index != -1) {
            menorCaminho[index].setVisitado(true);
        } else {
            return null;
        }
        return this.buscarVertice(index);
    }

    /**
     * Método que identifica o menor caminho entre determinado vértice de origem
     * e destino, por meio de Djikstra e salvando os antecessores.
     *
     * @param origem
     * @param destino
     * @param menor
     * @return caminho List
     */
    @Override
    public List menorCaminho(int origem, int destino, boolean menor) {
        origem = origem - 1;
        destino = destino - 1;
        List caminho = new ArrayList();
        AuxDjikstra[] rota = Djikstra(origem, menor);
        boolean achou;

        while ((destino != origem) && (destino != -5)) {
            achou = false;
            caminho.add(this.buscarVertice(destino));

            for (int i = 0; ((i < rota.length) && (achou == false)); i++) {

                if (rota[i].getThisId() == destino) {
                    destino = rota[i].getAntecessor();
                    achou = true;
                }
            }
        }

        if (destino == origem) {
            caminho.add(this.buscarVertice(destino));
        }
        return caminho;
    }

    /**
     * Método que identifica todas as possíveis rotas formada pela AuxGrafo e
     * retorna um Iterador sobre todos os caminhos possíveis.
     *
     * @param origem
     * @param destino
     * @param aux
     * @return Iterator
     */
    @Override
    public Iterator possiveisRotas(String origem, String destino, AuxGrafo aux) {
        aux.setInicio(origem);
        List a = aux.primeiraBorda(destino);
        aux.limpar();
        return (a.iterator());
    }

    /**
     * Método que identifica o vértice de maior grau e o retorna.
     *
     * @return v Vertice
     */
    public Vertice maiorGrau() {
        Vertice v = listaVertice.get(0);

        for (int i = 0; i < this.obterTamanho(); i++) {

            if (v.getGrau() <= listaVertice.get(i).getGrau()) {
                v = listaVertice.get(i);
            }
        }
        return v;
    }

    /**
     * Busca os caminhos a partir de origem destino
     *
     * @param origemNome
     * @param destinoNome
     * @return
     * @throws uefs.br.ecomp.server.exception.DadoInexistenteException
     */
    public List<Stack> buscarCaminhos(String origemNome, String destinoNome) throws DadoInexistenteException {
        Vertice origem = this.buscarVertice(origemNome);
        Vertice destino = this.buscarVertice(destinoNome);
        List<Stack> caminhos = new ArrayList<>();
        Stack<Vertice> pilhaMain = new Stack();
        if (listaVertice.isEmpty()) {
            return caminhos;
        }
        origem.setVisitado(true);
        pilhaMain.push(origem);
        while (!pilhaMain.isEmpty()) {
            Vertice adjacente = getVizinhoNaoVisitado(pilhaMain.peek());
            if (adjacente == null) {
                pilhaMain.pop();
            } else {
                adjacente.setVisitado(true);
                pilhaMain.push(adjacente);
                if (adjacente.equals(destino)) {// se o vizinho for o destino salve uma copia da pilha
                    caminhos.add((Stack) pilhaMain.clone());
                }
            }
        }
        return caminhos;
    }

//    public List<Stack> buscarCaminhosTrechos(String origemNome, String destinoNome) throws DadoInexistenteException {
//        
//    }
    /**
     * Retorna um vizinho não visitado do nó
     *
     * @param v
     * @return
     */
    public Vertice getVizinhoNaoVisitado(Vertice v) {
        for (Aresta aresta : listaAresta) {
            if (aresta.getOrigem().equals(v)) {
                Vertice vizinho = aresta.getDestino();
                if (!vizinho.isVisitado()) {
                    return vizinho;
                }
            }
        }
        return null;
    }
}
