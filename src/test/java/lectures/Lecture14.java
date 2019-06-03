package lectures;

import beans.Car;
import beans.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import mockdata.MockData;
import org.junit.Test;

public class Lecture14 {

  @Test
  // S1: Sort the cars Makes by average price
  public void sortCarMakeByAvgUnitPrice() throws IOException{
    Map<String, Double> carByUnitPrice = MockData.getCars().stream()
        .collect(Collectors.groupingBy(Car::getMake, Collectors.averagingDouble(Car::getPrice)));

    LinkedHashMap<String, Double> collect = carByUnitPrice.entrySet().stream()
        .sorted(Entry.comparingByValue(Comparator.reverseOrder())).collect(
            Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

    System.out.println(collect);

  }


  /*
    1. Concrete to abstraction - invoke stream
    2. Multiple operations map, filter, flatmap, average, sum, min, max n...
      - Intermediate operations are lazy.
    3. Terminal operators collect streams back to concrete type. .collect, .get
  */

  // parallel streams ?

  @Test
  //Q1 - Find the number of users whose name contains a particular keyword and younger than 18 and make
  public void youngSelectedPeoppe() throws Exception {
    String prefix = "Zack";
    List<Person> collect = MockData.getPeople().parallelStream()
        .filter(
            p -> p.getFirstName().toLowerCase().contains(prefix.toLowerCase()) && p.getAge() < 18)
        .collect(Collectors.toList());
    for (Person person : collect) {
      System.out.println(person);
    }
  }


  @Test
  public void simpleSorting() throws Exception {

    List<Car> collect = MockData.getCars().stream().sorted(Comparator.comparing(Car::getPrice))
        .collect(
            Collectors.toList());

    collect.forEach(System.out::println);
  }

  @Test
  // Sort the cars Makes by average price
  public void sortByAveragePrice() throws Exception {
    LinkedHashMap<String, Double> collect = MockData.getCars().parallelStream()
        .collect(Collectors.groupingBy(Car::getMake, Collectors.averagingDouble(Car::getPrice)))
        .entrySet().stream().sorted((Entry.<String, Double>comparingByValue().reversed())).collect(
            Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

    Map<String, Long> collectTest = MockData.getCars().stream()
        .collect(Collectors.groupingBy(c -> c.getColor(), Collectors.counting()));

    //Map<String, Long> collectTestSorted = collectTest.entrySet().stream().sorted((c1, c2) -> c1.getValue().compareTo(c2.getValue())).collect(Collectors.toMap(c -> c.getKey(), c -> c.getValue()));

    Map<String, Double> collect2 = collect.entrySet().stream()
        .sorted((Entry.<String, Double>comparingByValue().reversed())).collect(Collectors
            .toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    System.out.println(collect);
  }

  @Test
  //Find the most expensive car maker in average
  public void mostExpensiveCarMake() throws IOException {

    Optional<String> collectResult = MockData.getCars().stream().collect(
        Collectors.groupingBy(car -> car.getMake(), Collectors.averagingDouble(c -> c.getPrice())))
        .entrySet().stream().sorted(Entry.<String, Double>comparingByValue().reversed()).collect(
            Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1,
                LinkedHashMap::new)).keySet().stream().findFirst();

    System.out.println(collectResult);
  }


  @Test
  public void oldestCarMake() throws IOException {
    LinkedHashMap<String, Double> collect = MockData.getCars().stream()
        .collect(Collectors.groupingBy(c -> c.getMake(), Collectors.averagingInt(Car::getYear)))
        .entrySet().stream()
        .sorted(Entry.<String, Double>comparingByValue())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    System.out.println(collect);
  }

  @Test
  // Find the 5 people whose has the longest name and surname merged.
  public void longestNames() throws IOException {

    List<Person> limit = MockData.getPeople().stream().sorted((p1, p2) -> {
      Integer firstPersonNameLenght = p1.getFirstName().length() + p1.getLastName().length();
      Integer secondPersonNameLenght = p2.getFirstName().length() + p2.getLastName().length();
      return (firstPersonNameLenght.compareTo(secondPersonNameLenght)) * -1;
    }).limit(5).collect(Collectors.toList());

    System.out.println(limit);
  }


  @Test
  // gini questions if it exists or not
  public void giniQuestion() throws IOException {
    Optional<Car> first = MockData.getCars().stream().filter(c -> false).findFirst();
    if (first.isPresent()) {
      System.out.println(first);
    }
  }


  //Who can live longer, man or women
  // strategy : calculate age avearge by grouping men or women
  @Test
  public void getAverageAgeByGender() throws IOException {
    Optional<Entry<String, Double>> first = MockData.getPeople().stream().collect(
        Collectors.groupingBy(p -> p.getGender(), Collectors.averagingDouble(Person::getAge)))
        .entrySet().stream().sorted(Entry.<String, Double>comparingByValue().reversed())
        .findFirst();

    System.out.println(first);

  }

  @Test
  public void findPeopleHaveYahooEmailAddress() throws IOException {
    final String searchWord = "yaHoO";
    List<Person> collect = MockData.getPeople().stream()
        .filter(p -> p.getEmail().toLowerCase(Locale.ENGLISH).contains(searchWord.toLowerCase()))
        .collect(Collectors.toList());
    System.out.println(collect);
  }


  @Test
  // 8 Find the most two expensive car color
  public void findTheMostExpColar() throws IOException {
    LinkedHashMap<String, Double> collect = MockData.getCars().stream()
        .collect(Collectors.groupingBy(Car::getColor, Collectors.averagingDouble(Car::getPrice))).
            entrySet().stream().sorted(Entry.<String, Double>comparingByValue().reversed()).limit(2)
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> a, LinkedHashMap::new));

  }

