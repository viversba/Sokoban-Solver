package main;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Search {

	public enum Type{
		
		BFS,
		DFS,
		IDFS,
		AStar,
		Greedy
	}
	
	public Type type;
	public Node startValue;
	public Map map;
	
	public Search() {this(null, null, null);}
	
	public Search(Type type, Node startValue, Map map) {
		
		this.startValue = startValue;
		this.type = type;
		this.map = map;
		
		Start();
	}
	
	public void SetHeuristics() {
		
	}


	public void Start() {
		
		switch(type) {
			case BFS:
				BFS(startValue);
				break;
			case DFS:
				DFS(startValue);
				break;
			case AStar:
				AEstrella(startValue, new HungarianHeuristic());
				break;
		}
	}
	
	void BFS(Node parentNode) {

        Queue<Node> queue = new LinkedList<Node>();
        queue.add(parentNode);
        boolean found = false;
        int count = 0;
        while (queue.size() > 0) {
            Node node = queue.poll();
            if (map.GoalTest(node.state.boxPositions)) {
            	System.out.println();
            	System.out.println(node.PrintPath());
                System.out.println("Solution found at iteration number: " + count);
                System.out.println("Solution has depth: " + node.GetCount());
                found = true;
                return;
            }
            ArrayList<Node> children = node.Expand();
            if (children.size() > 0) {
                for (Node child : children) {
                    queue.add(child);
                }
            }
            count++;
        }
        
        System.out.println();
        if (!found) {
        	System.out.println("Solution not found");
        }
    }
	
	void DFS(Node parentNode) {

		LinkedList<Node> queue = new LinkedList<>();
		queue.add(parentNode);
		boolean found = false;
		int count = 0;
		while (queue.size() > 0) {
			Node node = queue.pollFirst();
			if (map.GoalTest(node.state.boxPositions)) {
				System.out.println();
				System.out.println(node.PrintPath());
				System.out.println("Solution found at iteration number: " + count);
				System.out.println("Solution has depth: " + node.GetCount());
				found = true;
				return;
			}
			ArrayList<Node> children = node.Expand();
			if (children.size() > 0) {
				for (Node child : children) {
					queue.addLast(child);
				}
			}
			count++;
		}

		System.out.println();
		if (!found) {
			System.out.println("Solution not found");
		}
	}

	void AEstrella(Node parentNode, Heuristic h) {

		PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
		int cost = 0;
		parentNode.setCost(cost);
		pq.add(parentNode);
		boolean found = false;
		int count = 0;
		while (pq.size() > 0) {
			Node node = pq.poll();
//			map.PrintWithState(node );
			if (map.GoalTest(node.state.boxPositions)) {
				System.out.println();
				System.out.println(node.PrintPath());
				System.out.println("Solution found at iteration number: " + count);
				System.out.println("Solution has depth: " + node.GetCount());
				found = true;
				return;
			}
			ArrayList<Node> children = node.Expand();
			for (Node child : children) {
				child.setCost(node.getCost()+1);
				int priority = child.getCost() +  h.calculatedHeuristic(child.getState().getCostMatriz());
				child.setPriority(priority);
				pq.add(child);
			}
			count++;
		}

		System.out.println();
		if (!found) {
			System.out.println("Solution not found");
		}
	}
}
