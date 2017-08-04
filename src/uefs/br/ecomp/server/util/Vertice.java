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
class Vertice {
     private final String nome;
    private int id, grau, x, y;   //Coordenada (x,y) para os vértices na interface.

    /** Construtor da classe, o campo nome é atribuído e, id e grau são inicializados.
     * 
     * @param nome
     */
    public Vertice(String nome) {
        this.nome = nome;
        this.id = 0;
        this.grau = 0;
    }
    
    /** Método que retorna o nome do vértice.
     *
     * @return nome String
     */
    public String getNome() {
        return nome;
    }

    /** Método que retorna o id do vértice.
     *
     * @return id int
     */
    public int getId() {
        return id;
    }

    /** Método que altera o id do vértice.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /** Método que retorna o grau do vértice.
     *
     * @return grau int
     */
    public int getGrau() {
        return grau;
    }

    /** Método que altera o grau do vértice.
     *
     * @param grau
     */
    public void setGrau(int grau) {
        this.grau = grau;
    }

    /** Método que retorna o valor de x na coordenada do vértice.
     *
     * @return x int
     */
    public int getX() {
        return x;
    }

    /** Método que altera o valor de x na coordenada do vértice.
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /** Método que retorna o valor de y na coordenada do vértice.
     *
     * @return y int
     */
    public int getY() {
        return y;
    }

    /** Método que altera o valor de y na coordenada do vértice.
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /** Método que compara dois vértices, critério: o nome dos vértices.
     *
     * @param o
     * @return boolean (true ou false)
     */
    @Override
    public boolean equals(Object o) {

        if (o instanceof Vertice) {
            return (this.toString().equals(o.toString()));
        }
        return false;
    }

    /** Método que retorna o atributo nome do vértice.
     *
     * @return nome String
     */
    @Override
    public String toString() {
        return nome;
    }
}
