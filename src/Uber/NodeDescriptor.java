package Uber;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class NodeDescriptor {

	static boolean _debug = true;

	public static void main(String... strings) {

		Node root = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		root.addAdj(node2, node3);
		node2.addAdj(root, node3);
		node3.addAdj(root, node2, node4);
		node4.addAdj(node3);

		// printAdj(root);
		Map<Integer, Node> idToNode = new HashMap<>();
		// map in -> lit of ints for links
		DFS(root);
		copyIntoMap(root, idToNode);
		copyEdges(idToNode);
	}

	public static void copyEdges(Map<Integer, Node> idToNode) {
		for (Entry<Integer, Node> a : idToNode.entrySet()) {

		}
	}

	public static void copyIntoMap(Node root, Map<Integer, Node> idToNode) {
		if (!root._visited) {
			idToNode.put(root._value, root);
		}
		root._visited = true;
		for (Node adj : root._adj) {
			if (!adj._visited) {
				DFS(adj);
			}
		}
	}

	public static void DFS(Node root) {
		if (!root._visited) {
			printAdj(root);
		}
		root._visited = true;
		for (Node adj : root._adj) {
			if (!adj._visited) {
				// printAdj(adj);
				// adj._visited = true;
				DFS(adj);
			}
		}
	}

	public static void printAdj(Node iNode) {
		print(iNode._value + ": ");
		for (Node adj : iNode._adj) {
			print(adj._value + " ");
		}
		println("");
	}

	static class Node {
		int _value;
		boolean _visited = false;
		Set<Node> _adj = new HashSet<>();

		public Node(int a) {
			_value = a;
		}

		public void addAdj(Node... nodes) {
			for (Node node : nodes) {
				_adj.add(node);
			}
		}

		public Node clone(Node iNode) {
			return new Node(iNode._value);
		}
	}

	private static void debug(Object obj) {
		if (_debug) {
			System.err.print(obj.toString());
		}
	}

	private static void debugln(Object obj) {
		if (_debug) {
			System.err.println(obj.toString());
		}
	}

	private static void print(Object obj) {
		System.out.print(obj.toString());
	}

	private static void println(Object obj) {
		System.out.println(obj.toString());
	}

}
