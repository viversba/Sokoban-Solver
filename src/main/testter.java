package main;

import java.util.Arrays;

public class testter {
  public static void main(String[] args) {
    double[][] costMatiz ={{200,1,2},{2,300,1},{1,2,400}};
    //int[][] mainMatriz = {{200,1,2},{2,300,1},{1,2,400}};
    Hungarian ha = new Hungarian(costMatiz);
    int[] assignment = ha.execute();
    //System.out.println("Bipartite Matching: " + Arrays.toString(result));

    for (int i = 0; i < assignment.length; i++) {
      System.out.println(assignment[i]);

    }
    int total = 0;
    int fila = 0;
    for (int index:
        assignment ) {

      total += costMatiz[fila++][index];
    }
    System.out.println(total);

  }
}
