package ru.fallindawn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements DAO {
    private static final PersonDAO PERSON_DAO = new PersonDAO();

    private static final String USER = "postgres";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:postgresql://localhost:5433/crud_persons";

    public static final String SELECT_FROM_PERSONS_WHERE_ID = "SELECT * FROM persons WHERE id=?";
    public static final String SELECT_ALL_PERSONS_QUERY = "SELECT * FROM persons";
    public static final String SAVE_PERSON_QUERY = "INSERT INTO persons VALUES(default, current_timestamp, ?, ?, ?)";
    public static final String UPDATE_PERSON_QUERY = "UPDATE persons SET first_name=?, second_name=?, patronimyc=? WHERE id=?";
    public static final String DELETE_PERSON_BY_ID_QUERY = "DELETE FROM persons WHERE id=?";

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
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SELECT_FROM_PERSONS_WHERE_ID);

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

    @Override
    public void save(String fio) {
        Person person = personFromString(fio);

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SAVE_PERSON_QUERY);

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getSecondName());
            preparedStatement.setString(3, person.getPatronymic());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(int id, String fio) {
        Person updatePerson = personFromString(fio);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(UPDATE_PERSON_QUERY);

            preparedStatement.setString(1, updatePerson.getFirstName());
            preparedStatement.setString(2, updatePerson.getSecondName());
            preparedStatement.setString(3, updatePerson.getPatronymic());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        PreparedStatement preparedStatement =
                null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_PERSON_BY_ID_QUERY);

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
