/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.model;

/**
 *
 * @author marcos
 */
class Passagem {
    private int id;
    private String nomeComprador;

    public Passagem(String nomeComprador) {
        this.id = 0;
        this.nomeComprador = nomeComprador;
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
    
    
}
