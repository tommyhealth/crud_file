package ru.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.nio.file.StandardOpenOption;


public class Main {
    private static int totalKey = 3;

    public static void main(String[] args) {
        Map<Integer, Person> persons = new HashMap<>();
        BufferedReader reader;
        try {
            String line = " ";
            reader = new BufferedReader(new FileReader("text.txt"));
            for (int i = 0; i < Files.lines(Path.of("text.txt")).count(); i++) {
                line = reader.readLine();
                String[] lineArray = line.split(" ");
                Person person = new Person(lineArray[0].substring(lineArray[0].indexOf('/') + 1), lineArray[1], lineArray[2]);
                persons.put(Integer.parseInt(lineArray[0].substring(0, 1)), person);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Menu menu = new Menu();
        while (true) {
            menu.showMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Укажите действие");
            int number = scanner.nextInt();
            switch (number) {
                case 1: {
                    for (Map.Entry entry : persons.entrySet()) {
                        System.out.println("ID: " + entry.getKey() + " Value: "
                                + entry.getValue());
                    }
                    break;
                }
                case 2: {
                    Scanner newPerson = new Scanner(System.in);
                    System.out.println("Введите ФИО");
                    String text = newPerson.nextLine();
                    String[] lineArray = text.split(" ");
                    Person person = new Person(lineArray[0], lineArray[1], lineArray[2]);
                    persons.put(++totalKey, person);
                    break;
                }
                case 3: {
                    Scanner personId = new Scanner(System.in);
                    System.out.println("Введите ID");
                    int id = personId.nextInt();
                    persons.remove(id);
                    break;
                }
                case 4: {
                    Scanner renewPerson = new Scanner(System.in);
                    System.out.println("Введите ID");
                    int id = renewPerson.nextInt();
                    Scanner renewPerson2 = new Scanner(System.in);
                    System.out.println("Введите новые данные");
                    String newData = renewPerson2.nextLine();
                    String[] lineArray = newData.split(" ");
                    Person person = new Person(lineArray[0], lineArray[1], lineArray[2]);
                    persons.put(id, person);
                    break;
                }
                case 5: {
                    Scanner seePerson = new Scanner(System.in);
                    System.out.println("Введите ID");
                    int id = seePerson.nextInt();
                    System.out.println(persons.get(id));
                    break;
                }
                case 0: {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("text.txt", false))) {
                        persons.forEach((id, person) -> {
                            try {
                                Files.write(Path.of("text.txt"), (id + "/" + person + "\n").getBytes(), StandardOpenOption.APPEND);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        System.exit(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                default: {
                    System.out.println("Значение не найдено");
                    break;
                }
            }
        }
    }
}
