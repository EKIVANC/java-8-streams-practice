package lectures;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public class Lecture3 {

  @Test
  public void min() throws Exception {
    final List<Integer> numbers = ImmutableList.of(2, 3, 100, 23, 93, 1,99);

    System.out.println(numbers.stream().reduce(numbers.get(0), (a, b) -> a < b ? a : b));

    Optional<Integer> min = numbers.stream().min(Integer::compareTo);
    System.out.println(min.get());


  }

  @Test
  public void max() throws Exception {
    final List<Integer> numbers = ImmutableList.of(1, 2, 3, 100, 23, 93, 99);
    System.out.println(numbers.stream().max(Integer::compareTo));
  }
}
