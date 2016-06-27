package algorithm.graph;

import java.util.ArrayList;

public class UGNode {
	int label;
	ArrayList<UGNode> neighbors;

	UGNode(int x) {
		label = x;
		neighbors = new ArrayList<UGNode>();
	}

}
