package main;

public class Utils {
  public static int CalculatedManhattanDistance(int pos1,int pos2){
    int manhattanDistance = 0;
    int pos1X = pos1 / Map.MAPINSTANCE.GetWidth();
    int pos1Y = pos1 % Map.MAPINSTANCE.GetWidth();
    int pos2X = pos2 /  Map.MAPINSTANCE.GetWidth();
    int pos2Y = pos2 % Map.MAPINSTANCE.GetWidth();
    manhattanDistance= Math.abs(pos1X -pos2X) + Math.abs(pos1Y-pos2Y);
    return manhattanDistance;
  }
  public static int CalculatedMinDistance(short pos1,short[] poss){
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < poss.length; i++) {
      int val = CalculatedManhattanDistance(pos1, poss[i]);
      if (val < min) min = val;
    }

    return min;
  }
}
