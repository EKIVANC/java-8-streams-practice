package exercises;

public class Combination {


  public static void main(String[] args) {
    Object[] elements = new Object[]{'A', 'B', 'C', 'D', 'E'};
    combination(elements, 3);
  }


  public static void combination(Object[] elements, int K) {
    int N = elements.length;
    if (K > N) {
      System.out.println("Invalid input, K > N");
      return;
    }

    // calculate the possible combinations
    //		c(N,K);

    // init combination index array
    int combination[] = new int[K];

    int r = 0; // index for combination array
    int i = 0; // index for elements array

    while (r >= 0) {
      // forward step if i < (N + (r-K))
      if (i <= (N + (r - K))) {
        combination[r] = i;
        // if combination array is full print and increment i;
        if (r == K - 1) {
          print(combination, elements);
          i++;
        } else {
          // if combination is not full yet, select next element
          i = combination[r] + 1;
          r++;
        }
      }

      // backward step
      else {
        r--;
        if (r >= 0) {
          i = combination[r] + 1;
        }

      }
    }
  }


  private static void print(int[] combination, Object[] elements) {
    String output = "";
    for (int z = 0; z < combination.length; z++) {
      output += elements[combination[z]];
    }
    System.out.println(output);
  }
}