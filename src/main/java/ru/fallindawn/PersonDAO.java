package ru.fallindawn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements DAO {
    private static final PersonDAO PERSON_DAO = new PersonDAO();
    private static final String USER = "postgres";
    private static final String PASSWORD = "89198768049";
    private static final String URL = "jdbc:postgresql://localhost:5433/crud_persons";

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private PersonDAO() {

    }

    public static PersonDAO getInstance() {
        return PERSON_DAO;
    }

    @Override
    public List<Person> getAll() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SELECT_ALL_PERSONS_QUERY = "SELECT * FROM persons";
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_PERSONS_QUERY);
            
            while ((resultSet.next())) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setSecondName(resultSet.getString("second_name"));
                person.setPatronymic(resultSet.getString("patronimyc"));
                people.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }

    @Override
    public Person getPersonById(int id) {
        Person person = null;

        try {
            PreparedStatement SELECT_PERSON_BY_ID_QUERY =
                    connection.prepareStatement("SELECT * FROM persons WHERE id=?");

            SELECT_PERSON_BY_ID_QUERY.setInt(1, id);

            ResultSet resultSet = SELECT_PERSON_BY_ID_QUERY.executeQuery();

            resultSet.next();

            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setFirstName(resultSet.getString("first_name"));
            person.setSecondName(resultSet.getString("second_name"));
            person.setPatronymic(resultSet.getString("patronimyc"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return person;
    }

    @Override
    public void save(String fio) {
        Person person = personFromString(fio);

        try {
            PreparedStatement SAVE_PERSON_QUERY =
                    connection.prepareStatement("INSERT INTO persons VALUES(default, current_timestamp, ?, ?, ?)");

            SAVE_PERSON_QUERY.setString(1, person.getFirstName());
            SAVE_PERSON_QUERY.setString(2, person.getSecondName());
            SAVE_PERSON_QUERY.setString(3, person.getPatronymic());

            SAVE_PERSON_QUERY.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(int id, String fio) {
        Person updatePerson = personFromString(fio);
        try {
            PreparedStatement UPDATE_PERSON_QUERY =
                    connection.prepareStatement("UPDATE persons SET first_name=?, second_name=?, patronimyc=? WHERE id=?");

            UPDATE_PERSON_QUERY.setString(1, updatePerson.getFirstName());
            UPDATE_PERSON_QUERY.setString(2, updatePerson.getSecondName());
            UPDATE_PERSON_QUERY.setString(3, updatePerson.getPatronymic());
            UPDATE_PERSON_QUERY.setInt(4, id);

            UPDATE_PERSON_QUERY.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement DELETE_PERSON_BY_ID_QUERY =
                null;
        try {
            DELETE_PERSON_BY_ID_QUERY = connection.prepareStatement("DELETE FROM persons WHERE id=?");

            DELETE_PERSON_BY_ID_QUERY.setInt(1, id);

            DELETE_PERSON_BY_ID_QUERY.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Person personFromString(String fio) {
        Person person = new Person();
        String[] f_i_o = fio.split(" ");
        person.setFirstName(f_i_o[0]);
        person.setSecondName(f_i_o[1]);
        person.setPatronymic(f_i_o[2]);
        return person;
    }
}
