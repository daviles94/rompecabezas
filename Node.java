/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AStar;

import java.util.ArrayList;

/**
 *
 * @author Daniela Velasquez
 * @author David Ruiz 
 */
public class Node {
	
	/*
	 * Represents the nodes of solution three.
	 * @param father father of current node.
	 * @param state state of node. Is a puzzle.
	 * @param value value from heuristic function.
	 * @param erased to erase the current node or not.
	 * */
	
    private Node father;
    private Puzzle state;
    private int value;
    private boolean erased;
    
    /*
     * Constructor.
     * @param father father of actual node.
     * @param actual state of node.
     * */

    public Node(Node father, Puzzle actual) {
        this.father = father;
        this.state = actual;
        value = -999999999;
        erased = false;
    }

    /*
     * Returns the father of actual node.
     * @return father a node.
     * */
    public Node getFather() {
        return father;
    }

    /*
     * Set the father.
     * @param father a node.
     * */
    public void setFather(Node father) {
        this.father = father;
    }
    /*
     * Returns the state of node.
     * @return state a puzzle.
     * */
    public Puzzle getState() {
        return state;
    }
    /*
     * Set the state of node.
     * @param state a puzzle.
     * */
    public void setState(Puzzle currentState) {
        this.state = currentState;
    }
    /*
     * Returns the value of heuristic of node.
     * @return value an integer.
     * */
    public int getValue(){
        return value;
    }
    /*
     * Set the value of heuristic of node.
     * @param value an integer.
     * */
    public void setValue(int value){
        this.value = value;
    }
    /*
     * Returns the number of correct pieces with respect to solution state.
     * @param other the solution state.
     * @return value the number of correct pieces
     * */
    public int correctPieces(Node other){
        return state.nCorrectPieces(other.getState());
    }
    /*
     * Returns the number of correct columns with respect to solution state.
     * @param other the solution state.
     * @return value the number of correct pieces
     * */
    public int correctColumns(Node other){
        return state.nCorrectColumns(other.getState());
    }
    /*
     * Returns the number of correct rows with respect to solution state.
     * @param other the solution state.
     * @return value the number of rows pieces
     * */
    public int correctRows(Node other){
        return state.nCorrectRows(other.getState());
    }    
    /*
     * Returns the distance of manhattan of all pieces with respect solution state.
     * @param other the solution state.
     * @return distance distance of manhattan
     * */
    public int getManhattan(Node other){
        return state.disManhattan(other.getState());
    }
    /*
     * Returns a value if the middle piece in the current node is empty.
     * @return value {0,1} not or empty piece.
     * */
    public int getMiddlePiece(){
        return state.inMiddle();
    }
    /*
     * Returns children of current node.
     * @return value {0,1} not or empty piece.
     * */
    public ArrayList<Node> getChildren(){
        ArrayList<Node> children = new ArrayList<Node>(); 
        ArrayList<Puzzle> stateChildren = state.nextState();
        
        for(Puzzle son:stateChildren){
            children.add(new Node(this,son));
        }
        return children;
    }
    
    public boolean equals(Node other){
        return state.equals(other.getState());
    }
    
    public int[] getChangedValue(Node other){
        return state.getChangedValues(other.getState());
    }
    
    public void setErased(boolean erase){
    	erased = erase;
    }
    
    public boolean isErased(){
    	return erased;
    }
    @Override
    public String toString(){
    	return state.toString();
    }
}
