package org.crudSpringApp.dao;


import org.crudSpringApp.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;
    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT,"Vlad"));
        people.add(new Person(++PEOPLE_COUNT, "Vilia"));
        people.add(new Person(++PEOPLE_COUNT, "Nate"));
        people.add(new Person(++PEOPLE_COUNT, "Maria"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        return people.stream().filter(person->person.getId()==id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);

    }
}
