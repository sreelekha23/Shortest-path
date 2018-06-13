package daaproj;

import java.io.*;
import java.util.*;

public class Dijkstra {
	private static final Graph.Edge[] GRAPH = { 
			new Graph.Edge("321081", "321101", 6),
			new Graph.Edge("321091", "321101", 10), 
			new Graph.Edge("321081", "321131", 1),
			new Graph.Edge("321091", "321111", 15), 
			new Graph.Edge("321101", "321111", 1),
			new Graph.Edge("321111", "321121", 6),
			new Graph.Edge("321131", "321091", 6),
			new Graph.Edge("321131", "321041", 4)

				};

	private static String STARTNode;
	private static String ENDNode;

	public static void main(String[] args) {

//		Graph gr = new Graph(GRAPH);
//		STARTNode = "32101";
//		ENDNode = "32110";
//
//		gr.dijflow(STARTNode);
//		gr.displayPaths(ENDNode);
	}

	public int getDistance(String START, String END) {
		Graph g = new Graph(GRAPH);
		g.dijflow(START);
		int distance = g.displayPaths(END);
		return distance;
	}

	static int[] addElement(int[] a, int e) {
		a = Arrays.copyOf(a, a.length + 1);
		a[a.length - 1] = e;
		return a;
	}

	public static int[] readFiles(String file) {
		try {
			File f = new File(file);
			Scanner s = new Scanner(f);
			int ctr = 0;
			while (s.hasNextInt()) {
				ctr++;
				s.nextInt();
			}
			int[] arr = new int[ctr];
			Scanner s1 = new Scanner(f);
			for (int i = 0; i < arr.length; i++)
				arr[i] = s1.nextInt();
			return arr;
		} catch (Exception e) {
			return null;
		}
	}
}

class Graph {
	private final Map<String, Vertex> graph;

	public static class Edge {
		public final String v1, v2;
		public final int dist;

		public Edge(String v1, String v2, int dist) {
			this.v1 = v1;
			this.v2 = v2;
			this.dist = dist;
		}
	}

	public static class Vertex implements Comparable<Vertex> {
		public final String name;
		public int dist = Integer.MAX_VALUE;
		public Vertex previous = null;
		public final Map<Vertex, Integer> neighbours = new HashMap<>();

		public Vertex(String name) {
			this.name = name;
		}

		private int displayPaths() {
			if (this == this.previous) {
				System.out.printf("%s", this.name);
			} else if (this.previous == null) {
				System.out.printf("%s(unreached)", this.name);
			} else {
				this.previous.displayPaths();

				System.out.printf(" -> %s(%d)", this.name, this.dist);
				return this.dist;
			}
			return 0;
		}

		public int compareTo(Vertex other) {
			if (dist == other.dist)
				return name.compareTo(other.name);

			return Integer.compare(dist, other.dist);
		}

		@Override
		public String toString() {
			return "(" + name + ", " + dist + ")";
		}
	}

	public Graph(Edge[] edges) {
		graph = new HashMap<>(edges.length);
		for (Edge e : edges) {
			if (!graph.containsKey(e.v1))
				graph.put(e.v1, new Vertex(e.v1));
			if (!graph.containsKey(e.v2))
				graph.put(e.v2, new Vertex(e.v2));
		}
		for (Edge e : edges) {
			graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
		}
	}

	public void dijflow(String startName) {
		if (!graph.containsKey(startName)) {
			System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
			return;
		}
		final Vertex source = graph.get(startName);
		NavigableSet<Vertex> q = new TreeSet<>();
		for (Vertex v : graph.values()) {
			v.previous = v == source ? source : null;
			v.dist = v == source ? 0 : Integer.MAX_VALUE;
			q.add(v);
		}

		dijflow(q);
	}

	private void dijflow(final NavigableSet<Vertex> q) {
		Vertex u, v;
		while (!q.isEmpty()) {

			u = q.pollFirst();
			if (u.dist == Integer.MAX_VALUE)
				break;
			for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
				v = a.getKey();
				final int alternateDist = u.dist + a.getValue();
				if (alternateDist < v.dist) {
					q.remove(v);
					v.dist = alternateDist;
					v.previous = u;
					q.add(v);
				}
			}
		}
	}

	public int displayPaths(String endName) {
		if (!graph.containsKey(endName)) {
			System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
			return 1;
		}

		int x = graph.get(endName).displayPaths();
		System.out.println();
		return x;
	}

	public void printAllPaths() {
		for (Vertex v : graph.values()) {
			v.displayPaths();
			System.out.println();
		}
	}
}