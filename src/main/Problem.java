package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.plaf.basic.BasicMenuUI.ChangeHandler;

public class Problem {
	
	Search search;
	State initialState;
	Map map;
	HashSet<State> visited;
	short[] goalPositions;
	
	public void Run(String[] args) throws IOException {
		
		if(args.length != 2) {
			System.out.println("Usage: java Sokoban [-search_type] [input file name]");
			return;
		}

		Search.Type type = Search.Type.AStar;

		// Handle search type
		switch (args[0]) {
		case "-dfs":
			type = Search.Type.DFS;
			break;
		case "-idfs":
			type = Search.Type.IDFS;
			break;
		case "-bfs":
			type = Search.Type.BFS;
			break;
		case "-greedy":
			type = Search.Type.Greedy;
			break;
		case "-astar":
			type = Search.Type.AStar;
			break;
		default:
			System.out.println("Not a valid search type");
			System.out.println("\n -dfs\n -idfs\n -bfs\n -greedy\n -astar");
			return;
		}
		
		// Handle input file
		File file;
		try {
			file  = new File(args[1]);
		}
		catch (Exception e) {
			System.out.println(e);
			return;
		}
		
		InputStream in = new FileInputStream(file);
        Reader reader = new InputStreamReader(in, Charset.defaultCharset());
        int r;
        
        ArrayList<Short> boxes = new ArrayList<Short>();
        ArrayList<Short> goals = new ArrayList<Short>();
        ArrayList<ArrayList<Character>> primitiveMap = new ArrayList<ArrayList<Character>>();
        
        short boxCounter = 0, lineCounter = 0, playerPos = -1, maxLength = 0, length = 0;
        primitiveMap.add(new ArrayList<Character>());
        
        // Read the map
        while ((r = reader.read()) != -1) {
            char ch = (char) r;
            
            if(ch == '\n') { 
            	lineCounter ++;
            	primitiveMap.add(new ArrayList<Character>());
            	maxLength = (short) Math.max((int) maxLength, (int) length);
            	length = 0;
            	System.out.println();
            	continue;
            }
            System.out.print(ch);
            
            boxCounter++;
            length++;
            primitiveMap.get(lineCounter).add(ch);
            
        }
        System.out.println("\n");
        reader.close();
        
        // Complete the map
        for(int i=0; i<primitiveMap.size(); i++) {
			for(int j=0; j<maxLength; j++) {
				// Add characters to complete map
				if(primitiveMap.get(i).size() < maxLength) primitiveMap.get(i).add('#');
			}
		}
        
        // Extract player and box positions
        for(int i=0; i<primitiveMap.size(); i++) {
			for(int j=0; j<maxLength; j++) {
				
				boxCounter = (short)(maxLength * i + j);
				char ch = primitiveMap.get(i).get(j);
	            if(ch == '.') goals.add(boxCounter);
	            if(ch == '$' || ch == '*') { 
	            	if(ch == '*') {
	            		ch = '.';
	            		goals.add(boxCounter);
	            	}
	            	if(ch == '$') {
	            		ch = ' ';
	            	}
	            	boxes.add(boxCounter);
	            }
	            if(ch == '@' || ch == '+') {
	            	if(ch == '+') {
	            		ch = '.';
	            		goals.add(boxCounter);
	            	}
	            	if(ch == '@') {
	            		ch = ' ';
	            	}
	            	playerPos = boxCounter;
	            }
	            primitiveMap.get(i).set(j, ch);
			}
		}
        
        // Handle Invalid Maps
        if(boxCounter == 0 || lineCounter == 0 || playerPos == -1) {
        	System.out.println("Make sure the map is in a valid format and has a player position specified");
        	return;
        }
        
        if(goals.size() != boxes.size()) {
        	System.out.println("Fatal: Goal states and boxes number must match!");
        	System.out.println("Quitting...");
        	return;
        }
        
        // Store the initial position of the boxes in an array
        short[] boxPositions = new short[boxes.size()];
        for(int i=0; i<boxes.size(); i++) {
        	boxPositions[i] = boxes.get(i).shortValue();
        }
        
        // Store the goal positions
        goalPositions = new short[goals.size()];
        for(int i=0; i<goals.size(); i++) {
        	goalPositions[i] = goals.get(i).shortValue();
        }
        
        // Initialize Objects
     	initialState = new State(boxPositions, playerPos);
     	map = Map.getInstanceMap(primitiveMap, maxLength);
     	
     	// Find deadlocks
     	for (short goalPos : goals) {
     		FindDeadLocksForGoal(goalPos);
     	}
		
     	map.PrintDeadLocks();
     	search = new Search(type, new Node(initialState, null ,map), map);
		
		visited = new HashSet<State>();
	}
	
	public void FindDeadLocksForGoal(short initPos){
		
		Node parentNode =  new Node(initialState, null ,map);
		initialState.imaginaryBoxPos = initPos;

		LinkedList<Node> queue = new LinkedList<>();
		HashSet<Integer> visited = new HashSet<>();
		visited.add((int)(initPos));
		queue.add(parentNode);
		
		while (queue.size() > 0) {
			Node node = queue.pollFirst();

			ArrayList<Node> children = node.PullExpand();

			if (children.size() > 0) {
				for (Node child : children) {
					if(visited.add((int)child.state.imaginaryBoxPos)){

						map.MarkAsNotDeadlock(child.state.imaginaryBoxPos);
						queue.addLast(child);
					}
				}
			}
		}
	}
}