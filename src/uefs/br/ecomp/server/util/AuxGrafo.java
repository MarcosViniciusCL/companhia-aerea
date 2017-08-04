/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.util;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author User
 */
class AuxGrafo {
     private String inicio, ultimo;    
    private List<String> lista = new LinkedList();
    private LinkedList<String> visitado;
    private final Map<String, LinkedHashSet<String>> mapa = new HashMap();   //Mapa chave-valor: vértice-adjacentes.
    
    /** Método que retorna o nome do vértice inicial.
     * 
     * @return  inicio
     */
    public String getInicio() {
        return inicio;
    }

    /** Método que altera o nome do vértice inicial.
     * 
     * @param  inicio
     */
    public void setInicio(String inicio) {
        this.inicio = inicio;  
        visitado = new LinkedList();
        visitado.add(inicio);
    }

    /** Método que retorna o nome do vértice final.
     * 
     * @return  final
     */
    public String getUltimo() {
        return ultimo;
    }

    /** Método que altera o nome do vértice final.
     * 
     * @param ultimo
     */
    public void setUltimo(String ultimo) {
        this.ultimo = ultimo;
    }

    /** Método que insere dois nomes em um mapa, no caso; do vértice de origem e
     * destino.
     *
     * @param origem
     * @param destino
     */
    public void adicionar(String origem, String destino) {
        LinkedHashSet<String> adjacente = mapa.get(origem);
        
        if (adjacente == null) {
            adjacente = new LinkedHashSet();
            mapa.put(origem, adjacente);
        }
        adjacente.add(destino);
    }

    /** Método recursivo que retorna todos os caminhos possíveis do um ponto a
     * outro, salva todos os caminhos numa lista, cada caminho em uma posição.
     *
     * @param origem
     * @return lista List
     */
    public List primeiraBorda(String origem) {
        LinkedList<String> nos = nosAdjacente(visitado.getLast());
        
        for (String no : nos) {
            
            if (visitado.contains(no)) {
                continue;
            }
            
            if (no.equals(origem)) {
                visitado.add(no);
                printarNaLista(visitado);
                visitado.removeLast();
                break;
            }
        }
        
        for (String no : nos) {
            
            if (visitado.contains(no) || no.equals(origem)) {
                continue;
            }
            visitado.addLast(no);
            primeiraBorda(origem);
            visitado.removeLast();
        }
        return lista;
    }

    /** Método que retorna uma lista de adjacência de acordo com a origem especificada.
     * 
     * @param origem
     * @return 
     */
    public LinkedList<String> nosAdjacente(String origem) {
        LinkedHashSet<String> adjacente = mapa.get(origem);
        
        if (adjacente == null) {
            return (new LinkedList());
        }
        return (new LinkedList(adjacente));
    }    

    /** Método que concatena caminho por caminho e salva numa lista.
     *
     * @param visitado LinkedList
     */
    private void printarNaLista(LinkedList<String> visitado) {
        String nova = " ";
        
        for (String no : visitado) {
            nova = nova + " -> " + no;
        }
        nova += "\n \n";
        lista.add(nova);
    }
    
    /** Método que zera a lista.
     */
    public void limpar(){
        lista = new LinkedList<>();
    }
}
