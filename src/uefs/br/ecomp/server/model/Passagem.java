/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author marcos
 */
public class Passagem {
    private int id;
    private String nomeComprador;
    private List<Trecho> trechoParaComprar;
    private List<Trecho> trechoComprados;

    public Passagem(String nomeComprador, List<Trecho> trechos) {
        this.id = 0;
        this.nomeComprador = nomeComprador;
        this.trechoComprados = new ArrayList<>();
        this.trechoParaComprar = trechos;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeComprador() {
        return nomeComprador;
    }

    public void setNomeComprador(String nomeComprador) {
        this.nomeComprador = nomeComprador;
    }
    
    public void trechoResevado(Trecho tc){
        this.trechoParaComprar.remove(tc);
        this.trechoComprados.add(tc);
    }
    
    public boolean rotaComprada(){
        return this.trechoParaComprar.isEmpty();
    }

    public List<Trecho> getTrechoParaComprar() {
        return trechoParaComprar;
    }
    
    
}
