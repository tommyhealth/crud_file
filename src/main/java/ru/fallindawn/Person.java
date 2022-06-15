package ru.fallindawn;

public class Person {
    private int id;
    private String firstName;
    private String secondName;
    private String patronymic;

    public Person(int id, String firstName, String secondName, String patronimyc) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronimyc;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.secondName + " " + this.patronymic;
    }

}

