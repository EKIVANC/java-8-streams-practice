package Interview_Q3;

import java.util.*;
import java.util.stream.Stream;


class Solution {
  public int solution() {
    // write your code in Java SE 8
    return 0;
  }
}

public class Kicker {

  public static void main(String[] args) {
    Solution s = new Solution();
    System.out.println(s.solution());
    System.out.println("kicker");
  }

}


//class Reconciler {
//
//  Stream<PendingTransaction> reconcile(Stream<PendingTransaction> pending, Stream<Stream<ProcessedTransaction>> processed) {
//
//    if(pending == null && processed == null) {
//      return Stream.empty();
//    }
//    Stream<Long>  filteredProcessedId = processed
//        .filter(Objects::nonNull)
//        .flatMap(p -> p)
//        .filter(p -> p.getStatus() != null && "DONE".equals(p.getStatus().orElse(null)))
//        .filter(p ->   p.getId() != null && p.getId().length() > 0)
//        .map(p -> Long.parseLong(p.getId()));
//
//    return pending.filter(p -> filteredProcessedId.anyMatch(pr -> pr.equals(p.getId())));
//  }
//
//}