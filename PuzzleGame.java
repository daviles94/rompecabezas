/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AStar;

import com.golden.gamedev.*;
import com.golden.gamedev.object.*;
import com.golden.gamedev.object.background.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author Daniela Velasquez Aguilar
 * @author David Ruiz Martin-Romo
 */
public class PuzzleGame extends Game{
	/*
	 * Run a puzzle game with AI by A* algorithm.
	 * @see com.golden.gamedev.Game
	 * */	

    private Piece[] originalBoard;
    private Piece[] goalBoard;
    private ArrayList<int[]> values;
    private SpriteGroup originalBoardSGroup,goalBoardSGroup;
    private int ImageOBFirst,ImageOBSecond,ImageGBFirst,ImageGBSecond;
    private int nclicks;
    private int nclicks2;
    private int npieces;
    private int iteratorIndex;
    private ImageBackground background,background2;
    private boolean semaphore;
    
    /**
     * Load all images used in the Game.
     */
    public void loadImages() {
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
    /*
     * All game resources initialization. It's an inherited function from Game.
     * This function is similar a constructor.
     * @see com.golden.gamedev.Game#initResources()
    */
    @Override
    public void initResources() {
	    values = new ArrayList<int[]>();
	    semaphore=false;
	    ImageOBFirst=-1;
	    ImageOBSecond=-1;
	    ImageGBFirst=-1;
	    ImageGBSecond=-1;
	    nclicks = 0;
	    nclicks2 = 0;
	    npieces = 9;
	    loadImages();
	    originalBoard = new Piece[npieces];
	    goalBoard = new Piece[npieces];
	    background = new ImageBackground(getImage("images/fondo.jpg"));
	    background2 = new ImageBackground(getImage("images/fondoContinuar.jpg"));
	    
	    for(int i = 0;i<npieces;i++){
	        originalBoard[i] = new Piece(i);
	        goalBoard[i]= new Piece(i);
	    }
	    
	    for(int i = 0;i<npieces;i++){
	        originalBoard[i].setImages(bsLoader.getStoredImages(Integer.toString(i)));
	        goalBoard[i].setImages(bsLoader.getStoredImages(Integer.toString(i)));
	    }
	    
	    int i,n,y,x,o=0;
	
	    n=230;
	    //(230,40) Point for start to build the board.
	    for(y=0;y<3;y++)
	    {
	        i=40;
	        for(x=0;x<3;x++)
	        {
	            originalBoard[o].setX(i);
	            goalBoard[o].setX(i+479);
	            originalBoard[o].setY(n);
	            goalBoard[o].setY(n);
	            o++;
	            i+=65; //space between images in the x axis.
	        }
	        n+=65; //space between images in the y axis.
	    }
	    
	    originalBoardSGroup = new SpriteGroup("original board");
	    goalBoardSGroup = new SpriteGroup("goal board");
	    
	    for(i = 0;i<npieces;i++){
	        originalBoardSGroup.add(originalBoard[i]);
	        goalBoardSGroup.add(goalBoard[i]);
	    }    
	    originalBoardSGroup.setBackground(background);
	    goalBoardSGroup.setBackground(background);
	    setFPS(10);
    }
    
    /**
     * Updates game variables.
     * @param elapsedTime 
     */
    @Override
    public void update(long elapsedTime) {
        for(int i=0;i<npieces;i++){
            originalBoard[i].update(elapsedTime);
            goalBoard[i].update(elapsedTime);
        }
        if(!solve()){
        	
            if(click()){
                if(nclicks == 0)
                {
                    int x1 = getMouseX();
                    int y1 = getMouseY();
                    ImageOBFirst = findImageOriginalBoard(x1,y1);
                    if(ImageOBFirst!=-1)
                        nclicks++;
                }
                else
                {
                    int x2 = getMouseX();
                    int y2 = getMouseY();
                    ImageOBSecond = findImageOriginalBoard(x2,y2);
                    if(ImageOBSecond!=-1){
                        nclicks=0;
                        changeImageOriginalBoard(ImageOBFirst,ImageOBSecond);   

                    }

                }
                if(nclicks2==0)
                                {
                    int x1 = getMouseX();
                    int y1 = getMouseY();
                    ImageGBFirst = findImageGoalBoard(x1,y1);
                    if(ImageGBFirst!=-1)
                        nclicks2++;
                }
                else
                {
                    int x2 = getMouseX();
                    int y2 = getMouseY();
                    ImageGBSecond = findImageGoalBoard(x2,y2);
                    if(ImageGBSecond!=-1){
                            changeImageGoalBoard(ImageGBFirst,ImageGBSecond);           
                            nclicks2=0;

                        }
                }
            }
        }
        else{
            astar();
            iteratorIndex=values.size()-1;
            semaphore = true;
        }            
        if(semaphore && iteratorIndex != -1){
            int in1 = getIndex(values.get(iteratorIndex)[0]);
            int in2 = getIndex(values.get(iteratorIndex)[1]);
            if(keyDown(KeyEvent.VK_SPACE)){
                changeImageOriginalBoard(in1,in2);
                iteratorIndex--;
                    if(iteratorIndex == -1){
                        semaphore = false;
                        values.clear();
                    }
            }
        }   
    }
    /*
     * Find the image in the original board. Every number in the board is a image of a piece with number.
     * @param x Point in the x axis.
     * @param y Point in the y axis.
     * */
    
    public int findImageOriginalBoard(double x,double y){
        for(int i = 0; i < npieces; i++){
            if(x >= originalBoard[i].getX()&& x<= originalBoard[i].getX() + originalBoard[i].getWidth()){
                if( y >= originalBoard[i].getY() && y <= originalBoard[i].getY() + originalBoard[i].getHeight()){
                    return i;
                }
            }
        }
        return -1;
    }
    /*
     * Find the image in the goal board. Every number in the board is a image of a piece with number.
     * @param x Point in the x axis.
     * @param y Point in the y axis.
     * */
    public int findImageGoalBoard(double x,double y){
        for(int i=0;i<npieces;i++){
            if(x>=goalBoard[i].getX()&&x<=goalBoard[i].getX()+goalBoard[i].getWidth()){
                if(y>=goalBoard[i].getY()&&y<=goalBoard[i].getY()+goalBoard[i].getHeight()){
                    return i;
                }
            }
        }
        return -1;
    }
    /*
     * Change two images from original board. The first image replaces the position of the second image.
     * @param ImageOBFirst First image we want to change.
     * @param ImageOBSecond Second image we want to change.
     * */
    public void changeImageOriginalBoard(int ImageOBFirst,int ImageOBSecond)
    {
        Piece aux;
        double x,y;
        x = originalBoard[ImageOBFirst].getX();
        y = originalBoard[ImageOBFirst].getY();
        originalBoard[ImageOBFirst].setX(originalBoard[ImageOBSecond].getX());
        originalBoard[ImageOBFirst].setY(originalBoard[ImageOBSecond].getY());
        originalBoard[ImageOBSecond].setX(x);
        originalBoard[ImageOBSecond].setY(y);
        aux = originalBoard[ImageOBFirst];
        originalBoard[ImageOBFirst] = originalBoard[ImageOBSecond];
        originalBoard[ImageOBSecond]=aux;
    }
    /*
     * Change two images from goal board. The first image replaces the position of the second image.
     * @param ImageGBFirst First image we want to change.
     * @param ImageGBSecond Second image we want to change.
     * */
    public void changeImageGoalBoard(int ImageGBFirst,int ImageGBSecond)
    {
        Piece aux;
        double x,y;
        //System.out.println(ImageOBFirst.getValor()+","+ImageOBSecond.getValor());
        x = goalBoard[ImageGBFirst].getX();
        y = goalBoard[ImageGBFirst].getY();
        goalBoard[ImageGBFirst].setX(goalBoard[ImageGBSecond].getX());
        goalBoard[ImageGBFirst].setY(goalBoard[ImageGBSecond].getY());
        goalBoard[ImageGBSecond].setX(x);
        goalBoard[ImageGBSecond].setY(y);
        aux = goalBoard[ImageGBFirst];
        goalBoard[ImageGBFirst] = goalBoard[ImageGBSecond];
        goalBoard[ImageGBSecond]=aux;
    }
    
    /*
     * Clicks on button "Solucionar" run this function.
     * */
    public boolean solve(){
        if(click()){
            if(getMouseX()>633 && getMouseX()<779){
                if(getMouseY()>521&&getMouseY()<568)
                    return true;
            }
        }
        return false;
    }
    /*
     * Renders game to the screen. It's an inherited function from Game.
     * @see Game
     * */
    @Override
    public void render(Graphics2D g) {
        // Graphics Engine (bsGraphics)
        // getting window size: getWidth(), getHeight()
        if(!semaphore)
            background.render(g);
        else   
            background2.render(g);
        originalBoardSGroup.render(g);
        goalBoardSGroup.render(g);

    }
    /*
     * Runs the game
     * */
    public static void main(String[] args) {
        GameLoader game = new GameLoader();
        game.setup(new PuzzleGame(), new Dimension(800,600), false);
        game.start();
    }
    /*
     * Runs the A* algorithm and find the path.
     * */
    public void astar(){
        Puzzle originalPuzzle,goalPuzzle;
        int[][] originalMatrix = new int[3][3];
        int[][] goalMatrix = new int[3][3];
        int c=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
            	originalMatrix[i][j]= originalBoard[c].getValue();
            	goalMatrix[i][j]=goalBoard[c].getValue();
                c++;
            }
        }
        originalPuzzle = new Puzzle(originalMatrix);
        goalPuzzle = new Puzzle(goalMatrix);
        
        Node root = new Node(null,originalPuzzle);
        Node goal  = new Node(null,goalPuzzle);
        Astar astar = new Astar(root,goal);
        
        astar.findPath();
        
        ArrayList<Node> path = astar.getPath();
        Node son = path.get(path.size()-1);
        
		while(son.getFather()!=null){
            Node father = son.getFather();
            int[] value = son.getChangedValue(father);
            if(value!=null){
                values.add(value);
            }
            son = son.getFather();
        }
    }
    /*
     * Each image has a value that is the number. Returns the index on the array with this value.
     * @param value an integer value. It is the same of image. */
    public int getIndex(int value){
        for(int i=0;i<npieces;i++){
            if(originalBoard[i].getValue() == value){
                return i;
            }
        }
        return -1;
    }
    /*
     * Compare the numbers of image and returns a boolean value.
     * */
    public boolean equal(){
        for(int i=0;i<npieces;i++){
            if(originalBoard[i].getValue()!=goalBoard[i].getValue()){
                return false;
            }
        }
        return true;
    }
}
