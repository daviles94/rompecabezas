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
public class Puzzle {
	
	/*
	 * A puzzle is a board with current position pieces.
	 * @param board matrix with numbers like image of board.
	 * */
    private int[][] board;
    
    /*
     * Constructor
     * @param board matrix with numbers like board.
     * */
    public Puzzle(int[][] board) {
        this.board = new int[3][3];
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                this.board[i][j] = board[i][j];
            }
        }
    }
    
    public int[][] getBoard() {
        return board;
    }
    public void setboard(int[][] board) {
        this.board = board;
    }
    public boolean equals(Puzzle other) {
        int[][] otherBoard = other.getBoard();
        int i,j;
        for(i=0;i<3;i++)
            for(j=0;j<3;j++)
                if(board[i][j]!=otherBoard[i][j])
                    return false;
        return true;
    }
    public void changePiece(int x1,int y1,int x2,int y2){
        int aux;
        aux = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = aux;
    }
    
    public int nCorrectPieces(Puzzle other){
        int[][] otherBoard = other.getBoard();
        int i,j,n=0;
        for(i=0;i<3;i++)
            for(j=0;j<3;j++)
                if(board[i][j]==otherBoard[i][j])
                    n++;
        return n;
    }
    
    public int nCorrectColumns(Puzzle other){
        int col,row,ncol=0;
        int[][] otherBoard=other.getBoard();
        for(col=0;col<3;col++){
            int n=0;
            for(row=0;row<3;row++){
                if(board[row][col]==otherBoard[row][col])
                    n++;
                if(n==3)
                    ncol++;
            }
        }
        return ncol;
    }
    
    public int nCorrectRows(Puzzle other){
        int col,row,nrows=0;
        int[][] otherBoard=other.getBoard();
        for(row=0;row<3;row++){
            int n=0;
            for(col=0;col<3;col++){
                if(board[row][col]==otherBoard[row][col])
                    n++;
                if(n==3)
                    nrows++;
            }
        }
        return nrows;
    }
    
    public int getColumn(int value,int [][] fin){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(fin[i][j]==value)
                    return j;
            }
        }
        return -1;
    }
    public int getRow(int value,int [][] board){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]==value)
                    return i;
            }
        }
        return -1;
    }
    public int disManhattan(Puzzle solution){
        int acc=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                int value = board[i][j];
                int x=solution.getRow(value, solution.getBoard());
                if(x==-1)
                    throw new IllegalArgumentException("An error happens in rows");
                int y=solution.getColumn(value, solution.getBoard());
                if(y==-1)
                    throw new IllegalArgumentException("An error happens in columns");
                acc+=Math.abs(i-x)+Math.abs(j-y);
            }
        }
        return acc;
    }
    
    public ArrayList<Puzzle> nextState(){
        int col,row;
        boolean[] movements;
        row = getRow(0,board);
        col = getColumn(0,board);
        ArrayList<Puzzle> children = new ArrayList<Puzzle>();

        movements = getMovements(row,col);
        for(int movement=0;movement<4;movement++){
            if(movements[movement]){
                Puzzle son = new Puzzle(board);
                switch(movement){
                    case 0:son.changePiece(row, col, row-1, col);
                        break;
                    case 1:son.changePiece(row, col, row, col+1);
                        break;
                    case 2:son.changePiece(row, col, row+1, col);
                        break;
                    case 3:son.changePiece(row, col, row, col-1);
                        break;
                }
                children.add(son);
            }
        }
        return children;
    }
    
    public boolean[] getMovements(int row,int col){
        boolean[] mov = new boolean[4];
        mov[0] = row != 0;
        mov[1] = col != 2;
        mov[2] = row != 2;
        mov[3] = col!= 0;
        return mov;
    }
    /*   
    0=up   
    1=right
    2=down
    3=left
    */
    
    public int inMiddle(){
        if(board[1][1]==0)
            return 1;
        else 
            return 0;
    }
    
    public int[] getChangedValues(Puzzle other){
        int[][] otherBoard = other.getBoard();
        int[] values = new int[2];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]!=otherBoard[i][j]){
                	values[0]=board[i][j];
                	values[1]=otherBoard[i][j];
                    return values;                           
                }
            }
        }      
        return null;
    }
    
    @Override
    public String toString(){
    	return board[0][0] +" ," + board[0][1] + " ," + board[0][2] + "\n" +
    			board[1][0] +" ," + board[1][1] + " ," + board[1][2] + "\n" +
    			board[2][0] +" ," + board[2][1] + " ," + board[2][2] + "\n";
    }
}


