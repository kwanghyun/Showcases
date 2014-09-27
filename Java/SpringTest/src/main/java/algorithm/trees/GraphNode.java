package algorithm.trees;

import java.util.ArrayList;
import java.util.List;


public class GraphNode {

	int value;
	State state;
	List<GraphNode> children = new ArrayList<GraphNode>();
	
	public GraphNode(int val){
		value = val;
		state = State.Unvisited;
	}
	public List<GraphNode> getAdjacent(){
		return children;
	}
	
	public void addChild(GraphNode node){
		children.add(node);
	}
	
	public void removeChild(GraphNode node){
		children.remove(node);
	}
}
