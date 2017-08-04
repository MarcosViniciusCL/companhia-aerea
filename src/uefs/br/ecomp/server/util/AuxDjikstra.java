/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.util;

/**
 *
 * @author User
 */
class AuxDjikstra {
      private int thisId;   //Recebe id do vértice.
    private boolean visitado;   //Marca como visitado.
    private double pesos;   //Salva distâncias.
    private int antecessor = -5;   //Salva id do antecessor.

    /** Método que retorna o id do vértice.
     *
     * @return thisId int
     */
    public int getThisId() {
        return thisId;
    }
    
    /** Método que altera o id do vértice.
     *
     * @param thisId
     */
    public void setThisId(int thisId) {
        this.thisId = thisId;
    }
    
    /** Método que retorna estado do vértice.
     *
     * @return visitado  boolean (true ou false)
     */
    public boolean getVisitado() {
        return visitado;
    }

    /** Método que altera o estado do vértice.
     *
     * @param visitado
     */
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    /** Método que retorna o peso acumulado até o vértice.
     *
     * @return pesos double
     */
    public double getPesos() {
        return pesos;
    }
    
    /** Método que altera o peso acumulado até o vértice.
     *
     * @param pesos
     */
    public void setPesos(double pesos) {
        this.pesos = pesos;
    }

    /** Método que retorna o antecessor do vértice.
     *
     * @return antecessor int
     */
    public int getAntecessor() {
        return antecessor;
    }

    /** Método que altera o antecessor do vértice.
     *
     * @param antecessor
     */
    public void setAntecessor(int antecessor) {
        this.antecessor = antecessor;
    }
}
