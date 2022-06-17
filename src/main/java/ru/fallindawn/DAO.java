package ru.fallindawn;

import java.util.List;

public interface DAO {

    List<Person> getAll();

    Person getPersonById(int id);

    void save(String fio);

    void update(int id, String fio);

    void delete(Integer id);
}
