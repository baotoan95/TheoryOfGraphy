package ltdt.test;

import ltdt.graph.Graph;
import ltdt.graph.impl.UndirectedGraph;
import ltdt.tree.Tree;

public class TreeTest {
	public static void main(String[] args) throws Exception {
		Graph graph = new UndirectedGraph(9);
		graph.addEdge(1, 2, 8);
		graph.addEdge(2, 3, 9);
		graph.addEdge(3, 4, 4);
		graph.addEdge(4, 5, 6);
		graph.addEdge(5, 7, 5);
		graph.addEdge(7, 9, 1);
		graph.addEdge(7, 8, 11);
		graph.addEdge(1, 6, 3);
		graph.addEdge(6, 7, 10);
		graph.addEdge(2, 6, 7);
		graph.addEdge(2, 5, 12);
		graph.addEdge(3, 5, 18);
		graph.printAdjacencyMatrix();
		Tree tree = new Tree(graph);
		tree.findKruskal().printAdjacencyMatrix();
		tree.findPrim().printAdjacencyMatrix();
	}
}
