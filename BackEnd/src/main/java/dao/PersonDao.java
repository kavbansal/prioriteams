package dao;

import exception.DaoException;
import model.Person;

import java.util.List;

public interface PersonDao {

    void add(Person ca) throws DaoException;
    List<Person> findAllPeople();
    List<Person> findPersonbyUsername(String username);
    List<Person> findPerson(String username, String password);
    List<Person> findAllProfessors();
    List<Person> findPersonbyPersonId(int pId);
}

