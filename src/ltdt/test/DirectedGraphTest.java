package ltdt.test;

import ltdt.graph.Graph;
import ltdt.graph.impl.DirectedGraph;

public class DirectedGraphTest {
	public static void main(String[] args) throws Exception {
		Graph graph = new DirectedGraph(6);
		graph.addEdge(1, 2);
		graph.addEdge(3, 2);
		graph.addEdge(4, 1);
		graph.addEdge(4, 3);
		graph.addEdge(4, 5);
		graph.addEdge(5, 6);
		graph.printAdjacencyMatrix();
		graph.printTotalDergee(2);
		System.out.println("Total edges: " + graph.getTotalEdges());
		System.out.println("Number of connected elements: " + graph.countConnectedElements());
		System.out.println("Is connected graph: " + graph.isConnectedGraph());
		System.out.println("Has euler path: " + graph.hasEulerPath());
		System.out.println("Has euler cycle: " + graph.hasEulerCycle());
		System.out.println("=========");
		
		Graph graph2 = new DirectedGraph(5);
		graph2.addEdge(1, 2);
		graph2.addEdge(3, 2);
		graph2.addEdge(4, 3);
		System.out.println("Is child: " + graph2.isChildGraph(graph));
		System.out.println("=========");
		
		Graph graph3 = new DirectedGraph(5);
		graph3.addEdge(2, 1);
		graph3.addEdge(1, 3);
		graph3.addEdge(3, 2);
		graph3.addEdge(1, 4);
		graph3.addEdge(4, 5);
		graph3.addEdge(5, 1);
		System.out.println("Has euler cycle: " + graph3.hasEulerCycle());
		System.out.println("Hash euler path: " + graph3.hasEulerPath());
		graph3.findEulerCycle(1).forEach(vex -> System.out.print(vex + " "));
		System.out.println("\n=========");
		
		Graph graph4 = new DirectedGraph(4);
		graph4.addEdge(1, 2);
		graph4.addEdge(2, 4);
		graph4.addEdge(3, 1);
		graph4.addEdge(4, 3);
		graph4.addEdge(3, 2);
		System.out.println("Has euler path: " + graph4.hasEulerPath());
		graph4.findEulerPath(1).forEach(vex -> System.out.print(vex + " "));
	}
}
