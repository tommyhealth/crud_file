package ru.fallindawn;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class PersonHibernateDAO implements DAO {

    private static final PersonHibernateDAO PERSON_HIBERNATE_DAO = new PersonHibernateDAO();

    public static PersonHibernateDAO getInstance() {
        return PERSON_HIBERNATE_DAO;
    }

    public Person getPersonById (int id) {
        return HibernateUtil.getSessionFactory().openSession().get(Person.class, id);
    }

    public void save(String fio) {
        Person person = personFromString(fio);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction beginTransaction = session.beginTransaction();
        session.save(person);
        beginTransaction.commit();
        session.close();
    }

    public void update(int id, String fio) {
        Person person = personFromString(fio);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction beginTransaction = session.beginTransaction();
        session.update(person);
        beginTransaction.commit();
        session.close();
    }

    public void delete(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction beginTransaction = session.beginTransaction();
        session.delete(id);
        beginTransaction.commit();
        session.close();
    }

    public List<Person> getAll() {
        List<Person> persons = (List<Person>)  HibernateUtil.getSessionFactory().openSession().createQuery("From ru.fallindawn.Person").list();
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
