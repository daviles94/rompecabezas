/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabezas;

import com.golden.gamedev.*;
import com.golden.gamedev.object.*;
import com.golden.gamedev.object.background.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 *
 * @author Daniela Velasquez Aguilar y David Ruiz Martin-Romo
 */
public class Rompecabezas extends Game{

    private Ficha[] tableroInicial;
    private Ficha[] tableroFinal;
    private int[][] indicesSoluciones;
    private ArrayList<int[]> valores;
    private SpriteGroup t0,t1;
    private int selec1,selec2,selec1t,selec2t;
    private int nclicks;
    private int nclicks2;
    private int nfichas;
    private int iteradorIndices;
    private ImageBackground fondo,fondo2;
    private boolean semaforo;
    /**
     * @param args the command line arguments
     */
    public void cargarImagenes() {
     bsLoader.storeImages("0", getImages("images/cero.png", 1, 1));
     bsLoader.storeImages("1", getImages("images/uno.png", 1, 1));
     bsLoader.storeImages("2", getImages("images/dos.png", 1, 1));
     bsLoader.storeImages("3", getImages("images/tres.png", 1, 1));     
     bsLoader.storeImages("4", getImages("images/cuatro.png", 1, 1));
     bsLoader.storeImages("5", getImages("images/cinco.png", 1, 1));
     bsLoader.storeImages("6", getImages("images/seis.png", 1, 1));
     bsLoader.storeImages("7", getImages("images/siete.png", 1, 1));     
     bsLoader.storeImages("8", getImages("images/ocho.png", 1, 1));     
    }
    
    @Override
    public void initResources() {
    valores = new ArrayList();
    semaforo=false;
    selec1=-1;
    selec2=-1;
    selec1t=-1;
    selec2t=-1;
    nclicks = 0;
    nclicks2 = 0;
    nfichas = 9;
    cargarImagenes();
    tableroInicial = new Ficha[nfichas];
    tableroFinal = new Ficha[nfichas];
    fondo = new ImageBackground(getImage("images/fondo.jpg"));
    fondo2 = new ImageBackground(getImage("images/fondoContinuar.jpg"));
    
    for(int i = 0;i<nfichas;i++){
        tableroInicial[i] = new Ficha(i);
        tableroFinal[i]= new Ficha(i);
    }
    
    for(int i = 0;i<nfichas;i++){
        tableroInicial[i].setImages(bsLoader.getStoredImages(Integer.toString(i)));
        tableroFinal[i].setImages(bsLoader.getStoredImages(Integer.toString(i)));
    }
    
    int i,n,y,x,o=0;

    n=230;
    //230,40
    for(y=0;y<3;y++)
    {
        i=40;
        for(x=0;x<3;x++)
        {
            tableroInicial[o].setX(i);
            tableroFinal[o].setX(i+479);
            tableroInicial[o].setY(n);
            tableroFinal[o].setY(n);
            o++;
            i+=65;
        }
        n+=65;
    }
    
    t0 = new SpriteGroup("tablero inicial");
    t1 = new SpriteGroup("tablero final");
    
    for(i = 0;i<nfichas;i++){
        t0.add(tableroInicial[i]);
        t1.add(tableroFinal[i]);
    }    
    t0.setBackground(fondo);
    t1.setBackground(fondo);
    setFPS(10);
    }
    
