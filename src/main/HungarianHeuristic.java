package main;

public class HungarianHeuristic implements Heuristic {
  @Override
  public int calculatedHeuristic(Object state) {
    int[][]matrizCostos = (int[][]) state;
    int [][] init =new int[matrizCostos.length][matrizCostos[0].length];
    for(int i = 0; i < matrizCostos.length; i++){
      for (int j = 0; j <matrizCostos[i].length; j++) {
        init[i][j]= matrizCostos[i][j];
      }
    }
    HungarianAlgorithm ha = new HungarianAlgorithm(matrizCostos);
    int[][] assignment = ha.findOptimalAssignment();
    int total = 0;

    for (int[] index:
        assignment ) {

      total += init[index[0]][index[1]];
    }
    return total;
  }
}
