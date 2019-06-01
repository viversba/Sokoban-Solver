package main;

import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.crypto.provider.AESParameters;
import com.sun.security.auth.NTDomainPrincipal;

public class Node {
	
	public Node Parent;
	public State state;
	public String movement;
	
	public int cost;
	private int priority = 0;

	public Node(State state) {this(state, null);}
	
	public Node(State state , Node Parent) {
		this(state, Parent, null, " ");
	}
	
	public Node(State state , Node Parent, Map map) {
		this(state, Parent, map, " ");
	}
	
	public Node(State state , Node Parent, String movement) {
		this.Parent = Parent;
		this.state = state;
		this.movement = movement;
	}
	
	public Node(State state , Node Parent, Map map, String movement) {
		this.Parent = Parent;
		this.state = state;
		this.movement = movement;
	}
	
	public boolean IsInPath2 (Node node) {
		if(this.state.Equals(node.state))
			return true;
		return Parent != null ? Parent.IsInPath(node) : false;
	}

	public boolean IsInPath (Node node) {
				Node parent = this.Parent;
				while (parent != null){
					if(parent.state.Equals(node.state)) return true;
					parent = parent.Parent;
				}
				return false;
	}

	public String PrintPath() {
		StringBuilder path = new StringBuilder();
		path.append(movement);
		return Parent != null ? Parent.PrintPath() + path.toString() : path.toString();
	}
	
	public ArrayList<Node> Expand() {
		
		ArrayList<Node> children = new ArrayList<>();
		FindReachablePaths();

		char[] actions = {'U', 'R', 'D', 'L'};
		int[] movements = {-Map.MAPINSTANCE.GetWidth(), 1, Map.MAPINSTANCE.GetWidth(), - 1};

		for(int i=0; i<this.state.boxPositions.length; i++) {
			short boxPos = this.state.boxPositions[i];
			//short[] newBoxPositions = this.state.boxPositions.clone();
			for(int j=0; j<4; j++) {

				int newPos = boxPos + movements[j];
				int pushPos = boxPos + movements[(j+2)%4];

				String pathString = GetPath(pushPos);
				if(pathString != null && !this.state.IsBoxOnSPot((short)newPos)&& !Map.MAPINSTANCE.isDeadLock(newPos) &&(Map.MAPINSTANCE.GetCharAt((short)newPos) == ' ' || Map.MAPINSTANCE.GetCharAt((short)newPos) == '.') ){
					state.boxPositions[i] = (short)newPos;
					Node childNode = new Node(new State(state.boxPositions, boxPos), this, pathString + actions[j]);
					childNode.setCost(pathString.length()+1+this.getCost());
					state.boxPositions[i] =  boxPos;
					//if(!IsInPath(childNode))
					children.add(childNode);
				}
			}
		}
		
		return children;
	}

