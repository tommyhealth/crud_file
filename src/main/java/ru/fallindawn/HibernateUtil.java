package ru.fallindawn;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private  static SessionFactory sessionFactory;

   static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static SessionFactory getSessionFactory() { return sessionFactory; }




    //public static SessionFactory getSessionFactory() {
      //  if (sessionFactory == null) {
        //    try {
          //      Configuration configuration = new Configuration().configure();
            //    configuration.addAnnotatedClass(Person.class);
              //  StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                //sessionFactory = configuration.buildSessionFactory(builder.build());
//
  //          } catch (Exception e) {
    //            System.out.println("Исключение!" + e);
      //      }
        //}
        //return sessionFactory;
    //}


    //public static SessionFactory getSessionFactory() {
      //  return sessionFactory;
    //}
}
