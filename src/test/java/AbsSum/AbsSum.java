package AbsSum;

import java.util.Arrays;

class Solution {
  int solution(int[] A) {
    // write your code in Java SE 8

    if (A == null ) {
      return 0;
    }

    Arrays.sort(A);

    int minAbsSum = Integer.MAX_VALUE;
    for (int i = 0; i < A.length; i++) {
      for (int j = i; j <A.length ; j++) {
          int tmp =  Math.abs(A[i]+A[j]);
          if (tmp< minAbsSum ){
            minAbsSum = tmp;
          }
      }
    }
    return minAbsSum;
  }
}


public class AbsSum {

  public static void main(String[] args) {
    Solution s = new Solution();

//    int[] A = new int[]{1, 4, -3};

//    int[] A = new int[5];
//    A[0] = -8;
//    A[1] =  4;
//    A[2] =  5;
//    A[3] =-10;
//    A[4] =  3;

    int p = s.solution(null);

    System.out.println(p);
  }
}
