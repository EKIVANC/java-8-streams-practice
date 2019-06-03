package lectures;


import beans.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import mockdata.MockData;
import org.assertj.core.util.Lists;
import org.junit.Test;

public class Lecture8 {

  @Test
  public void simpleGrouping() throws Exception {
    Map<String, Long> result = MockData.getCars().stream().filter( car -> car.getColor().equalsIgnoreCase("yellow") )
        .collect(Collectors.groupingBy(Car::getColor, Collectors.counting() ));

    System.out.println(result);

  }

  @Test
  public void groupingAndCounting() throws Exception {
    ArrayList<String> names = Lists
        .newArrayList(
            "John",
            "John",
            "Mariam",
            "Alex",
            "Mohammado",
            "Mohammado",
            "Vincent",
            "Alex",
            "Alex"
        );

    Map<String, Long> retVal = names.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    System.out.println(retVal);

  }

}