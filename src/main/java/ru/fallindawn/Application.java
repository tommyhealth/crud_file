package ru.fallindawn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Application {
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

    public static List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM crud_persons";
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
}
