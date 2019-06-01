package main;


import java.util.*;

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

	public void Start() {
		
		switch(type) {
			case BFS:
				BFS(startValue);
				break;
			case DFS:
				DFS(startValue);
				break;
			case AStar:
				AStar(startValue, new HungarianHeuristic());
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

	void AStar(Node parentNode, Heuristic h) {

		PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
		HashSet<String> set = new HashSet<>();
		int cost = 0;
		parentNode.setCost(cost);
		pq.add(parentNode);

		int count = 0;
		while (pq.size() > 0) {
			Node node = pq.poll();
			set.add(node.state.toString());
			//node.getState().Print();
			if (map.GoalTest(node.state.boxPositions)) {
				System.out.println();
				System.out.println(node.PrintPath());
				System.out.println("Solution found at iteration number: " + count);
				System.out.println("Solution has depth: " + node.GetCount());
				return;
			}

			ArrayList<Node> children = node.Expand();
			for (Node child : children) {
				if(!set.contains(child.state.toString())){
					int priority = child.getCost() +  h.calculatedHeuristic(child.getState().getCostMatriz());
					int minDistance = Utils.CalculatedMinDistance(child.getState().playerPos,child.getState().boxPositions);
					child.setPriority(priority+minDistance);
					pq.add(child);
				}

			}
			count++;
		}

		System.out.println();
		System.out.println("Solution not found");

	}
}
