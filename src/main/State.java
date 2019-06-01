package main;

import java.util.ArrayList;
import java.util.Arrays;


public class State {

	public short playerPos;
	public short imaginaryBoxPos;
	public short[] boxPositions;
	public double[][] costMatriz;
	public ArrayList<PathNode> reachablePaths;
	//public boolean[] reachablePositions;


	public State(short[] boxPositions, short playerPos) {
		this.boxPositions = boxPositions.clone();
		this.playerPos = playerPos;
		this.imaginaryBoxPos = 0;
		this.costMatriz = new double[boxPositions.length][boxPositions.length];
		//reachablePositions = new boolean[Map.MAPINSTANCE.GetLength()];
		reachablePaths = new ArrayList<>();
		Arrays.sort(this.boxPositions);
	}
	
	public boolean Equals(State state) {


		if (this.playerPos != state.playerPos) return false;
		for(int i=0 ; i<this.boxPositions.length; i++)
			if(this.boxPositions[i] != state.boxPositions[i]) return false;
		return true;
	}
	
	public int GetIndexOfBoxAtPosition(short pos) {
		for(int i=0; i<boxPositions.length; i++)
			if (boxPositions[i] == pos) return i;
		return -1;
	}
	
	public boolean IsBoxOnSPot(short pos) {
		
		for(short place : this.boxPositions)
			if (place == pos) return true;
		return false;
	}
	
	public void  makeCostMatriz(){
		for (int i = 0; i < boxPositions.length; i++) {
			short currentBoxPos = boxPositions[i];
			for (int j = 0; j < Map.goalPositionsList.size(); j++) {
				costMatriz[i][j] = Utils.CalculatedManhattanDistance(currentBoxPos, Map.goalPositionsList.get(i));
			}
		}
	}
	
	public void AddPath(PathNode newPath) {
		reachablePaths.add(newPath);
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
	public void Print() {

		for(int i=0; i< Map.MAPINSTANCE.map.length; i++) {
			for(int j=0; j<Map.MAPINSTANCE.map[i].length; j++) {
				if( IsBoxOnSPot((short)(Map.MAPINSTANCE.GetWidth()*i + j)) ){
					System.out.print('$');
				}else if((Map.MAPINSTANCE.GetWidth()*i + j) == this.playerPos){
					System.out.print('@');
				}
				else{
					System.out.print(Map.MAPINSTANCE.map[i][j]);
				}
			}
			System.out.println();
		}
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(playerPos);
		sb.append(' ');
		for (int bp:
				 boxPositions) {
			sb.append(bp);
			sb.append(' ');
		}
		return sb.toString();
	}



}