  @Test
  //9  Find which year has the most expensive cars
  public void mostExpensiceCarYear() throws IOException {
    LinkedHashMap<Integer, Double> collect = MockData.getCars().stream().collect(Collectors
        .groupingBy(car -> car.getYear(), Collectors.averagingDouble(car -> car.getPrice())))
        .entrySet().stream().sorted(Entry.<Integer, Double>comparingByValue().reversed())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> a, LinkedHashMap::new));
  }


  //10- Find which car make has how many number of cars in each color.
  @Test
  public void groupCarMakeByNumberOfCarsOwned() throws IOException {
    LinkedHashMap<CarMakeWithColor, Long> collect = MockData.getCars().stream()
        .peek(System.out::println)
        .collect(Collectors.groupingBy(car -> {
          String color = car.getColor();
          String make = car.getMake();
          return new CarMakeWithColor(make, color);
        }, Collectors.counting())).entrySet().stream().sorted(Entry.comparingByValue())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> a, LinkedHashMap::new));

    System.out.println(collect);
  }


  @Test
  public void testForDebugging() {
    List<String> versions = new ArrayList<>();
    versions.add("Lollipop");
    versions.add("KitKat");
    versions.add("Jelly Bean");
    versions.add("Ice Cream Sandwidch");
    versions.add("Honeycomb");
    versions.add("Gingerbread");

    versions.stream().filter( s-> s.length() >7 ).peek(e-> System.out.println("After length 7: "+e) )
        .filter(s-> s.startsWith("H")).peek( s-> System.out.println("After startsWith(\"H\" filter: "+s ) )
        .collect(Collectors.toSet());
  }


}


final class CarMakeWithColor {

  final String carMake;
  final String color;

  public CarMakeWithColor(String carMake, String color) {
    this.carMake = carMake;
    this.color = color;
  }

  @Override
  public boolean equals(Object obj) {
//    return super.equals(obj);
    if (this.carMake.equals(((CarMakeWithColor) obj).getCarMake()) && this.color
        .equals(((CarMakeWithColor) obj).getColorr())) {
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return (this.carMake + this.color).hashCode();
  }

  public String getCarMake() {
    return carMake;
  }

  public String getColorr() {
    return color;
  }
}