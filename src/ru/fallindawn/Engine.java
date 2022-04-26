package ru.fallindawn;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Engine {
    public static final String FILE_NAME = "text.txt";
    private static int totalKey;
    private static final Map<Integer, Person> persons = new HashMap<>();

    public void start() {
        BufferedReader reader;
        if (Objects.isNull(persons)) {
            System.err.println("Возникла внутренняя ошибка программы");
            return;
        }
        try {
            String fioString = " ";
            reader = new BufferedReader(new FileReader(FILE_NAME));
            for (int i = 0; i < Files.lines(Path.of(FILE_NAME)).count(); i++) {
                fioString = reader.readLine();
                persons.put(getIdFromString(fioString), personFromString(fioString));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void work() {
        if (Objects.isNull(persons)) {
            System.err.println("Возникла внутренняя ошибка программы");
            return;
        }

        totalKey = persons.size();

        Menu menu = new Menu();
        while (true) {
            menu.showMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Укажите действие");
            int number = scanner.nextInt();
            switch (number) {
                case 1: {
                    persons.forEach((key, value) -> {
                        System.out.println("ID: " + key + " Value: " + value);
                    });
                    break;
                }
                case 2: {
                    Scanner newPersonScanner = new Scanner(System.in);
                    System.out.println("Введите ФИО");
                    String fioString = newPersonScanner.nextLine();
                    persons.put(++totalKey, personFromString(fioString));
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
                    String fioString = renewPerson2.nextLine();
                    persons.put(id, personFromString(fioString));
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
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
                        persons.forEach((id, person) -> {
                            try {
                                Files.write(Path.of(FILE_NAME), (id + "/" + person + "\n").getBytes(), StandardOpenOption.APPEND);
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

    private Person personFromString(String line) {
        String[] fullName = line.split(" ");
        return new Person(fullName[0].substring(fullName[0].indexOf('/') + 1), fullName[1], fullName[2]);
    }

    private int getIdFromString(String line) {
        return Integer.parseInt(line.substring(0, 1));
    }
}
