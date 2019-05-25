package main;

public class HungarianHeuristic implements Heuristic {
  @Override
  public int calculatedHeuristic(Object state) {
    int[][]matrizCostos = (int[][]) state;
    int [][] init =new int[3][3];
    for(int i = 0; i < 3; i++){
      for (int j = 0; j <3; j++) {
        init[i][j]= matrizCostos[i][j];
      }
    }
    HungarianAlgorithm ha = new HungarianAlgorithm(matrizCostos);
    int[][] assignment = ha.findOptimalAssignment();
    int total = 0;

    for (int[] index:
        assignment ) {
      System.out.println(index[0] +" "+index[1]);
      total += init[index[0]][index[1]];
    }
    return total;
  }
}
