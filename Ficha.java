/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rompecabezas;

import com.golden.gamedev.object.sprite.AdvanceSprite;

/**
 *
 * @author Daniela
 */
public class Ficha extends AdvanceSprite{
    private int valor;

    public Ficha(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
        
}
