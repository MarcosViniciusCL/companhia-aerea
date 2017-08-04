/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.exception;

/**
 *
 * @author User
 */
public class DadoInexistenteException extends Exception {
       
    public DadoInexistenteException() {
        super();
    }

    public DadoInexistenteException(String str) {
        super(str);
    }

    public DadoInexistenteException(Throwable exception) {
        super(exception);
    }
}
