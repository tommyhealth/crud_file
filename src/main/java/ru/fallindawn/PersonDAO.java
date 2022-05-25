package ru.fallindawn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
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

    public List<Person> getAll() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM persons";
            ResultSet resultSet = statement.executeQuery(SQL);

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

    public Person getPersonById(int id) {
        Person person = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM persons WHERE id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

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

    public void save(String fio) {
        Person person = personFromString(fio);

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO persons VALUES(default, current_timestamp, ?, ?, ?)");

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getSecondName());
            preparedStatement.setString(3, person.getPatronymic());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, String fio) {
        Person updatePerson = personFromString(fio);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE persons SET first_name=?, second_name=?, patronimyc=? WHERE id=?");

            preparedStatement.setString(1, updatePerson.getFirstName());
            preparedStatement.setString(2, updatePerson.getSecondName());
            preparedStatement.setString(3, updatePerson.getPatronymic());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement =
                null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM persons WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
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
