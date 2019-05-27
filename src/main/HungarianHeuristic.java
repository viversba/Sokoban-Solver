package main;

public class HungarianHeuristic implements Heuristic {
 
	@Override
  public int calculatedHeuristic(Object state) {
	  
    int[][] costMatrix = (int[][]) state;
    int [][] init =new int[costMatrix.length][costMatrix[0].length];
    for(int i = 0; i < costMatrix.length; i++){
      for (int j = 0; j <costMatrix[i].length; j++) {
        init[i][j]= costMatrix[i][j];
      }
    }
    HungarianAlgorithm algorithm = new HungarianAlgorithm(costMatrix);
    int[][] assignment = algorithm.findOptimalAssignment();
    int total = 0;

    for (int[] index:
        assignment ) {

      total += init[index[0]][index[1]];
    }
    return total;
  }
}
