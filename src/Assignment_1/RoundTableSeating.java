package Assignment_1;

import java.util.*;

public class RoundTableSeating {
	static int[][] dislikes = { { 0, 68, 55, 30, 82, 48, 33, 10, 76, 43 }, { 68, 0, 90, 11, 76, 20, 55, 17, 62, 99 },
			{ 55, 90, 0, 70, 63, 96, 51, 90, 88, 64 }, { 30, 11, 70, 0, 91, 86, 78, 99, 53, 92 },
			{ 82, 76, 63, 91, 0, 43, 88, 53, 42, 75 }, { 48, 20, 96, 86, 43, 0, 63, 97, 37, 26 },
			{ 33, 55, 51, 78, 88, 63, 0, 92, 87, 81 }, { 10, 17, 90, 99, 53, 97, 92, 0, 81, 78 },
			{ 76, 62, 88, 53, 42, 37, 87, 81, 0, 45 }, { 43, 99, 64, 92, 75, 26, 81, 78, 45, 0 } };

	static String[] guests = { "Ahmad", "Salem", "Ayman", "Hani", "Kamal", "Samir", "Hakem", "Fuad", "Ibrahim",
			"Khalid" };
	static int numGuests = guests.length;

	public static void main(String[] args) {
		Graph graph = new Graph(numGuests);
		// Construct graph with dislike percentages as weights
		for (int i = 0; i < numGuests; i++) {
			for (int j = i + 1; j < numGuests; j++) {
				int dislikePercentage = dislikes[i][j];
				graph.addEdge(i, j, dislikePercentage);
			}
		}

		// Find seating arrangements using different algorithms
		List<String> arrangementGreedy = findSeatingArrangementGreedy(graph);
		List<String> arrangementAStar = findSeatingArrangementAStar(graph);
		List<String> arrangementUCS = findSeatingArrangementUCS(graph);

		// Print the seating arrangements with total cost
		System.out.println("Greedy Seating Arrangement: " + arrangementGreedy);
		System.out.println("Total Cost: " + calculateTotalCost(arrangementGreedy, graph, "Greedy"));
		System.out.println("\nA* Seating Arrangement: " + arrangementAStar);
		System.out.println("Total Cost: " + calculateTotalCost(arrangementAStar, graph, "AStar"));
		System.out.println("\nUCS Seating Arrangement: " + arrangementUCS);
		System.out.println("Total Cost: " + calculateTotalCost(arrangementUCS, graph, "UCS"));
	}

	// Function to calculate the cost based on dislike percentage and cost function
	static int calculateCost(int dislikePercentage, String algorithm) {
		if (algorithm.equals("Greedy")) {
			return dislikePercentage;
		} else if (algorithm.equals("AStar")) {
			return dislikePercentage + (dislikePercentage * dislikePercentage);
		} else if (algorithm.equals("UCS")) {
			return dislikePercentage * dislikePercentage;
		} else {
			return 0;
		}
	}

	// Calculate total cost of seating arrangement
	static int calculateTotalCost(List<String> arrangement, Graph graph, String algorithm) {
		int totalCost = 0;
		for (int i = 0; i < arrangement.size() - 1; i++) {
			String guest1 = arrangement.get(i);
			String guest2 = arrangement.get(i + 1);
			int index1 = Arrays.asList(guests).indexOf(guest1);
			int index2 = Arrays.asList(guests).indexOf(guest2);
			totalCost += calculateCost(graph.adjMatrix[index1][index2], algorithm);
		}
		return totalCost;
	}
	static List<String> findSeatingArrangementUCS(Graph graph) {
	    List<String> seatingArrangement = new ArrayList<>();
	    boolean[] visited = new boolean[graph.V];

	    String startGuest = "Ahmad";
	    seatingArrangement.add(startGuest);
	    visited[Arrays.asList(guests).indexOf(startGuest)] = true;

	    PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));

	    while (seatingArrangement.size() < graph.V) {
	        String currentGuest = seatingArrangement.get(seatingArrangement.size() - 1);
	        int currentIndex = Arrays.asList(guests).indexOf(currentGuest);

	        for (String guest : guests) {
	            int nextIndex = Arrays.asList(guests).indexOf(guest);
	            if (!visited[nextIndex]) {
	                int actualCost = graph.adjMatrix[currentIndex][nextIndex];
	                pq.offer(new Node(guest, actualCost));
	            }
	        }

	        Node minNode = pq.poll();
	        seatingArrangement.add(minNode.guest);
	        visited[Arrays.asList(guests).indexOf(minNode.guest)] = true;
	    }

	    return seatingArrangement;
	}


	static List<String> findSeatingArrangementGreedy(Graph graph) {
	    List<String> seatingArrangement = new ArrayList<>();
	    boolean[] visited = new boolean[graph.V];

	    String startGuest = "Ahmad";
	    seatingArrangement.add(startGuest);
	    visited[Arrays.asList(guests).indexOf(startGuest)] = true;

	    PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));

	    while (seatingArrangement.size() < graph.V) {
	        String currentGuest = seatingArrangement.get(seatingArrangement.size() - 1);
	        int currentIndex = Arrays.asList(guests).indexOf(currentGuest);

	        for (String guest : guests) {
	            int nextIndex = Arrays.asList(guests).indexOf(guest);
	            if (!visited[nextIndex]) {
	                int cost = graph.adjMatrix[currentIndex][nextIndex];
	                pq.offer(new Node(guest, cost));
	            }
	        }

	        Node minNode = pq.poll();
	        seatingArrangement.add(minNode.guest);
	        visited[Arrays.asList(guests).indexOf(minNode.guest)] = true;
	    }

	    return seatingArrangement;
	}


	static List<String> findSeatingArrangementAStar(Graph graph) {
	    List<String> seatingArrangement = new ArrayList<>();
	    boolean[] visited = new boolean[graph.V];

	    String startGuest = "Ahmad";
	    seatingArrangement.add(startGuest);
	    visited[Arrays.asList(guests).indexOf(startGuest)] = true;

	    PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));

	    while (seatingArrangement.size() < graph.V) {
	        String currentGuest = seatingArrangement.get(seatingArrangement.size() - 1);
	        int currentIndex = Arrays.asList(guests).indexOf(currentGuest);

	        for (String guest : guests) {
	            int nextIndex = Arrays.asList(guests).indexOf(guest);
	            if (!visited[nextIndex]) {
	                int actualCost = graph.adjMatrix[currentIndex][nextIndex];
	                int heuristicCost = calculateCost(actualCost, "AStar");
	                int totalCost = actualCost + heuristicCost;
	                pq.offer(new Node(guest, totalCost));
	            }
	        }

	        Node minNode = pq.poll();
	        seatingArrangement.add(minNode.guest);
	        visited[Arrays.asList(guests).indexOf(minNode.guest)] = true;
	    }

	    return seatingArrangement;
	}

	static class Graph {
		int V;
		int[][] adjMatrix;

		public Graph(int V) {
			this.V = V;
			this.adjMatrix = new int[V][V];
		}

		public void addEdge(int u, int v, int weight) {
			adjMatrix[u][v] = weight;
			adjMatrix[v][u] = weight; // Assuming dislike is symmetric
		}
	}

	static class Node {
		String guest;
		int cost;

		Node(String guest, int cost) {
			this.guest = guest;
			this.cost = cost;
		}
	}
}
