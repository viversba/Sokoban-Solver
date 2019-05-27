package main;

import java.util.Arrays;


public class State {

	public short playerPos;
	public short imaginaryBoxPos;
	public short[] boxPositions;
	public double[][] costMatriz;


	public State(short[] boxPositions, short playerPos) {
		this.boxPositions = boxPositions.clone();
		this.playerPos = playerPos;
		this.imaginaryBoxPos = 0;
		this.costMatriz = new double[boxPositions.length][boxPositions.length];
	}
	
	public boolean Equals(State state) {
		Arrays.sort(state.boxPositions);
		Arrays.sort(this.boxPositions);


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
	
	public void  makeCostMatriz(){
		for (int i = 0; i < boxPositions.length; i++) {
			short currentBoxPos = boxPositions[i];
			for (int j = 0; j < Map.goalPositionsList.size(); j++) {

				costMatriz[i][j] = Utils.CalculatedManhattanDistance(currentBoxPos, Map.goalPositionsList.get(i));
			}
			
		}
	}
	public String PrintBoxPositions() {
		
		StringBuilder sBuilder = new StringBuilder();
		for(int i=0; i<boxPositions.length; i++) {
			sBuilder.append(""+boxPositions[i] + " ");
		}
		return sBuilder.toString();
	}

	public double[][] getCostMatriz() {
		makeCostMatriz();
		return this.costMatriz;
	}
}
