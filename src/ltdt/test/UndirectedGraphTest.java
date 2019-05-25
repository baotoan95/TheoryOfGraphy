package ltdt.test;

import ltdt.graph.Graph;
import ltdt.graph.impl.UndirectedGraph;

public class UndirectedGraphTest {
	public static void main(String[] args) throws Exception {
		Graph graph = new UndirectedGraph(6);
		graph.addEdge(1, 2);
		graph.addEdge(2, 3);
		graph.addEdge(3, 4);
		graph.addEdge(4, 1);
		graph.addEdge(4, 5);
		graph.addEdge(5, 6);
		graph.addEdge(3, 6);
		
		graph.printTotalDergee(4);
		System.out.println("Total edges: " + graph.getTotalEdges());
		System.out.print("BSF: ");
		graph.BFS(1).forEach(vex -> System.out.print(vex + " "));
		System.out.print("\nDSF: ");
		graph.DFS(1).forEach(vex -> System.out.print(vex + " "));
		System.out.println("\nNumber of connected elements: " + graph.countConnectedElements());
		System.out.println("Is connected graph: " + graph.isConnectedGraph());
		System.out.println("Has euler path: " + graph.hasEulerPath());
		System.out.println("Has euler cycle: " + graph.hasEulerCycle());
		System.out.println("Euler path: ");
		graph.findEulerPath(4).forEach(vex -> System.out.print(vex + " "));
		System.out.println("\n===========");
		
		Graph h5 = new UndirectedGraph(7);
		h5.addEdge(1, 2);
		h5.addEdge(1, 3);
		h5.addEdge(3, 2);
		h5.addEdge(3, 5);
		h5.addEdge(5, 4);
		h5.addEdge(4, 3);
		h5.addEdge(5, 7);
		h5.addEdge(7, 6);
		h5.addEdge(6, 5);
		System.out.println("Has Euler cycle: " + h5.hasEulerCycle());
		h5.findEulerCycle(1).forEach(vex -> System.out.print(vex + " "));
		System.out.println("\n===========");
		
		Graph graph2 = new UndirectedGraph(5);
		graph2.addEdge(1, 4);
		graph2.addEdge(4, 5);
		graph2.printAdjacencyMatrix();
		System.out.println("Is child: " + graph2.isChildGraph(graph));
		graph2.BFS(1).forEach(vex -> System.out.print(vex + " "));
	}
}
