package algorithm.graph;

import java.util.ArrayList;
import java.util.List;

class GNode {
	char id;
	int val;
	int cost;
	List<GNode> adjs;

	public GNode(int val) {
		this.val = val;
		this.adjs = new ArrayList<>();
	}

	public GNode(char id, int cost) {
		this.id = id;
		this.adjs = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "GNode [val=" + val + ", cost=" + cost + ", adjs=" + adjs + "]";
	}
}