	/*public ArrayList<Node> Expand2() {

		ArrayList<Node> children = new ArrayList<Node>();
		Map map = Map.MAPINSTANCE;
		// Move up
		if(state.playerPos >= map.GetWidth()) {
			short newPos = (short) (state.playerPos - map.GetWidth());
			char moveTo = map.GetCharAt(newPos);
			// Check if the position is a goal point or an empty space
			if(moveTo == ' ' || moveTo == '.') {
				// Check if there are no boxes on that position
				if(!this.state.IsBoxOnSPot(newPos)) {
					State newState = new State(state.boxPositions, newPos);
					Node newChild = new Node(newState, this, "u");
					if(!IsInPath(newChild)) {
						children.add(newChild);
					}
				}
				else {
					// If there is a box on that position, verify that it can be moved
					if(state.playerPos >= map.GetWidth() * 2) {
						short nextPos = (short) (state.playerPos - map.GetWidth() * 2);
						char nextSpace = map.GetCharAt(nextPos);
						if((nextSpace == ' ' || nextSpace == '.') && !this.state.IsBoxOnSPot(nextPos) && !map.isDeadLock(nextPos)) {
							short[] newBoxPositions = state.boxPositions.clone();
							int boxToMoveIndex = state.GetIndexOfBoxAtPosition(newPos);
							newBoxPositions[boxToMoveIndex] -= map.GetWidth();
							State newState = new State(newBoxPositions, newPos);
							Node newChild = new Node(newState, this, map, "U");
							if(!IsInPath(newChild)) {
								children.add(newChild);
							}
						}
					}
				}

			}
		}

		// Move up
		if(state.playerPos < map.GetLength() - map.GetWidth()) {
			short newPos = (short) (state.playerPos + map.GetWidth());
			char moveTo = map.GetCharAt(newPos);
			// Check if the position is a goal point or an empty space
			if(moveTo == ' ' || moveTo == '.') {
				// Check if there are no boxes on that position
				if(!this.state.IsBoxOnSPot(newPos)) {
					State newState = new State(state.boxPositions, newPos);
					Node newChild = new Node(newState, this, "d");
					if(!IsInPath(newChild)) {
						children.add(newChild);
					}
				}
				else {
					// If there is a box on that position, verify that it can be moved
					if(state.playerPos < map.GetLength() - map.GetWidth() * 2) {
						short nextPos = (short) (state.playerPos + map.GetWidth() * 2);
						char nextSpace = map.GetCharAt(nextPos);
						if((nextSpace == ' ' || nextSpace == '.') && !this.state.IsBoxOnSPot(nextPos) && !map.isDeadLock(nextPos)) {
							short[] newBoxPositions = state.boxPositions.clone();
							int boxToMoveIndex = state.GetIndexOfBoxAtPosition(newPos);
							newBoxPositions[boxToMoveIndex] += map.GetWidth();
							State newState = new State(newBoxPositions, newPos);
							Node newChild = new Node(newState, this, "D");
							if(!IsInPath(newChild)) {
								children.add(newChild);
							}
						}
					}
				}

			}
		}

		// Move left
		if(state.playerPos % map.GetWidth() > 0) {
			short newPos = (short) (state.playerPos - 1);
			char moveTo = map.GetCharAt(newPos);
			// Check if the position is a goal point or an empty space
			if(moveTo == ' ' || moveTo == '.') {
				// Check if there are no boxes on that position
				if(!this.state.IsBoxOnSPot(newPos)) {
					State newState = new State(state.boxPositions, newPos);
					Node newChild = new Node(newState, this, "l");
					if(!IsInPath(newChild)) {
						children.add(newChild);
					}
				}
				else {
					// If there is a box on that position, verify that it can be moved
					if(state.playerPos % map.GetWidth() > 1) {
						short nextPos = (short) (state.playerPos - 2);
						char nextSpace = map.GetCharAt(nextPos);
						if((nextSpace == ' ' || nextSpace == '.') && !this.state.IsBoxOnSPot(nextPos) && !map.isDeadLock(nextPos)) {
							short[] newBoxPositions = state.boxPositions.clone();
							int boxToMoveIndex = state.GetIndexOfBoxAtPosition(newPos);
							newBoxPositions[boxToMoveIndex] -= 1;
							State newState = new State(newBoxPositions, newPos);
							Node newChild = new Node(newState, this, "L");
							if(!IsInPath(newChild)) {
								children.add(newChild);
							}
						}
					}
				}

			}
		}

		// Move right
		if(state.playerPos % map.GetWidth() < map.GetWidth() - 1) {
			short newPos = (short) (state.playerPos + 1);
			char moveTo = map.GetCharAt(newPos);
			// Check if the position is a goal point or an empty space
			if(moveTo == ' ' || moveTo == '.') {
				// Check if there are no boxes on that position
				if(!this.state.IsBoxOnSPot(newPos)) {
					State newState = new State(state.boxPositions, newPos);
					Node newChild = new Node(newState, this, "r");
					if(!IsInPath(newChild)) {
						children.add(newChild);
					}
				}
				else {
					// If there is a box on that position, verify that it can be moved
					if(state.playerPos % map.GetWidth() < map.GetWidth() - 2) {
						short nextPos = (short) (state.playerPos + 2);
						char nextSpace = map.GetCharAt(nextPos);
						if((nextSpace == ' ' || nextSpace == '.') && !this.state.IsBoxOnSPot(nextPos) && !map.isDeadLock(nextPos)) {
							short[] newBoxPositions = state.boxPositions.clone();
							int boxToMoveIndex = state.GetIndexOfBoxAtPosition(newPos);
							newBoxPositions[boxToMoveIndex] += 1;
							State newState = new State(newBoxPositions, newPos);
							Node newChild = new Node(newState, this, "R");
							if(!IsInPath(newChild)) {
								children.add(newChild);
							}
						}
					}
				}

			}
		}

		return children;
	}
	*/
	public void FindReachablePaths() {
		
		PathNode startNode = new PathNode(state.playerPos, "");
		LinkedList<PathNode> queue = new LinkedList<>();
		queue.addFirst(startNode);
		this.state.AddPath(startNode);
		HashSet<Short> hash = new HashSet<>();
		hash.add(startNode.GetPosition());

		int[] movements = {-Map.MAPINSTANCE.GetWidth(), 1, Map.MAPINSTANCE.GetWidth(), - 1};
		char[] descriptions = {'u', 'r', 'd', 'l'};
		
		while(queue.size() > 0) {
			PathNode node = queue.pollFirst();
			for(int i=0; i<4; i++) {
				int mov = movements[i];
				short newPosition = (short) (node.GetPosition() + mov);
				if(hash.add(newPosition) && !this.state.IsBoxOnSPot(newPosition) && (Map.MAPINSTANCE.GetCharAt(newPosition) == ' ' || Map.MAPINSTANCE.GetCharAt(newPosition) == '.')) {
					PathNode newPathNode = new PathNode(newPosition, node.GetPath() + descriptions[i]);
					queue.addLast(newPathNode);
					if(IsBoxAdjacentToPos(newPosition)) {
						this.state.AddPath(newPathNode);
						//System.out.println(newPathNode.GetPath());
						//System.out.println(newPathNode.GetPosition());
					}
				}
			}
		}
	}
	
