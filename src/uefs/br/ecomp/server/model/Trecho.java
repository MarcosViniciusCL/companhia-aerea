/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.model;

import uefs.br.ecomp.server.exception.ReservaExcedidaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcos
 */
public class Trecho {
    private String cidOrigem;
    private String cidDestino;
    private String compania;
    private List<Passagem> passagem;
    private int distancia;

    public Trecho(String cidOrigem, String cidDestino, String compania) {
        this.cidOrigem = cidOrigem;
        this.cidDestino = cidDestino;
        this.compania = compania;
        this.passagem = new ArrayList<>();
    }
    
    public void resevarTrecho(Passagem passagem) throws ReservaExcedidaException{
        if(this.passagem.size() > 0)
            throw new ReservaExcedidaException();
        this.passagem.add(passagem);
    }
    
    public void limparReservas(){
        this.passagem.clear();
    }

    @Override
    public String toString(){
        return cidOrigem+"->"+cidDestino;
    }
    
    public String getCidOrigem() {
        return cidOrigem;
    }

    public void setCidOrigem(String cidOrigem) {
        this.cidOrigem = cidOrigem;
    }

    public String getCidDestino() {
        return cidDestino;
    }

    public void setCidDestino(String cidDestino) {
        this.cidDestino = cidDestino;
    }

    public int getDistancia() {
        return distancia;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public List<Passagem> getPassagem() {
        return passagem;
    }

    public void setPassagem(List<Passagem> passagem) {
        this.passagem = passagem;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
    
    
}
