/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.model;

import java.io.Serializable;
import uefs.br.ecomp.server.exception.ReservaExcedidaException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author marcos
 */
public class Trecho implements Serializable{
    private String origem;
    private String destino;
    private String companhia;
    private List<Passagem> passagem;
    private int distancia;

    public Trecho(String cidOrigem, String cidDestino, String compania) {
        this.origem = cidOrigem;
        this.destino = cidDestino;
        this.companhia = compania;
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
        return origem+"->"+destino;
    }
    
    @Override
    public boolean equals(Object obj){
        Trecho o = (Trecho) obj;
        return this.getCidDestino().equals(o.getCidDestino()) && this.getCidOrigem().equals(o.getCidOrigem());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.origem);
        hash = 79 * hash + Objects.hashCode(this.destino);
        hash = 79 * hash + Objects.hashCode(this.companhia);
        hash = 79 * hash + Objects.hashCode(this.passagem);
        hash = 79 * hash + this.distancia;
        return hash;
    }
    
    public String getCidOrigem() {
        return origem;
    }

    public void setCidOrigem(String cidOrigem) {
        this.origem = cidOrigem;
    }

    public String getCidDestino() {
        return destino;
    }

    public void setCidDestino(String cidDestino) {
        this.destino = cidDestino;
    }

    public int getDistancia() {
        return distancia;
    }

    public String getCompania() {
        return companhia;
    }

    public void setCompania(String compania) {
        this.companhia = compania;
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
