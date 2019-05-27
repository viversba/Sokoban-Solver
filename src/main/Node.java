package main;

import java.security.PrivilegedActionException;
import java.util.ArrayList;

public class Node {
	
	public Node Parent;
	public State state;
	public char movement;
	
	public int cost;
	
	private Map map;
	private int prioridad = 0;

	public Node(State state) {this(state, null);}
	
	public Node(State state , Node Parent) {
		this(state, Parent, null, ' ');
	}
	
	public Node(State state , Node Parent, Map map) {
		this(state, Parent, map, ' ');
	}
	
	public Node(State state , Node Parent, Map map, char movement) {
		this.Parent = Parent;
		this.state = state;
		this.map = map;
		this.movement = movement;
	}
	
	public boolean IsInPath (Node node) {
		if(this.state.Equals(node.state))
			return true;
		return Parent != null ? Parent.IsInPath(node) : false;
	}
	
	public String PrintPath() {
		StringBuilder path = new StringBuilder();
		path.append(movement);
		return Parent != null ? Parent.PrintPath() + path.toString() : path.toString();
	}
	
	public ArrayList<Node> Expand() {
		
		ArrayList<Node> children = new ArrayList<Node>();
		
		// Move up
		if(state.playerPos >= map.GetWidth()) {
			short newPos = (short) (state.playerPos - map.GetWidth());
			char moveTo = map.GetCharAt(newPos);
//			System.out.println();
			// Check if the position is a goal point or an empty space
			if(moveTo == ' ' || moveTo == '.') {
				// Check if there are no boxes on that position
				if(!IsBoxOnSPot(newPos)) {
					State newState = new State(state.boxPositions, newPos);
					Node newChild = new Node(newState, this, map, 'u');
					if(!IsInPath(newChild)) {
						children.add(newChild);
					}
				}
				else {
					// If there is a box on that position, verify that it can be moved
					if(state.playerPos >= map.GetWidth() * 2) {
						short nextPos = (short) (state.playerPos - map.GetWidth() * 2);
						char nextSpace = map.GetCharAt(nextPos);
						if((nextSpace == ' ' || nextSpace == '.') && !IsBoxOnSPot(nextPos) &&!map.isDeadLock(newPos)) {
							short[] newBoxPositions = state.boxPositions.clone();
							int boxToMoveIndex = state.GetIndexOfBoxAtPosition(newPos);
							newBoxPositions[boxToMoveIndex] -= map.GetWidth();
							State newState = new State(newBoxPositions, newPos);
							Node newChild = new Node(newState, this, map, 'U');
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
				if(!IsBoxOnSPot(newPos)) {
					State newState = new State(state.boxPositions, newPos);
					Node newChild = new Node(newState, this, map, 'd');
					if(!IsInPath(newChild)) {
						children.add(newChild);
					}
				}
				else {
					// If there is a box on that position, verify that it can be moved
					if(state.playerPos < map.GetLength() - map.GetWidth() * 2) {
						short nextPos = (short) (state.playerPos + map.GetWidth() * 2);
						char nextSpace = map.GetCharAt(nextPos);
						if((nextSpace == ' ' || nextSpace == '.') && !IsBoxOnSPot(nextPos)&&!map.isDeadLock(newPos)) {
							short[] newBoxPositions = state.boxPositions.clone();
							int boxToMoveIndex = state.GetIndexOfBoxAtPosition(newPos);
							newBoxPositions[boxToMoveIndex] += map.GetWidth();
							State newState = new State(newBoxPositions, newPos);
							Node newChild = new Node(newState, this, map, 'D');
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
				if(!IsBoxOnSPot(newPos)) {
					State newState = new State(state.boxPositions, newPos);
					Node newChild = new Node(newState, this, map, 'l');
					if(!IsInPath(newChild)) {
						children.add(newChild);
					}
				}
				else {
					// If there is a box on that position, verify that it can be moved
					if(state.playerPos % map.GetWidth() > 1) {
						short nextPos = (short) (state.playerPos - 2);
						char nextSpace = map.GetCharAt(nextPos);
						if((nextSpace == ' ' || nextSpace == '.') && !IsBoxOnSPot(nextPos)&&!map.isDeadLock(newPos)) {
							short[] newBoxPositions = state.boxPositions.clone();
							int boxToMoveIndex = state.GetIndexOfBoxAtPosition(newPos);
							newBoxPositions[boxToMoveIndex] -= 1;
							State newState = new State(newBoxPositions, newPos);
							Node newChild = new Node(newState, this, map, 'L');
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
				if(!IsBoxOnSPot(newPos)) {
					State newState = new State(state.boxPositions, newPos);
					Node newChild = new Node(newState, this, map, 'r');
					if(!IsInPath(newChild)) {
						children.add(newChild);
					}
				}
				else {
					// If there is a box on that position, verify that it can be moved
					if(state.playerPos % map.GetWidth() < map.GetWidth() - 2) {
						short nextPos = (short) (state.playerPos + 2);
						char nextSpace = map.GetCharAt(nextPos);
						if((nextSpace == ' ' || nextSpace == '.') && !IsBoxOnSPot(nextPos)&&!map.isDeadLock(newPos)) {
							short[] newBoxPositions = state.boxPositions.clone();
							int boxToMoveIndex = state.GetIndexOfBoxAtPosition(newPos);
							newBoxPositions[boxToMoveIndex] += 1;
							State newState = new State(newBoxPositions, newPos);
							Node newChild = new Node(newState, this, map, 'R');
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

	public ArrayList<Node> PullExpand() {
		  //up, down, left, right
		  int[] moves = {-map.GetWidth(), map.GetWidth(),-1 , +1};
	    char[] sMoves ={'u','d','l','r'};

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

	          Node newChild = new Node(newState, this, map, sMoves[i]);
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
	
	public boolean IsBoxOnSPot(short pos) {

		for(short place : state.boxPositions)
			if (place == pos) return true;
		return false;
	}

  public int getPrioridad() {
		return this.prioridad;
  }

	public void setCost(int cost) {
		this.cost= cost;
	}

	public int getCost() {
		return this.cost;
	}

	public State getState() {
		return this.state;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}
}
