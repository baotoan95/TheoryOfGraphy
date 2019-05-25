package ltdt.graph.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import ltdt.graph.Graph;

public class UndirectedGraph extends Graph {

	public UndirectedGraph(int numOfVexs) {
		super(numOfVexs);
	}

	@Override
	public void addEdge(int src, int dist) throws Exception {
		validateNumOfVexs(src, dist);
		// Can go two way
		adjMatrix[src - 1][dist - 1] = 1;
		adjMatrix[dist - 1][src - 1] = 1;
	}

	@Override
	public void addEdge(int src, int dist, int weight) throws Exception {
		validateNumOfVexs(src, dist);
		// Can go two way
		adjMatrix[src - 1][dist - 1] = weight;
		adjMatrix[dist - 1][src - 1] = weight;
	}

	@Override
	public void removeEdge(int src, int dist) throws Exception {
		validateNumOfVexs(src, dist);
		// Can go two way
		adjMatrix[src - 1][dist - 1] = 0;
		adjMatrix[dist - 1][src - 1] = 0;
	}

	@Override
	public void printTotalDergee(int vex) {
		System.out.println("Total degree of " + vex + ": " + getTotalDegree(vex));
	}
	
	public int getTotalDegree(int vex) {
		int totalDegree = 0;
		for (int i = 0; i < adjMatrix.length; i++) {
			if(adjMatrix[vex - 1][i] != 0) {
				totalDegree++;
			}
		}
		return totalDegree;
	}

	@Override
	public int getTotalEdges() {
		int totalEdges = 0;
		for (int i = 1; i <= adjMatrix.length; i++) {
			totalEdges += getTotalDegree(i);
		}
		return totalEdges / 2;
	}

	@Override
	public List<Integer> BFS(int startVex) {
		int visitVex = startVex - 1;
		List<Integer> visitedList = new ArrayList<Integer>(); 
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(visitVex);
		while (queue.size() > 0) {
			int visitedIndex = queue.remove();
			visitedList.add(visitedIndex + 1);
			for (int i = 0; i < adjMatrix.length; i++) {
				if(adjMatrix[visitedIndex][i] != 0 && !visitedList.contains(i + 1) && !queue.contains(i)) {
					queue.add(i);
				}
			}
		}
		return visitedList;
	}

	@Override
	public List<Integer> DFS(int startVex) {
		int visitVex = startVex - 1;
		List<Integer> visitedList = new ArrayList<Integer>(); 
		Stack<Integer> stack = new Stack<Integer>();
		stack.add(visitVex);
		while (stack.size() > 0) {
			int visitedIndex = stack.pop();
			visitedList.add(visitedIndex + 1);
			for (int i = 0; i < adjMatrix.length; i++) {
				if(adjMatrix[visitedIndex][i] != 0 && !visitedList.contains(i + 1) && !stack.contains(i)) {
					stack.push(i);
				}
			}
		}
		return visitedList;
	}

	@Override
	public int countConnectedElements() {
		int count = 0;
		List<Integer> visitedVexs = new ArrayList<>();
		for (int i = 1; i <= adjMatrix.length; i++) {
			if(!visitedVexs.contains(i)) { // Vex start from 1
				visitedVexs.addAll(DFS(i));
				count++;
			}
		}
		return count;
	}

	@Override
	public boolean hasEulerPath() {
		int countVexHasOddDegree = 0;
		for (int i = 1; i <= adjMatrix.length; i++) {
			if(getTotalDegree(i) % 2 != 0) {
				countVexHasOddDegree++;
			}
		}
		return isConnectedGraph() && countVexHasOddDegree == 2;
	}

	@Override
	public boolean hasEulerCycle() {
		boolean hasEulerCycle = isConnectedGraph();
		for (int i = 1; i <= adjMatrix.length; i++) {
			if(getTotalDegree(i) % 2 != 0) {
				return false;
			}
		}
		return hasEulerCycle;
	}

	@Override
	public List<Integer> findEulerPath(int startVex) throws Exception {
		Graph unGraph = new UndirectedGraph(adjMatrix.length);
		unGraph.setAdjMatrix(adjMatrix);
		
		if(!unGraph.hasEulerPath()) {
			throw new Exception("There is no Euler path on this graph");
		}
		// Check if it has odd degree vertex then must be start at one of them
		for (int i = 1; i <= adjMatrix.length; i++) {
			if(unGraph.getTotalDegree(i) % 2 != 0 && unGraph.getTotalDegree(startVex) % 2 == 0) {
				throw new Exception("You have to start at odd degree vertex (e.i vertex " + i + ")");
			}
		}
		// Find path
		List<Integer> visitedVertexes = new ArrayList<>();
		visitedVertexes.add(startVex);
		// Finish when go through all of edges
		int numOfEdges = unGraph.getTotalEdges();
		while (visitedVertexes.size() <= numOfEdges) {
			Queue<Integer> nextVertexes = unGraph.findNextVertexes(startVex);
			while (!nextVertexes.isEmpty()) {
				int nextVertex = nextVertexes.poll();
				if(isValidNextEdge(startVex, nextVertex, unGraph.getAdjMatrix())) {
					visitedVertexes.add(nextVertex);
					// Remove visited edge from the graph
					unGraph.removeEdge(startVex, nextVertex);
					// Move to next vertex
					startVex = nextVertex;
					// Found the needed edge
					break;
				}
			}
		}
		return visitedVertexes;
	}
	
	private boolean isValidNextEdge(int src, int dist, int[][] adjTemp) throws Exception {
		Graph unGraph = new UndirectedGraph(adjTemp.length);
		unGraph.setAdjMatrix(adjTemp);
		// If there is only one edge from src. Pick it!
		if(unGraph.getTotalDegree(src) == 1) {
			return true;
		}
		// If graph has more than one edge, choose non-bridge edge
		int countVisitedVertexesFromSrcToDist1 = unGraph.DFS(src).size();
		// Try to remove src-dist edge and count again
		unGraph.removeEdge(src, dist);
		int countVisitedVertexesFromSrcToDist2 = unGraph.DFS(src).size();
		// Add removed edge back to the graph
		unGraph.addEdge(src, dist);
		return countVisitedVertexesFromSrcToDist1 > countVisitedVertexesFromSrcToDist2 ? false : true;
	}

}
