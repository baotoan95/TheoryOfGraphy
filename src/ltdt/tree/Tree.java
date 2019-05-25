package ltdt.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.IntStream;

import ltdt.graph.Graph;
import ltdt.graph.impl.DirectedGraph;
import ltdt.graph.impl.UndirectedGraph;

public class Tree {
	private Graph graph;
	private List<Edge> edges;
	
	public Tree(int numOfVexs) {
		this.graph = new UndirectedGraph(numOfVexs);
		convertAdjMatrixToListEdges();
	}
	
	public Tree(Graph graph) throws Exception {
		if(graph instanceof DirectedGraph) {
			throw new Exception("Please input undirected graph");
		}
		this.graph = graph;
		convertAdjMatrixToListEdges();
	}
	
	private void convertAdjMatrixToListEdges() {
		this.edges = new ArrayList<Edge>();
		int[][] adjMatrix = this.graph.getAdjMatrix();
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < i; j++) {
				if(adjMatrix[i][j] != 0) {
					this.edges.add(new Edge(i + 1, j + 1, adjMatrix[i][j]));
				}
			}
		}
	}
	
	private int[][] convertListEdgesToAdjMatrix(List<Edge> edges, int numOfVertexes) {
		int[][] adjMatrix = new int[numOfVertexes][numOfVertexes];
		edges.forEach(vex -> {
			adjMatrix[vex.getStart() - 1][vex.getEnd() - 1] = vex.getWeight();
			adjMatrix[vex.getEnd() - 1][vex.getStart() - 1] = vex.getWeight();
		});
		return adjMatrix;
	}
	
	public Graph findKruskal() throws Exception {
		List<Edge> path = new ArrayList<>();
		Queue<Edge> listEdges = new PriorityQueue<>();
		listEdges.addAll(this.edges);
		Graph graph = new UndirectedGraph(this.graph.getNumOfVexs());
		int[] parents = initParents();
		while(!listEdges.isEmpty()) {
			// Get sorted element and remove from queue
			Edge edge = listEdges.remove();
			if(!isMakeCycle(parents, edge)) {
				path.add(edge);
			}
		}
		graph.setAdjMatrix(convertListEdgesToAdjMatrix(path, graph.getNumOfVexs()));
		int totalWeight = 0;
		for(Edge e : path) {
			totalWeight += e.getWeight(); 
		}
		System.out.println("Total weight: " + totalWeight);
		return graph;
	}
	
	/**
	 * Union-find algorithm
	 */
	private boolean isMakeCycle(int[] parents, Edge edge) {
		int x = find(parents, edge.getStart() - 1);
		int y = find(parents, edge.getEnd() - 1);
		if(x != y) {
			union(parents, x, y);
		}
		return x == y;
	}
	
	private int find(int [] parents, int vertex) {
        // Chain of parent pointers from x upwards through the tree
        // until an element is reached whose parent is itself
        if(parents[vertex] != vertex) {
        	return find(parents, parents[vertex]);
        }
        return vertex;
    }
	
	private void union(int [] parents, int x, int y){
        int x_set_parent = find(parents, x);
        int y_set_parent = find(parents, y);
        // make x as parent of y
        parents[y_set_parent] = x_set_parent;
    }
	
	private int[] initParents() {
		int[] parents = new int[this.graph.getNumOfVexs()];
		for (int i = 0; i < parents.length; i++) {
			parents[i] = i;
		}
		return parents;
	}
	
	public Graph findPrim() throws Exception {
		// Store constructed MST
		int[] parent = new int[graph.getNumOfVexs()];
		// Contains minimum weight edge
		int[] key = new int[graph.getNumOfVexs()];
		// Mark vertices not yet included in MST
		boolean[] mstSet = new boolean[graph.getNumOfVexs()];
		// Initial as infinity
		for (int i = 0; i < graph.getNumOfVexs(); i++) {
			key[i] = Integer.MAX_VALUE;
			mstSet[i] = false;
		}
		// Add first 1st vertex to MST
		key[0] = 0; // Make key 0 so that this vertex is picked as first
		parent[0] = -1; // First node is always root of MST
		int[][] adjMatrix = graph.getAdjMatrix();
		for (int i = 0; i < graph.getNumOfVexs() - 1; i++) {
			// Pick the minimum key vertex from the set of vertices which not include in MST
			int u = minKey(key, mstSet);
			// Add picked vertex to MST
			mstSet[u] = true;
			// Update parent and minimum key of u by find all of u's neighbors
			for (int v = 0; v < graph.getNumOfVexs(); v++) {
				if(adjMatrix[u][v] != 0 && mstSet[v] == false && adjMatrix[u][v] < key[v]) {
					// Mark u as parent of v
					parent[v] = u;
					// Add weight of edge (u - v) to key
					key[v] = adjMatrix[u][v];
				}
			}
		}
		
		// Create minimum spinning tree
		Graph graph = new UndirectedGraph(this.graph.getNumOfVexs());
		for (int i = 1; i < this.graph.getNumOfVexs(); i++) {
            graph.addEdge(parent[i] + 1, i + 1, this.graph.getAdjMatrix()[i][parent[i]]);
		}
		System.out.println("Total weight: " + IntStream.of(key).sum());
		return graph;
	}
	
	private int minKey(int[] key, boolean[] mstSet) {
		int min = Integer.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < graph.getNumOfVexs(); i++) {
			if(mstSet[i] == false && key[i] < min) {
				min = key[i];
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
//	public List<Integer> findSpinningTreeDFS() {
//		List<Integer> rs = new ArrayList<>();
//		rs.add(1);
//		visit(1, rs);
//		return rs;
//	}
//	
//	private void visit(int vex, List<Integer> rs) {
//		Queue<Integer> neighbor = this.graph.findNextVertexes(vex);
//		while (!neighbor.isEmpty()) {
//			int visitVertex = neighbor.poll();
//			if(!rs.contains(visitVertex)) {
//				rs.add(visitVertex);
//				visit(visitVertex, rs);
//			}
//		}
//	}
}
