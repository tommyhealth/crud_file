package ru.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Engine {
    private static int totalKey;
    private static final Map<Integer, Person> persons = new HashMap<>();

    public void start() {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void work() {
        totalKey = persons.size();

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
                    Scanner newPersonScanner = new Scanner(System.in);
                    System.out.println("Введите ФИО");
                    String text = newPersonScanner.nextLine();
                    String[] lineArray = text.split(" ");
                    Person person = new Person(lineArray[0], lineArray[1], lineArray[2]);
                    persons.put(++totalKey, person);
                    break;
                }
                case 3: {
                    Scanner personIdScanner = new Scanner(System.in);
                    System.out.println("Введите ID");
                    int id = personIdScanner.nextInt();
                    persons.remove(id);
                    break;
                }
                case 4: {
                    Scanner renewPersonScanner = new Scanner(System.in);
                    System.out.println("Введите ID");
                    int id = renewPersonScanner.nextInt();
                    Scanner renewPerson2 = new Scanner(System.in);
                    System.out.println("Введите новые данные");
                    String newData = renewPerson2.nextLine();
                    String[] lineArray = newData.split(" ");
                    Person person = new Person(lineArray[0], lineArray[1], lineArray[2]);
                    persons.put(id, person);
                    break;
                }
                case 5: {
                    Scanner seePersonScanner = new Scanner(System.in);
                    System.out.println("Введите ID");
                    int id = seePersonScanner.nextInt();
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
