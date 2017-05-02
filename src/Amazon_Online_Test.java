import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

public class Amazon_Online_Test {

	static boolean _debug = false;

	public static void main(String[] args) {

		if (args.length != 0) {
			System.err.println("in debug");
			_debug = true;
			// _fromEclipse = true;
			String aInput = "";
			for (String data : getData()) {
				aInput += data + "\r\n";
			}
			System.setIn(new ByteArrayInputStream(aInput.getBytes()));
		}

		doIt();

	}

	// debug method - discard!
	static private Vector<String> getData() {
		Vector<String> aInput = new Vector<String>();

		aInput.add("3");

		return aInput;
	}

	// METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
	public static int totalScore(String[] blocks, int n) {
		int totalScore = 0;
		List<Integer> scores = new ArrayList<>();
		for (String str : blocks) {
			boolean isInt = false;
			int intVal = 0;
			try {
				intVal = Integer.parseInt(str);
				isInt = true;
			} catch (Exception e) {
			}
			if (isInt) { // ********* CASE DIGIT ***********
				scores.add(intVal);
			} else if (str.equals("X")) { // ********* CASE X ***********
				if (scores.size() >= 1) {
					int lastVal = scores.get(scores.size() - 1);
					scores.add(lastVal * 2);
				} else
					scores.add(0);
			} else if (str.equals("+")) { // ********* CASE + ***********
				int sumOfLastTwo = 0; // default
				if (scores.size() == 1) {
					sumOfLastTwo = scores.get(scores.size() - 1); // by itself;
				} else if (scores.size() >= 2) {
					// debugln("adding " + scores.get(scores.size() - 1) + " and " +
					// scores.get(scores.size() - 2));
					sumOfLastTwo = scores.get(scores.size() - 1) + scores.get(scores.size() - 2);
				}
				scores.add(sumOfLastTwo);
			} else if (str.equals("Z")) { // ********* CASE Z ***********
				if (scores.size() >= 1) {
					scores.remove(scores.size() - 1);
				}
			}
			// debugln("score added " + scores.get(scores.size() - 1));
		}

		for (int score : scores) {
			totalScore += score;
		}

		return totalScore;
	}
	// METHOD SIGNATURE ENDS

	public static class Node {
		Node right;
		Node left;
		Node parent;
		int val;
		boolean visited = false; // cheap BFS flag ;)
		int dist = 0;

		public Node(int iVal) {
			val = iVal;
		}

		@Override
		public String toString() {
			return "val:" + val + ";dist:" + dist;
		}
	}

	public static Node insertInBinarySearch(Node root, int val, int node1, int node2, Param p) {
		if (root == null) {
			Node node = new Node(val);
			// remember from/to nodes
			if (val == node1) {
				// debugln("remembering " + p.n1);
				p.n1 = node;
			}
			if (val == node2)
				p.n2 = node;
			return node;
		} else {
			Node cur;
			if (val <= root.val) {
				cur = insertInBinarySearch(root.left, val, node1, node2, p);
				root.left = cur;
				// debugln("inserting " + val + " on left of " + root.val);
			} else {
				cur = insertInBinarySearch(root.right, val, node1, node2, p);
				root.right = cur;
				// debugln("inserting " + val + " on right of " + root.val);
			}
			// debugln("attaching " + cur.val + " to parent " + root.val);
			cur.parent = root;

			return root;
		}
	}

	public static void buildBst(int[] values, int n, int node1, int node2, Param p) {
		Node node = null;
		for (int i = 0; i < n; i++) {
			int val = values[i];
			if (i == 0) { // define Root
				node = new Node(val);
			} else {
				// debugln("about to insert " + val);
				insertInBinarySearch(node, val, node1, node2, p);
			}
		}
	}

	static class Param { // return mode than one new Ref ;)
		Node n1;
		Node n2;
	}

	public static int bstDistance(int[] values, int n, int node1, int node2) {
		if (values.length == 0 || n != values.length)
			return -1;

		if (node1 == node2)
			return 0;

		// build tree first
		Param p = new Param();
		buildBst(values, n, node1, node2, p);

		if (p.n1 == null || p.n2 == null)
			return -1;

		// Tree is built! now let's do a usual BFS between n1 and n2
		Queue<Node> toProcess = new LinkedList<>();
		p.n1.visited = true;
		toProcess.offer(p.n1);
		while (!toProcess.isEmpty()) {
			Node cur = toProcess.poll();
			if (cur == p.n2) { // found it!
				return p.n2.dist; // (cheap) XXXX best place for this?
			}
			if (cur.left != null && !cur.left.visited) {
				cur.left.visited = true;
				cur.left.dist = cur.dist + 1;
				debug("adding left " + cur.left);
				toProcess.offer(cur.left);
			}
			if (cur.right != null && !cur.right.visited) { // XXXX avoid copy-paste?
				cur.right.visited = true;
				cur.right.dist = cur.dist + 1;
				debugln("adding right " + cur.right);
				toProcess.offer(cur.right);
			}
			if (cur.parent != null && !cur.parent.visited) { // XXXX avoid copy-paste?
				cur.parent.visited = true;
				cur.parent.dist = cur.dist + 1;
				debugln("adding parent " + cur.parent);
				toProcess.offer(cur.parent);
			}
		}

		return -1;
	}

	private static void doIt() {
		Scanner scan = new Scanner(System.in);

		int n = scan.nextInt();
		scan.close();

		// expects 27
		// String[] blocks = { "5", "-2", "4", "Z", "X", "9", "+", "+" };
		// println(totalScore(blocks, blocks.length));

		// int[] nodes = { 5, 6, 3, 1, 2, 4 };
		int[] nodes = { 5, 6, 3, 3, 2, 4 };
		int n1 = 2; // 2
		int n2 = 6; // 4
		println(bstDistance(nodes, nodes.length, n1, n2));

	}

	private static void debug(Object obj) {
		System.err.print(obj.toString());
	}

	private static void debugln(Object obj) {
		System.err.println(obj.toString());
	}

	private static void print(Object obj) {
		System.out.print(obj.toString());
	}

	private static void println(Object obj) {
		System.out.println(obj.toString());
	}
}
