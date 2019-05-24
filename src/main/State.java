package main;

public class State {

	public short playerPos;
	public short imaginaryBoxPos;
	public short[] boxPositions;

	public State() {}
	
	public State(short[] boxPositions, short playerPos) {
		this.boxPositions = boxPositions.clone();
		this.playerPos = playerPos;
	}
	
	public boolean Equals(State state) {
		
		if (this.playerPos != state.playerPos) return false;
		for(int i=0 ; i<this.boxPositions.length; i++)
			if(this.boxPositions[i] != state.boxPositions[i]) return false;
		return true;
	}
	
	public int GetIndexOfBoxAtPosition(short pos) {
		for(int i=0; i<boxPositions.length; i++) {
			if (boxPositions[i] == pos) return i;
		}
		return -1;
	}
	
	public String PrintBoxPositions() {
		
		StringBuilder sBuilder = new StringBuilder();
		for(int i=0; i<boxPositions.length; i++) {
			sBuilder.append(""+boxPositions[i] + " ");
		}
		return sBuilder.toString();
	}
}
