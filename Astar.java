/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabezas;

import java.util.ArrayList;

/**
 *
 * @author Daniela Velasquez
 * @author David Ruiz
 233
 */
public class Astar {
    private ArrayList<Nodo> openGeneral;
    private ArrayList<Nodo> close;
    private int profundidadLimite;
    private Nodo raiz,solucion;
    
    public Astar(Nodo raiz,Nodo solucion){
        profundidadLimite = 10;
        this.raiz = raiz;
        this.solucion = solucion;
        openGeneral = new ArrayList();
        close = new ArrayList();
    }
    
    public void encontrarCaminoParcial(){
        Nodo x;
        ArrayList<Nodo> open = new ArrayList();
        open.add(raiz);
        int profundidad = 0;
        boolean fin=false;
        ArrayList<Nodo> hijos;
        while(!open.isEmpty() && !fin){
            x = heuristica(open,profundidad);
            borrar(open,x);
            close.add(x);
            if(x.equals(solucion)){
                fin = true;                
            }
            else{
                if(profundidad != profundidadLimite){
                    hijos = x.getHijos();        
                    profundidad++;
                    if(!hijos.isEmpty()){    
                        borrar(open,hijos);
                        borrar(close,hijos);
                        borrar(openGeneral,hijos);
                        for(Nodo h: hijos){
                            open.add(h);
                        }
                    }
                }
                else{
                    for(Nodo o : open){
                        openGeneral.add(o);
                    }
                    fin = true;
                }
            }
        }
    }
    
    public ArrayList<Nodo> encontrarCamino(){
        int i = 1;
        do{
            encontrarCaminoParcial();
            i++;
            raiz = close.get(close.size()-1);
        }while(!raiz.equals(solucion));            
        return close;
    }
    
    public Nodo heuristica(ArrayList<Nodo> v,int profundidad){
        Nodo x;
        int i;
        for(i=0;i<v.size();i++){
            x = v.get(i);
            if(x.getValor()==-999999999)
                calcularValor(profundidad,x);
        }
        x=v.get(0);
        for(i=1;i<v.size();i++){
            if(x.getValor()<v.get(i).getValor())
                x=v.get(i);
        }
        return x;
    }
    public void calcularValor(int p,Nodo i){
        int v;
        v = p*p*p + i.piezasCorrectas(solucion) + i.columnasCorrectas(solucion)+2*i.filasCorrectas(solucion) - i.getManhattan(solucion)+ i.getPiezaMedio();
        i.setValor(v);
    }
    
    public void borrar(ArrayList<Nodo> oc,ArrayList<Nodo> hijos){
        Nodo aux,aux2;
        ArrayList<Integer> ind = new ArrayList();
        for(int i = 0; i<hijos.size();i++){
            aux = hijos.get(i);
            for (Nodo oc1 : oc) {
                aux2 = oc1;
                if(aux.equals(aux2))
                {
                    ind.add(i);
                }
            }
        }
        if(ind.size()>0){
            for(Integer x : ind){
                hijos.remove(x.intValue());
            }
        }
    }
    public void borrar(ArrayList<Nodo> oc,Nodo n){
        int indice = -1;
        Nodo aux;
        
        for(int i=0;i<oc.size();i++){
            aux = oc.get(i);
            if(aux.equals(n))
               indice = i;
        }
        if(indice != -1)
            oc.remove(indice);
    }
    
}
