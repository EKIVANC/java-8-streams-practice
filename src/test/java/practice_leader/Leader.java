package practice_leader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Leader {

  public static void main(String[] args) {

    int[] elements = new int[]{4, 3, 4, 4, 4, 2};

    Solution s = new Solution();
    s.solution(elements);
  }


}


class Solution {
  public int solution(int[] A) {

    List<Integer> collect = Arrays.stream(A).boxed().collect(Collectors.toList());

    int counter =0;

    for (int i = 0; i < collect.size() ; i++) {
      List<Integer> firstPart = collect.subList(0,i+1);
      List<Integer> secondPart = collect.subList(i+1, collect.size());

      if (firstPart != null && firstPart.size() > 0 && secondPart!= null && secondPart.size() > 0 ){
        if (checkIfEqualLeader(firstPart, secondPart )) {
          counter++;
        }
      }
    }

    return counter;
  }

  private boolean checkIfEqualLeader(List<Integer> firstPart, List<Integer> secondPart) {

    Integer leaderOfFirstPart = findLeader(firstPart);
    Integer leaderOfSecondPart = findLeader(secondPart);

    if (leaderOfFirstPart != null && leaderOfSecondPart != null &&  leaderOfFirstPart.compareTo(leaderOfSecondPart) ==0  ) {
      return true;
    }
    return false;
  }

  private Integer findLeader(List<Integer> part) {

    HashMap<Integer, Integer>  leaderList = new HashMap<>();

    Integer maxRepeatedValueCount = 0;
    Integer maxRepeatedValue = -1;

    for (Integer num : part) {
      Integer segmentValue = leaderList.get(num);
      if (segmentValue == null){
        segmentValue = new Integer(1);
      }
      else {
        segmentValue +=1;
      }

      leaderList.put(num, segmentValue);

      if (segmentValue > maxRepeatedValueCount) {
          maxRepeatedValueCount = segmentValue;
          maxRepeatedValue=num;
      }
    }


    double i = part.size() / 2;

    if (maxRepeatedValueCount > (long)i ) {
        return maxRepeatedValue;
    }

    return null;
  }

}