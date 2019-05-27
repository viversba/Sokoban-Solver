package main;

public class HungarianHeuristic implements Heuristic {
  @Override
  public int calculatedHeuristic(Object state) {
    double[][]matrizCostos = (double[][]) state;

    Hungarian ha = new Hungarian(matrizCostos);
    int[] assignment = ha.execute();
    int total = 0;
    int fila=0;
    for (int index:
        assignment ) {
      total += matrizCostos[fila++][index];
    }
    return total;
  }
}
