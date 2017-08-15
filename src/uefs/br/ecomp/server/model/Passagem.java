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
class Passagem {
    private int id;
    private String nomeComprador;
    private List<Trecho> trechoParaComprar;
    private List<Trecho> trechoComprados;

    public Passagem(String nomeComprador, Stack trechos) {
        this.id = 0;
        this.nomeComprador = nomeComprador;
        this.trechoComprados = new ArrayList<>();
  //      this.trechoParaComprar = gerarList(trechos);
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

//    private List<Trecho> gerarList(Stack<Trecho> trechos) {
//        List<Trecho> array = new ArrayList<>();
//        for (Trecho trecho : trechos) {
//            array.add(trecho);
//        }
//    }
    
    
}
