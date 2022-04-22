package ru.company;

public class Person {
    private String firstName;
    private String secondName;
    private String patronymic;

    public Person( String firstName, String secondName, String patronimyc) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronimyc;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.secondName + " " + this.patronymic;
    }

}

