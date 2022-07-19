package ru.fallindawn;

import ru.fallindawn.service.PersonHibernateService;

public class Main {

    public static void main(String[] args) {
        PersonHibernateService personHibernateService = new PersonHibernateService();
        personHibernateService.run();
    }
}

