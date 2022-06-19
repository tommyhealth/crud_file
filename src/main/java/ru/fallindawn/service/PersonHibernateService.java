package ru.fallindawn.service;

import ru.fallindawn.Menu;
import ru.fallindawn.dao.DAO;
import ru.fallindawn.dao.PersonHibernateDAO;

import java.util.Scanner;

public class PersonHibernateService {

    private final DAO hibernateDAO = PersonHibernateDAO.getInstance();

    public void run() {
        Menu menu = new Menu();
        while (true) {
            menu.showMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Укажите действие");
            int number = scanner.nextInt();
            switch (number) {
                case 1: {
                    hibernateDAO.getAll().forEach(person -> {
                        System.out.println("ID: " + person.getId() + " Value: " + person);
                    });
                    break;
                }
                case 2: {
                    Scanner newPersonScanner = new Scanner(System.in);
                    System.out.println("Введите ФИО");
                    String fioString = newPersonScanner.nextLine();
                    hibernateDAO.save(fioString);
                    break;
                }
                case 3: {
                    Scanner personIdScanner = new Scanner(System.in);
                    System.out.println("Введите ID");
                    int id = personIdScanner.nextInt();
                    hibernateDAO.delete(id);
                    break;
                }
                case 4: {
                    Scanner renewPersonScanner = new Scanner(System.in);
                    System.out.println("Введите ID");
                    int id = renewPersonScanner.nextInt();
                    Scanner renewPerson2 = new Scanner(System.in);
                    System.out.println("Введите новые данные");
                    String fullFioString = renewPerson2.nextLine();
                    hibernateDAO.update(id, fullFioString);
                    break;
                }
                case 5: {
                    Scanner seePersonScanner = new Scanner(System.in);
                    System.out.println("Введите ID");
                    int id = seePersonScanner.nextInt();
                    System.out.println(hibernateDAO.getPersonById(id));
                    break;
                }
                case 0: {
                    System.out.println("ПОКА!");
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Значение не найдено");
                    break;
                }
            }
        }
    }
}