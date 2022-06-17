package ru.fallindawn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "crud_persons")
public class Person {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String secondName;
    private String patronymic;

    @Override
    public String toString() {
        return this.firstName + " " + this.secondName + " " + this.patronymic;
    }

}

