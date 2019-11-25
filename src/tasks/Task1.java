package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис (он выдает несортированный Set<Person>)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

  // !!! Редактируйте этот метод !!!
  /*
  Метод indexof имеет сложность O(n), так как перебирает в цикле и сравнивает элементы листа с переданным в метод
  аргументом, пока не встретит совпадение или не закончатся элементы.
  Метод indexof вызывается для каждого элемента множеста persons, что также составляет сложность O(n).
  Исходя из этого асимптотика работы сортировки составит O(n^2).
  */
  private List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = PersonService.findPersons(personIds);
    return persons.stream()
            .sorted(Comparator.comparing(p -> personIds.indexOf(p.getId())))
            .collect(Collectors.toList());
  }

  @Override
  public boolean check() {
    List<Integer> ids = List.of(1, 2, 3);

    return findOrderedPersons(ids).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(ids);
  }
}
