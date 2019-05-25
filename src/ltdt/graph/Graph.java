package ltdt.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import ltdt.graph.impl.UndirectedGraph;

public abstract class Graph {
	protected int[][] adjMatrix;
	private final int numOfVexs;
	
	public Graph(int numOfVexs) {
		this.numOfVexs = numOfVexs;
		this.adjMatrix = new int[numOfVexs][numOfVexs];
	}
	
	public abstract void addEdge(int src, int dist) throws Exception;

	public abstract void addEdge(int src, int dist, int weight) throws Exception;

	public abstract void removeEdge(int src, int dist) throws Exception;
	
	public abstract void printTotalDergee(int vex);
	public abstract int getTotalDegree(int vex);
	
	public abstract int getTotalEdges();
	
	public abstract List<Integer> BFS(int startVex);
	
	public abstract List<Integer> DFS(int startVex);
	public List<Integer> DFS(int startVex, Graph graph) {
		return graph.DFS(startVex);
	}
	
	public abstract int countConnectedElements();
	
	public abstract boolean hasEulerPath();
	public abstract boolean hasEulerCycle();
	
	public abstract List<Integer> findEulerPath(int startVex) throws Exception;
	public List<Integer> findEulerCycle(int startVex) throws Exception {
		if(!hasEulerCycle()) {
			throw new Exception("There is no Euler cycle on this graph");
		}
		// Use undirected graph is the same with directed graph
		// We just do this because we don't want to change adjMatrix variable (in global)
		// For prevent it affected on other methods
		Graph unGraph = new UndirectedGraph(adjMatrix.length);
		unGraph.setAdjMatrix(adjMatrix);
		
		Stack<Integer> visitVertexes = new Stack<>();
		List<Integer> path = new ArrayList<>();
		visitVertexes.push(startVex);
		while (!visitVertexes.isEmpty()) {
			int currentVertex = visitVertexes.peek();
			// Find next vertex
			Queue<Integer> neighborVertexes = unGraph.findNextVertexes(currentVertex);
			if(!neighborVertexes.isEmpty()) {
				// Choose one of next vertexes
				int nextVertex = neighborVertexes.peek();
				// Push to visit stack
				visitVertexes.push(nextVertex);
				// Remove edge
				unGraph.removeEdge(currentVertex, nextVertex);
			} else { // If current vertex has no longer neighborhood
				// Add it to path
				path.add(currentVertex);
				// Remove it from visit stack
				visitVertexes.pop();
			}
		}
		// Invert result list
		Collections.reverse(path);
		return path;
	}
	
	public boolean isConnectedGraph() {
		return countConnectedElements() == 1;
	}
	
	public Queue<Integer> findNextVertexes(int vertex) {
		Queue<Integer> nextVertexes = new LinkedList<>();
		for (int i = 0; i < adjMatrix.length; i++) {
			if(adjMatrix[vertex - 1][i] != 0) {
				nextVertexes.add(i + 1);
			}
		}
		return nextVertexes;
	}
	
	public boolean isChildGraph(Graph parent) throws Exception {
		if(adjMatrix.length > parent.adjMatrix.length) {
			throw new Exception("Child can't greater than parent");
		}
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix.length; j++) {
				if(adjMatrix[i][j] > parent.adjMatrix[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void validateNumOfVexs(int src, int dist) throws Exception {
		if(src > this.getNumOfVexs() || dist > this.getNumOfVexs() || src <= 0 || dist <= 0) {
			throw new Exception("Out of vex. Vex must be start at 1");
		}
	}
	
	public void printAdjacencyMatrix() {
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix.length; j++) {
				System.out.print(this.adjMatrix[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	public int getNumOfVexs() {
		return numOfVexs;
	}
	
	public int[][] getAdjMatrix() {
		return adjMatrix;
	}
	
	public void setAdjMatrix(int[][] adjMatrix) {
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix.length; j++) {
				this.adjMatrix[i][j] = adjMatrix[i][j];
			}
		}
		
	}
}
