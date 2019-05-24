package main;


import java.util.ArrayList;
import java.util.LinkedList;
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
}
