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
public class Puzzle {
    private int[][] tablero;

    public Puzzle(int[][] tableroPadre) {
        this.tablero = new int[3][3];
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                this.tablero[i][j] = tableroPadre[i][j];
            }
        }
    }
    
    public int[][] getTablero() {
        return tablero;
    }
    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
    }
    public boolean equals(Puzzle otro) {
        int[][] tablerofinal = otro.getTablero();
        int i,j;
        for(i=0;i<3;i++)
            for(j=0;j<3;j++)
                if(tablero[i][j]!=tablerofinal[i][j])
                    return false;
        return true;
    }
    public void cambiarPieza(int x1,int y1,int x2,int y2){
        int aux;
        aux = tablero[x1][y1];
        tablero[x1][y1] = tablero[x2][y2];
        tablero[x2][y2] = aux;
    }
    
    public int npiezasCorrectas(Puzzle otro){
        int[][] tablerofinal = otro.getTablero();
        int i,j,n=0;
        for(i=0;i<3;i++)
            for(j=0;j<3;j++)
                if(tablero[i][j]==tablerofinal[i][j])
                    n++;
        return n;
    }
    
    public int ncolCorrectas(Puzzle otro){
        int col,fil,ncol=0;
        int[][] tablerofinal=otro.getTablero();
        for(col=0;col<3;col++){
            int n=0;
            for(fil=0;fil<3;fil++){
                if(tablero[fil][col]==tablerofinal[fil][col])
                    n++;
                if(n==3)
                    ncol++;
            }
        }
        return ncol;
    }
    
    public int nfilCorrectas(Puzzle otro){
        int col,fil,nfil=0;
        int[][] tablerofinal=otro.getTablero();
        for(fil=0;fil<3;fil++){
            int n=0;
            for(col=0;col<3;col++){
                if(tablero[fil][col]==tablerofinal[fil][col])
                    n++;
                if(n==3)
                    nfil++;
            }
        }
        return nfil;
    }
    
    public int getColumna(int valor,int [][] fin){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(fin[i][j]==valor)
                    return j;
            }
        }
        return -1;
    }
    public int getFila(int valor,int [][] fin){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(fin[i][j]==valor)
                    return i;
            }
        }
        return -1;
    }
    public int disManhattan(Puzzle sol){
        int acc=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                int valor = tablero[i][j];
                int x=sol.getFila(valor, sol.getTablero());
                if(x==-1)
                    throw new IllegalArgumentException("Ha ocurrido un error en las filas");
                int y=sol.getColumna(valor, sol.getTablero());
                if(y==-1)
                    throw new IllegalArgumentException("Ha ocurrido un error en las columnas");
                acc+=Math.abs(i-x)+Math.abs(j-y);
            }
        }
        return acc;
    }
    
    public ArrayList<Puzzle> nextEstado(){
        int col,fil;
        boolean[] movimientos;
        fil = getFila(0,tablero);
        col = getColumna(0,tablero);
        ArrayList<Puzzle> hijos = new ArrayList();

        movimientos = getMovimientos(fil,col);
        for(int x=0;x<4;x++){
            if(movimientos[x]){
                Puzzle hijo = new Puzzle(tablero);
                switch(x){
                    case 0:hijo.cambiarPieza(fil, col, fil-1, col);
                        break;
                    case 1:hijo.cambiarPieza(fil, col, fil, col+1);
                        break;
                    case 2:hijo.cambiarPieza(fil, col, fil+1, col);
                        break;
                    case 3:hijo.cambiarPieza(fil, col, fil, col-1);
                        break;
                }
                hijos.add(hijo);
            }
        }
        return hijos;
    }
    
    public boolean[] getMovimientos(int fil,int col){
        boolean[] mov = new boolean[4];
        mov[0] = fil != 0;
        mov[1] = col != 2;
        mov[2]=fil != 2;
        mov[3] = col!= 0;
        return mov;
    }
    /*
    0=arriba
    1=derecha
    2=abajo
    3=izquierda
    */
    
    public int enMedio(){
        if(tablero[1][1]==0)
            return 1;
        else 
            return 0;
    }
    
    public int[] getValoresCambiados(Puzzle otro){
        int[][] otrot = otro.getTablero();
        int[] valores = new int[2];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(tablero[i][j]!=otrot[i][j]){
                    valores[0]=tablero[i][j];
                    valores[1]=otrot[i][j];
                    return valores;                           
                }
            }
        }      
        return null;
    }
}


