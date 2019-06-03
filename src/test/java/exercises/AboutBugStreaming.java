package exercises;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AboutBugStreaming {

  public static void main(String[] args) {

    List<Integer> ints = new ArrayList<>();
    ints.add(1);
    ints.add(2);
    ints.add(3);
    ints.add(4);
    ints = ints.subList(0, 2);

    Stream stream = ints.stream();
    ints.add(5);
    stream.forEach(System.out::println);

  }

}
