package main;

public class testter {
  public static void main(String[] args) {
    int[][] costMatiz ={{1,2},{2,1}};
    int[][] mainMatriz = {{1,2},{2,1}};

    HungarianAlgorithm ha = new HungarianAlgorithm(costMatiz);
    int[][] assignment = ha.findOptimalAssignment();
    int total = 0;

    for (int[] index:
        assignment ) {

      total += mainMatriz[index[0]][index[1]];
    }
    System.out.println(total);

  }
}
