package ru.fallindawn.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.fallindawn.HibernateUtil;
import ru.fallindawn.dao.model.Person;

import java.util.List;

public class PersonHibernateDAO implements DAO {

    private static final PersonHibernateDAO PERSON_HIBERNATE_DAO = new PersonHibernateDAO();

    public static PersonHibernateDAO getInstance() {
        return PERSON_HIBERNATE_DAO;
    }

    public Person getPersonById (int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Person person = session.get(Person.class, id);
        session.close();
        return person;
    }

    public void save(String fio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Person person = personFromString(fio);
        Transaction beginTransaction = session.beginTransaction();
        session.persist(person);
        beginTransaction.commit();
        session.close();
    }

    public void update(int id, String fio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Person person = personFromString(fio);
        Transaction beginTransaction = session.beginTransaction();
        Person personBd = getPersonById(id);
        personBd.setFirstName(person.getFirstName());
        personBd.setSecondName(person.getSecondName());
        personBd.setPatronymic(person.getPatronymic());
        session.merge(personBd);
        beginTransaction.commit();
        session.close();
    }

    public void delete(Integer id) {
         Session session = HibernateUtil.getSessionFactory().openSession();
         Transaction beginTransaction = session.beginTransaction();
         Person person = getPersonById(id);
         session.remove(person);
         beginTransaction.commit();
         session.close();
    }

    public List<Person> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Person> persons = session.createQuery("From Person", Person.class).list();
        session.close();
        return persons;
    }

    private Person personFromString(String fio) {
        Person person = new Person();
        String[] f_i_o = fio.split(" ");
        person.setFirstName(f_i_o[0]);
        person.setSecondName(f_i_o[1]);
        person.setPatronymic(f_i_o[2]);
        return person;
    }
}