	public String GetPath(int pos) {
		for(int i = 0; i<state.reachablePaths.size(); i++) {
			if(state.reachablePaths.get(i).GetPosition() == pos) return state.reachablePaths.get(i).GetPath();
		}
		return null;
	}
	
	public boolean IsBoxAdjacentToPos(short position) {

		int[] movements = {-Map.MAPINSTANCE.GetWidth(), 1, Map.MAPINSTANCE.GetWidth(), - 1};
		for(int i=0; i<4; i++)
			if(this.state.IsBoxOnSPot((short)(position + movements[i]))) return true;

		return false;
	} 

	public ArrayList<Node> PullExpand() {
		
		Map map = Map.MAPINSTANCE;
		  //up, down, left, right
		int[] moves = {-map.GetWidth(), map.GetWidth(),-1 , +1};


	    short pos = this.state.imaginaryBoxPos;
	    boolean[] haveSpace = {
	        (pos >= map.GetWidth()*2),
	        (pos < map.GetLength() - map.GetWidth()*2),
	        (pos % map.GetWidth() > 1),
	        ((pos + 1)%map.GetWidth() > 1)
	    };

		ArrayList<Node> children = new ArrayList<Node>();
	    for (int i = 0; i < 4; i++) {
	    	if (haveSpace[i]){
				int dir  = moves[i];
				short newBoxPos= (short) (pos + dir);
				short newPlayerPos= (short) (pos +(dir*2));
				char moveToPlayer = map.GetCharAt(newPlayerPos);
				char moveToBox = map.GetCharAt(newBoxPos);
				if((moveToPlayer == ' '|| moveToPlayer == '.')&&(moveToBox == ' ' || moveToBox == '.')) {
					// Check if there are no boxes on that position
					map.deadlock[newBoxPos] = true;
					State newState = new State(state.boxPositions, state.playerPos);
					newState.imaginaryBoxPos = newBoxPos;

					Node newChild = new Node(newState, this, map, " ");
					children.add(newChild);
				}
	    	}
	    }

		return children;
	}
	
	public int GetCount() {
        int count = 0;
        if (this.Parent == null) {
            return count;
        }
        count++;
        return count + Parent.GetCount();
    }

	public int getPriority() {
		return this.priority;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCost() {
		return this.cost;
	}

	public State getState() {
		return this.state;
	}

	public void setPriority(int prioridad) {
		this.priority = prioridad;
	}
}
