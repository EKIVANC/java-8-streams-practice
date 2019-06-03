package lectures;


import static org.assertj.core.api.Assertions.assertThat;

import beans.Car;
import beans.Person;
import beans.PersonDTO;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import mockdata.MockData;
import org.junit.Test;

public class Lecture5 {

  @Test
  public void understandingFilter() throws Exception {
    ImmutableList<Car> cars = MockData.getCars();

    List<Car> yellowCars = cars.stream().filter(car -> car.getColor().equalsIgnoreCase("yellow"))
        .collect(Collectors.toList());

    System.out.println(yellowCars);

  }

  @Test
  public void ourFirstMapping() throws Exception {
    // transform from one data type to another
    List<Person> people = MockData.getPeople();
    List<PersonDTO> collectResult = people.stream()
        .map(PersonDTO::map)
        .collect(
            Collectors.toList());

    System.out.println(collectResult);
  }

  @Test
  public void averageCarPrice() throws Exception {
    // calculate average of car prices
    double retVal = MockData.getCars().stream().mapToDouble(value -> value.getPrice()).average()
        .getAsDouble();
    System.out.println(retVal);
  }

}



