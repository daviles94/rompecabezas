/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AStar;

import com.golden.gamedev.object.sprite.AdvanceSprite;

/**
 *
 * @author Daniela
 */
public class Piece extends AdvanceSprite{
    /**
	 * Each image of board.
	 */
	private static final long serialVersionUID = -3843684905853969772L;
	private int value;

	/**
	 * Constructor.
	 * @param value The number of image.
	 * */
    public Piece(int value) {
        this.value = value;
    }
    /*
     * Get the value of piece.
     * @return value number of image.
     * */
    public int getValue() {
        return value;
    }
    /*
     * Set the value.
     * @param value number of image.
     * */
    public void setValor(int value) {
        this.value = value;
    }
        
}
