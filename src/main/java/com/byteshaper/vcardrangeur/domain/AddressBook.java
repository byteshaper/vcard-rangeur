package com.byteshaper.vcardrangeur.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AddressBook {

  private final List<Person> persons;

  public AddressBook(List<Person> persons) {
    this.persons = persons;
  }

  public int getPersonCount() {
    return persons.size();
  }

  public Set<String> getDifferentPhoneNumbersTypes() {
    return persons.stream().map(p -> p.telephonNumbers).flatMap(List::stream).map(t -> t.type).collect(
        Collectors.toSet());
  }

  public long getPersonsWithoutNameCount() {
    return persons.stream().filter(p -> Person.UNKNOWN_PROPERTY.equals(p.fullName)).count();
  }

  public List<Person> getPersons() {
    return persons;
  }

  public Map<String, Integer> getDuplicates() {
    Map<String, Integer> nameToCount = new HashMap<>();
    
    persons.forEach(p -> nameToCount.computeIfPresent(p.fullName, (name, count) -> count + 1));
    persons.forEach(p -> nameToCount.computeIfAbsent(p.fullName, name -> 1));
    
    return nameToCount
        .keySet()
        .stream()
        .filter(name -> nameToCount.get(name) > 1)
        .collect(Collectors.toMap(name -> name, name -> nameToCount.get(name)));
  }
}
