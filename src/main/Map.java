package main;

import java.util.ArrayList;

public class Map {

	public char[][] map;
	public boolean[] goalPositions;
	public boolean[] deadlock;
	public static ArrayList<Integer> goalPositionsList;
	public static Map MAP_INSTANCE;

	public static Map getInstanceMap(ArrayList<ArrayList<Character>> arrayMap, short maxLength) {
		if (MAP_INSTANCE == null){
			MAP_INSTANCE = new Map(arrayMap,maxLength);
		}
		else{
			System.out.println("The map is already created");
		}

		return MAP_INSTANCE;
	}
	
	private Map(ArrayList<ArrayList<Character>> arrayMap, short maxLength) {
		
		map = new char[arrayMap.size()][maxLength];
		goalPositions = new boolean[GetLength()];
		deadlock = new boolean[GetLength()];
		goalPositionsList = new ArrayList<>();
		
		for(int i=0; i<arrayMap.size(); i++) {
			for(int j=0; j<maxLength; j++) {
				// Add characters to complete map
				if(arrayMap.get(i).size() < maxLength) arrayMap.get(i).add('#');
				// Copy characters to main array
				map[i][j] = arrayMap.get(i).get(j);
				
				// Each time a goal position is discovered, put it in a goalPositions array
				if(map[i][j] == '.') {
					goalPositions[maxLength * i + j] = true;
					goalPositionsList.add(maxLength * i + j);
				}
			}
		}
	}
	
	public void Print() {
		
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[i].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
	
	public void PrintDeadLocks() {
		for(int i=0; i< deadlock.length; i++) {
			if(isDeadLock(i)) {
				System.out.printf("%c\t",'x');
			}else System.out.printf(" \t");
			if ((i+1)%GetWidth() == 0)System.out.println();
		}
		System.out.println();
	}
	
	public void MarkAsNotDeadlock(int position) {
		
		deadlock[position] = true;
	}
	
	public char GetCharAt(short pos) {
		
		return map[pos / map[0].length][pos % map[0].length];
	}
	
	public short GetLength() {
		
		return (short)(map.length * map[0].length);
	}
	
	public short GetWidth() {
		return (short)map[0].length;
	}
	
	public short GetHeight() {
		return (short)map.length;
	}
	
	public boolean GoalTest(short[] boxPositions) {
		
		for(short box : boxPositions)
			if(!goalPositions[box]) return false;
		return true;
	}
	
	public boolean isDeadLock(int pos) {
		return !deadlock[pos];
	}
}
