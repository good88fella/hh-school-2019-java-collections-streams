package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count; //>>> удаляем строку, так как хранить значение в этом поле нет смысла

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    /*
    if (persons.size() == 0) {
      return Collections.emptyList(); >>> удаляем, стрим создаст пустую коллекцию
    }
    */
    /*
    persons.remove(0); >>> удаляем эту строку, так как список persons может быть не изменяемым, следовательно при
    попытке удалить эелемент получим UnsupportedOperationException.
    Чтобы пропустить первый элемент добавим к стриму метод skip.
    */
    return persons.stream()
            .map(Person::getFirstName)
            .skip(1)
            .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    /*
    return getNames(persons).stream().distinct().collect(Collectors.toSet()); >>> заменяем эту строку, так как
    можно короче и быстрее.
    Сразу создаем коллекцию необходимого размера, тогда как стрим будет добавлять элементы по одному, растягивая
    коллекцию по мере необходимости.
    */
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    /*
    String result = ""; >>> удаляем переменную
    if (person.getSecondName() != null) {
      result += person.getSecondName();
    }
    заменяем условия на стрим
    */
    /*
    if (person.getFirstName() != null) {
      result += " " + person.getFirstName(); >>> заменяем строку с использованием тернарного оператора
    }
    Объект person может быть создан с помощью контруктора "public Person(Integer id, String firstName, Instant createdAt)",
    в таком случае перед именем будет стоять лишний пробел.
    */
    /*
    if (person.getSecondName() != null) {
      result += " " + person.getSecondName();
    }
    заменяем secondName на MiddleName
    */
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
            .filter(p -> p != null)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    /*
    Map<Integer, String> map = new HashMap<>(1);
    for (Person person : persons) {
      if (!map.containsKey(person.getId())) {
        map.put(person.getId(), convertPersonToString(person));
      }
    }
    */
    return persons.stream()
            .collect(Collectors.toMap(Person::getId, p -> convertPersonToString(p),
                    (existing, replacement) -> existing));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    /*
    boolean has = false; >>> удаляем строку, так как нет необходимости в переменной
    for (Person person1 : persons1) {
      for (Person person2 : persons2) {
        if (person1.equals(person2)) {
          has = true;
        }
      }
    }
    Создаем HashSet из первой коллекции, теперь временная сложность обращения к элементу O(1).
    Теперь перебираем вторую коллекцию и смотрим есть ли совпадения с первой, в итоге сложность O(n).
    */
    Set<Person> persons = new HashSet<>(persons1);
    return persons2.stream().anyMatch(persons::contains);
  }

  //Выглядит вроде неплохо...

  /*
  public long countEven(Stream<Integer> numbers) { >>> заменяем стрим на коллекцию
    count = 0; //>>> удаляем строку, переменная не нужна
    numbers.filter(num -> num % 2 == 0).forEach(num -> count++); >>> заменяем forEach на метод count
    return count;
  }
  В случае многопоточности результат может быть неверным, так как другой поток может влезть и обнулить,
  изменить переменную count, которая в данный момент используется еще каким-то потоком.
  */
  public long countEven(Collection<Integer> numbers) {
    return numbers.stream()
            .filter(num -> num % 2 == 0)
            .count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true; // заменяем на тру)
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
