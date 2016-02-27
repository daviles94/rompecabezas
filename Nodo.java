/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabezas;

import java.util.ArrayList;

/**
 *
 * @author Daniela
 */
public class Nodo {
    private Nodo padre;
    private Puzzle estado;
    private int valor;

    public Nodo(Nodo padre, Puzzle actual) {
        this.padre = padre;
        this.estado = actual;
        valor = -999999999;
    }

    public Nodo getPadre() {
        return padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public Puzzle getEstado() {
        return estado;
    }

    public void setEstado(Puzzle actual) {
        this.estado = actual;
    }
    public int getValor(){
        return valor;
    }
    public void setValor(int valor){
        this.valor = valor;
    }
    public int piezasCorrectas(Nodo otro){
        return estado.npiezasCorrectas(otro.getEstado());
    }
    public int columnasCorrectas(Nodo otro){
        return estado.ncolCorrectas(otro.getEstado());
    }
    public int filasCorrectas(Nodo otro){
        return estado.nfilCorrectas(otro.getEstado());
    }    
    public int getManhattan(Nodo otro){
        return estado.disManhattan(otro.getEstado());
    }
    public int getPiezaMedio(){
        return estado.enMedio();
    }
    public ArrayList<Nodo> getHijos(){
        ArrayList<Nodo> hijos = new ArrayList(); 
        ArrayList<Puzzle> estadosHijos = estado.nextEstado();
        
        for(Puzzle h:estadosHijos){
            Nodo hi= new Nodo(this,h);
            hijos.add(hi);
        }
        return hijos;
    }
    
    public boolean equals(Nodo otro){
        return estado.equals(otro.getEstado());
    }
    
    public int[] getValorCambiado(Nodo otro){
        return estado.getValoresCambiados(otro.getEstado());
    }
}
