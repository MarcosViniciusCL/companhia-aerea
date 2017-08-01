/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uefs.br.ecomp.server.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcos
 */
public class Arquivo {

    public String ler(String endArq) {
        BufferedReader buffRead;
        try {
            buffRead = new BufferedReader(new FileReader(endArq));
            String temp;
            String linha = "";
            while ((temp = buffRead.readLine()) != null) {
                linha += temp;
            }
            buffRead.close();
            return linha;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void gravar(String endArq, String texto) {
        FileWriter arq;
        try {
            arq = new FileWriter(endArq);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf(texto);
            arq.close();
        } catch (IOException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
