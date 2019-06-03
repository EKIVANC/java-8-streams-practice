package lectures;

import beans.Car;
import beans.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import mockdata.MockData;
import org.junit.Test;

public class MoreExercise {


  class Foo {
    String name;
    List<Bar> bars = new ArrayList<>();

    Foo(String name) {
      this.name = name;
    }
  }

  class Bar {
    String name;

    Bar(String name) {
      this.name = name;
    }
  }


  // int array to -> stream
  @Test
  public void testStreams() throws IOException {
    List<Foo> foos = new ArrayList<>();

// create foos
    IntStream
        .range(1, 4)
        .forEach(i -> foos.add(new Foo("Foo" + i)));

// create bars
    foos.forEach(f ->
        IntStream
            .range(1, 4)
            .forEach(i -> f.bars.add(new Bar("Bar" + i + " <- " + f.name))));

    System.out.println(foos);


//    Map<Integer, List<Person>> collect = MockData.getPeople()
//        .stream()
//        .collect(Collectors.groupingBy(p -> p.getAge()));

//    IntSummaryStatistics ageStatistics = MockData.getPeople().stream()
//        .collect(Collectors.summarizingInt(Person::getAge));
//
//    System.out.println(ageStatistics.getMax());


//    Supplier<Stream<String>> streamSupplier = () -> Stream.of("d2", "a2", "b1", "b3", "c")
//        .filter(s -> s.startsWith("a"));



//    Stream.of("d2", "a2", "b1", "b3", "c")
//        .sorted((s1, s2) -> {
//          System.out.printf("sort: %s; %s\n", s1, s2);
//          return s1.compareTo(s2);
//        })
//        .filter(s -> {
//          System.out.println("filter: " + s);
//          return s.startsWith("a");
//        })
//        .map(s -> {
//          System.out.println("map: " + s);
//          return s.toUpperCase();
//        })
//        .forEach(s -> System.out.println("forEach: " + s));

//    int[] numbers = {9,3,1,2,3};
//
//    Integer[] integers = Arrays.stream(numbers).boxed().toArray(Integer[]::new);
//    System.out.println(integers);

//    IntStream.of(numbers).distinct().sorted().
    //(n1,n2)-> { return n1.compareTo(n2); }
    //Stream.of(numbers).distinct().sorted(Comparator::comparingInt.reversed());

//    Stream.of(numbers).forEach( p-> {
//      System.out.println(p[0]);
//    }  ); ;// .distinct().sorted(Comparator.reverseOrder());

//    IntStream.of(numbers).distinct().sorted( (n1, n2)-> {
//      return 0;
//    } ).forEach(System.out::println);
  }


  @Test
  public void testIndefiniteStream() {
    IntStream.iterate(0, i -> (i + 1) % 2)
        //.distinct()
        .limit(10)
        .forEach(System.out::println);
  }

  @Test
  public void testIdefiniteParallel() {
    IntStream.iterate(0, i -> (i + 1) % 2)
        .parallel()
        .distinct()
        .limit(10)
        .forEach(System.out::println);
  }


  @Test
  public void limitOffSetTrap() {
    // skip oncekilerin calismasini engelliyor ama ters calisiyor!
    IntStream.iterate(0, i -> i + 1)
        .skip(5)
        .limit(10)
        .forEach(System.out::println);
  }


  @Test
  public void limitOffSetTrapFixing() {
    // skip oncekilerin calismasini engelliyor ama ters calisiyor!
    IntStream.iterate(0, i -> i + 1)
        .skip(5).peek(p -> System.out.println("skip is working with:" + p))
        .limit(10).peek(p -> System.out.println("limit with:" + p))
        .forEach(p -> System.out.println("foreach working with:" + p));
  }

  @Test
  public void anotherBrickOnTheWall() {
    IntStream.iterate(0, i -> (i + 1) % 2)
        .parallel()
        .distinct()
        .limit(10)
        .forEach(System.out::println);
  }


  private final String NAME = "JERRY";

  @Test
  // 1- Find the number of users whose name contains a particular keyword and younger than 18 and make - Done
  public void numberOfPeopleForaCertainCondition() throws IOException {
    MockData.getPeople().stream()
        .filter(p -> p.getFirstName().toUpperCase().contains(NAME) && p.getAge() < 18)
        .peek(System.out::println).collect(
        Collectors.toList());
  }

  //2- Sort the cars makes by average price - Done
  @Test
  public void sortCarMakesByAvgPrice() throws IOException {
    LinkedHashSet<Entry<String, Double>> collect = MockData.getCars().stream()
        .collect(Collectors.groupingBy(Car::getMake, Collectors.averagingDouble(Car::getPrice)))
        .entrySet().stream().sorted(Entry.<String, Double>comparingByValue().reversed())
        .peek(System.out::println)
        .collect(Collectors.toCollection(LinkedHashSet::new));

    System.out.println(collect);
  }

  //3- Find the most expensive car maker in avegahe - Done
  @Test
  public void mostExpensiveCarMaker() throws IOException {
    Optional<Entry<String, Double>> first = MockData.getCars().stream()
        .collect(Collectors.groupingBy(Car::getMake, Collectors.averagingDouble(Car::getPrice)))
        .entrySet().stream()
        .sorted(Entry.<String, Double>comparingByValue().reversed()).findFirst();
    System.out.println(first);
  }

  //4- Find the the car maker who has the oldest car - Done
  @Test
  public void oldestCarMaker() throws IOException {
    Optional<Car> first = MockData.getCars().stream()
        .sorted(Comparator.comparing(Car::getYear)).findFirst();

    System.out.println(first.get().getMake());
  }

  // 5- Find the 5 people whose has the longest name and surname merged. - Done
  @Test
  public void get5LongestName() throws IOException {
    List<Person> collect = MockData.getPeople().stream().sorted(
        (p1, p2) -> {
          Integer l1 = (p1.getFirstName()).length();
          Integer l2 = (p2.getFirstName()).length();
          return l1.compareTo(l2) * -1;
        }).limit(5)
        .collect(Collectors.toList());

    System.out.println(collect);
  }

  //6- Who can live longer, man or women
  @Test
  public void whoLivesLonger() throws IOException {
    Optional<Entry<String, Double>> first = MockData.getPeople().stream().
        collect(
            Collectors.groupingBy(p -> p.getGender(),
                Collectors.averagingDouble(Person::getAge))).entrySet().stream().sorted(
        Entry.<String, Double>comparingByValue().reversed()).findFirst();

    System.out.println(first.get());
  }

  // 7- Find the people who has yahoo eMail address
  @Test
  public void findYahooIst() throws IOException {
    List<Person> yahooIst = MockData.getPeople().stream()
        .filter(p -> p.getEmail().toLowerCase().contains("yahoo"))
        .collect(Collectors.toList());

    System.out.println(yahooIst);
  }

  // 8- Find the most two expensive car color
  @Test
  public void expCarColor() throws IOException {
    LinkedHashSet<Entry<String, Double>> collect = MockData.getCars().stream()
        .collect(Collectors.groupingBy(Car::getColor, Collectors.averagingDouble(Car::getPrice)))
        .entrySet().stream().sorted(
            (c1, c2) -> c1.getValue().compareTo(c2.getValue()) * -1
        ).collect(
            Collectors.toCollection(LinkedHashSet::new));
    System.out.println(collect);
  }

}