    /**
     *
     * @param elapsedTime
     */
    @Override
    public void update(long elapsedTime) {
        for(int i=0;i<nfichas;i++){
            tableroInicial[i].update(elapsedTime);
            tableroFinal[i].update(elapsedTime);
        }
        if(!solucionar()){
            if(click()){
                if(nclicks == 0)
                {
                    int x1 = getMouseX();
                    int y1 = getMouseY();
                    selec1 = encontrarImagen(x1,y1);
                    if(selec1!=-1)
                        nclicks++;
                }
                else
                {
                    int x2 = getMouseX();
                    int y2 = getMouseY();
                    selec2 = encontrarImagen(x2,y2);
                    if(selec2!=-1){
                        nclicks=0;
                        cambiaImagen(selec1,selec2,elapsedTime);   

                    }

                }
                if(nclicks2==0)
                                {
                    int x1 = getMouseX();
                    int y1 = getMouseY();
                    selec1t = encontrarImagen2(x1,y1);
                    if(selec1t!=-1)
                        nclicks2++;
                }
                else
                {
                    int x2 = getMouseX();
                    int y2 = getMouseY();
                    selec2t = encontrarImagen2(x2,y2);
                    if(selec2t!=-1){
                            cambiaImagen2(selec1t,selec2t);           
                            nclicks2=0;

                        }
                }
            }
        }
        else{
            algoritmo();
            iteradorIndices=valores.size()-1;
            semaforo = true;
        }            
        if(semaforo && iteradorIndices != -1){
            int in1 = getIndice(valores.get(iteradorIndices)[0]);
            int in2 = getIndice(valores.get(iteradorIndices)[1]);
            if(keyDown(KeyEvent.VK_SPACE)){
                cambiaImagen(in1,in2,elapsedTime);
                iteradorIndices--;
                    if(iteradorIndices == -1){
                        semaforo = false;
                        valores.clear();
                    }
            }
        }   
    }
    public int encontrarImagen(double x,double y){
        for(int i=0;i<nfichas;i++){
            if(x>=tableroInicial[i].getX()&&x<=tableroInicial[i].getX()+tableroInicial[i].getWidth()){
                if(y>=tableroInicial[i].getY()&&y<=tableroInicial[i].getY()+tableroInicial[i].getHeight()){
                    return i;
                }
            }
        }
        return -1;
    }
    public int encontrarImagen2(double x,double y){
        for(int i=0;i<nfichas;i++){
            if(x>=tableroFinal[i].getX()&&x<=tableroFinal[i].getX()+tableroFinal[i].getWidth()){
                if(y>=tableroFinal[i].getY()&&y<=tableroFinal[i].getY()+tableroFinal[i].getHeight()){
                    return i;
                }
            }
        }
        return -1;
    }
    
    public void cambiaImagen(int selec1,int selec2,long elapsedTime)
    {
        Ficha aux;
        double x,y;
        x = tableroInicial[selec1].getX();
        y = tableroInicial[selec1].getY();
        tableroInicial[selec1].setX(tableroInicial[selec2].getX());
        tableroInicial[selec1].setY(tableroInicial[selec2].getY());
        tableroInicial[selec2].setX(x);
        tableroInicial[selec2].setY(y);
        aux = tableroInicial[selec1];
        tableroInicial[selec1] = tableroInicial[selec2];
        tableroInicial[selec2]=aux;
    }
    public void cambiaImagen2(int selec1,int selec2)
    {
        Ficha aux;
        double x,y;
        x = tableroFinal[selec1].getX();
        y = tableroFinal[selec1].getY();
        tableroFinal[selec1].setX(tableroFinal[selec2].getX());
        tableroFinal[selec1].setY(tableroFinal[selec2].getY());
        tableroFinal[selec2].setX(x);
        tableroFinal[selec2].setY(y);
        aux = tableroFinal[selec1];
        tableroFinal[selec1] = tableroFinal[selec2];
        tableroFinal[selec2]=aux;
    }
    
    public boolean solucionar(){
        if(click()){
            if(getMouseX()>633 && getMouseX()<779){
                if(getMouseY()>521&&getMouseY()<568)
                    return true;
            }
        }
        return false;
    }
    @Override
    public void render(Graphics2D g) {
        // Graphics Engine (bsGraphics)
        // getting window size: getWidth(), getHeight()
        if(!semaforo)
            fondo.render(g);
        else   
            fondo2.render(g);
        t0.render(g);
        t1.render(g);

    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        GameLoader game = new GameLoader();
        game.setup(new Rompecabezas(), new Dimension(800,600), false);
        game.start();
    }

    public void algoritmo(){
        Puzzle inicial,end;
        int[][] m0 = new int[3][3];
        int[][] m1 = new int[3][3];
        int c=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                m0[i][j]= tableroInicial[c].getValor();
                m1[i][j]=tableroFinal[c].getValor();
                c++;
            }
        }
        inicial = new Puzzle(m0);
        end = new Puzzle(m1);
        
        Nodo raiz = new Nodo(null,inicial);
        Nodo solucion  = new Nodo(null,end);
        Astar grafo = new Astar(raiz,solucion);
        ArrayList<Nodo> camino = grafo.encontrarCamino();

        Nodo hijo = camino.get(camino.size()-1);
        while(hijo.getPadre()!=null){
            Nodo padre = hijo.getPadre();
            int[] v = hijo.getValorCambiado(padre);
            if(v!=null){
                valores.add(v);
            }
            hijo = hijo.getPadre();
        }
    }
    
    public int getIndice(int valor){
        for(int i=0;i<nfichas;i++){
            if(tableroInicial[i].getValor()==valor){
                return i;
            }
        }
        return -1;
    }
    public boolean igual(){
        for(int i=0;i<nfichas;i++){
            if(tableroInicial[i].getValor()!=tableroFinal[i].getValor()){
                return false;
            }
        }
        return true;
    }
}
