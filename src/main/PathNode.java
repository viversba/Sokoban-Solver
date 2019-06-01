package main;

public class PathNode {
	
	private short position;
	private String pathString;
	
	public PathNode(short position, String pathString) {
		
		this.position = position;
		this.pathString = pathString;
	}

	public short GetPosition() {
		return this.position;
	}
	
	public String GetPath() {
		return this.pathString;
	}
}
