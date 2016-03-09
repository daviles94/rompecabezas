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
public class Astar {
	
	/*
	 * Astar Class. It has the A* algorithm.
	 * */
    private ArrayList<Node> openFull;
    private ArrayList<Node> close;
    private int depthLimit;
    private Node root,goal;

    /*
     * Constructor. Initialize depth variable. In this case, the depth limit is 4.
     * @param root Node root, the initial root to start the algorithm.
     * @param goal Node goal, the ending or goal of the algorithm.
     * */
	public Astar(Node root,Node goal){
    	depthLimit = 4;
        this.root = root;
        this.goal = goal;
        openFull = new ArrayList<Node>();
        close = new ArrayList<Node>();
    }
    /*
     * Find 4 solutions nodes of the path.
     * */
    private void findPartialPath(){
        Node selectedOpen;
        ArrayList<Node> open = new ArrayList<Node>();
        open.add(root);
        int depth = 0;
        boolean end=false;
        ArrayList<Node> sons;
        while(!open.isEmpty() && !end){
        	selectedOpen = findSelectedNode(open,depth);
            remove(open,selectedOpen);
            close.add(selectedOpen);
            if(selectedOpen.equals(goal)){
            	end = true;                
            }
            else{
                if(depth != depthLimit){
                	sons = selectedOpen.getChildren();       
                    depth++;
                    if(!sons.isEmpty()){    
                        remove(open,sons);
                        remove(close,sons);
                        remove(openFull,sons);
                        for(Node son: sons){
                            open.add(son);
                        }
                    }
                }
                else{
                    for(Node Node : open){
                    	openFull.add(Node);
                    }
                    end = true;
                }
            }
        }
    }
    /*
     * Find full path.
     * */
    
    public void findPath(){
    	while(!root.equals(goal)){
    		findPartialPath();
            root = close.get(close.size()-1);
            close.remove(close.size()-1);
        }
    	close.add(root);
    }
    /*
     *Print all nodes of the path. 
     * */
    public void printPath(){
    	for(Node n: close){
    		System.out.println(n);
    	}
    }
    /*
     * From array open (possibles son) select one for path.
     * @param NodesOpen array of possibles sons.
     * @param depth depth limit of algorithm.
     * */
    private Node findSelectedNode(ArrayList<Node> NodesOpen,int depth){
        Node Node;
        addHeuristic(NodesOpen,depth);
        Node=NodesOpen.get(0);
        for(int i=1;i<NodesOpen.size();i++){
            if(Node.getValue()<NodesOpen.get(i).getValue())
            	Node=NodesOpen.get(i);
        }
        return Node;
    }
    /*
     * Add value to possibles sons.
     * @param NodesOpen array of possibles sons.
     * @param depth depth limit of algorithm.
     * */
    private void addHeuristic(ArrayList<Node> NodesOpen,int depth){
    	Node Node;
        for(int i=0;i<NodesOpen.size();i++){
        	Node = NodesOpen.get(i);
            if(Node.getValue()==-999999999)
            	heuristic(depth,Node);
        }
    }
    /*
     * Heuristic function selected.
     * @param depth depth limit of algorithm.
     * @param Node each node, a possible son.
     * */
    private void heuristic(int depth,Node Node){
        int value;
        value = depth*depth*depth + Node.correctPieces(goal) + Node.correctColumns(goal)+2*Node.correctRows(goal) - Node.getManhattan(goal)+ Node.getMiddlePiece();
        Node.setValue(value);
    }
    /*
     * Remove duplicate sons of other fathers.
     * @param Nodes array of possibles sons or the solution path.
     * @param sons array of possibles sons from selected father.
     * */
    private void remove(ArrayList<Node> Nodes,ArrayList<Node> sons){
        Node aux;
        for(int i = 0; i<sons.size();i++){
            aux = sons.get(i);
            for (Node node : Nodes) {
                if(aux.equals(node))
                {
                	aux.setErased(true);
                }
            }
        }
        ArrayList<Node> copy = new ArrayList<Node>();
        
        for(Node son: sons){
        	if(!son.isErased())
        		copy.add(son);
        }
        sons.clear();
        for(Node sonCopy: copy){
        		sons.add(sonCopy);
        }
        copy.clear();
        copy = null;
    }
    /*
     * Remove one node from array nodes.
     * @param nodes array of nodes.
     * @param node node to erase.
     * */
    private void remove(ArrayList<Node> nodes,Node node){
        int index = -1;
        Node aux;
        
        for(int i=0;i<nodes.size();i++){
            aux = nodes.get(i);
            if(aux.equals(node))
               index = i;
        }
        if(index != -1)
            nodes.remove(index);
    }
    /*
     * Returns the solution path.
     * @return close full path.
     * */
    public ArrayList<Node> getPath(){
    	return close;
    }
}
