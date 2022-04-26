package ru.fallindawn;

public class Person {
    private final String firstName;
    private final String secondName;
    private final String patronymic;

    public Person(String firstName, String secondName, String patronimyc) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronimyc;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.secondName + " " + this.patronymic;
    }

}

