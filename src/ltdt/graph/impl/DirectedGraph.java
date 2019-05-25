package ltdt.graph.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import ltdt.graph.Graph;

public class DirectedGraph extends Graph {

	public DirectedGraph(int numOfVexs) {
		super(numOfVexs);
	}

	@Override
	public void addEdge(int src, int dist) throws Exception {
		validateNumOfVexs(src, dist);
		// Just go one way
		adjMatrix[src - 1][dist - 1] = 1;
	}

	@Override
	public void addEdge(int src, int dist, int weight) throws Exception {
		validateNumOfVexs(src, dist);
		// Just go one way
		adjMatrix[src - 1][dist - 1] = weight;
	}

	@Override
	public void removeEdge(int src, int dist) throws Exception {
		validateNumOfVexs(src, dist);
		adjMatrix[src - 1][dist - 1] = 0;
	}

	@Override
	public void printTotalDergee(int vex) {
		System.out.println("Total in degree of " + vex + ": " + getTotalInDegree(vex));
		System.out.println("Total out degree of " + vex + ": " + getTotalOutDegree(vex));
	}
	
	public int getTotalOutDegree(int vex) {
		int totalOutDegree = 0;
		for (int i = 0; i < adjMatrix.length; i++) {
			if(adjMatrix[vex - 1][i] != 0) {
				totalOutDegree++;
			}
		}
		return totalOutDegree;
	}
	
	public int getTotalInDegree(int vex) {
		int totalInDegree = 0;
		for (int i = 0; i < adjMatrix.length; i++) {
			if(adjMatrix[i][vex - 1] != 0) {
				totalInDegree++;
			}
		}
		return totalInDegree;
	}

	@Override
	public int getTotalEdges() {
		int totalEdges = 0;
		// Loop each vex (start from 1)
		for (int i = 1; i <= adjMatrix.length; i++) {
			totalEdges += getTotalInDegree(i) + getTotalOutDegree(i);
		}
		return totalEdges / 2;
	}

	@Override
	public List<Integer> BFS(int startVex) {
		return null;
	}

	@Override
	public List<Integer> DFS(int startVex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int countConnectedElements() {
		Graph undirectedGraph = convertToUndirectedGraph();
		return undirectedGraph.countConnectedElements();
	}

	@Override
	public boolean hasEulerPath() {
		Graph undirectedGraph = convertToUndirectedGraph();
		boolean hasEulerPath = undirectedGraph.isConnectedGraph();
		int countVexHasOddDegree = 0;
		for (int i = 1; i <= adjMatrix.length; i++) {
			if(getTotalInDegree(i) % 2 != 0 && getTotalOutDegree(i) % 2 != 0) {
				countVexHasOddDegree++;
			}
		}
		return hasEulerPath && (countVexHasOddDegree == 2);
	}

	@Override
	public boolean hasEulerCycle() {
		Graph undirectedGraph = convertToUndirectedGraph();
		boolean isWeakConnected = undirectedGraph.isConnectedGraph();
		for (int i = 1; i <= adjMatrix.length; i++) {
			if(getTotalInDegree(i) != getTotalOutDegree(i)) {
				return false;
			}
		}
		return isWeakConnected;
	}
	
	public Graph convertToUndirectedGraph() {
		Graph graph = new UndirectedGraph(adjMatrix.length);
		int[][] adjMatrixMask = new int[adjMatrix.length][adjMatrix.length];
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix.length; j++) {
				if(adjMatrix[i][j] != 0) {
					adjMatrixMask[i][j] = adjMatrixMask[j][i] = adjMatrix[i][j];
				}
			}
		}
		graph.setAdjMatrix(adjMatrixMask);
		return graph;
	}

	/**
	 * The same with findEulerCycle
	 */
	@Override
	public List<Integer> findEulerPath(int startVex) throws Exception {
		if(!hasEulerPath()) {
			throw new Exception("There is no Euler path on this graph");
		}
		if(getTotalInDegree(startVex) != getTotalOutDegree(startVex)) {
			throw new Exception("Please start from vertex that has the same in degree and out degree");
		}
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
	
	@Override
	public int getTotalDegree(int vex) {
		return getTotalInDegree(vex) + getTotalOutDegree(vex);
	}

}
