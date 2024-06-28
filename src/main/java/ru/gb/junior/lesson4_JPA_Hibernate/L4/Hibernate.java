package ru.gb.junior.lesson4_JPA_Hibernate.L4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;


import java.util.List;

public class Hibernate {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";


    public static void con() {
        Connector connector = new Connector();
        try (Session session = connector.getSession()){
            // Запись в БД
            Magic magic = new Magic("Волшебная стрела", 10, 0, 0);
            session.beginTransaction();
            session.save(magic);
            magic = new Magic("Молния", 25, 0, 0);
            session.save(magic);
            magic = new Magic("Каменная кожа", 0, 0, 6);
            session.save(magic);
            magic = new Magic("Жажда крови", 0, 6, 0);
            session.save(magic);
            magic = new Magic("Проклятие", 0, -3, 0);
            session.save(magic);
            magic = new Magic("Лечение", -30, 0, 0);
            session.save(magic);
            session.getTransaction().commit();


            // получить список всех объектов
            List<Magic> books = session.createQuery("FROM Magic ", Magic.class).getResultList();
            books.forEach(System.out::println);

            // изменение объекта
            String hql = "FROM Magic WHERE id = :id";
            Query<Magic> query = session.createQuery(hql, Magic.class);
            query.setParameter("id", 4);
            Magic magic1 = query.getSingleResult();
            System.out.println(magic1);
            magic1.setAttBonus(100);
            magic1.setName("Ярость");
            session.beginTransaction();
            session.update(magic1);
            session.getTransaction().commit();

            //Удаление
            Transaction t = session.beginTransaction();
            List<Magic> magics = session.createQuery("FROM Magic", Magic.class).getResultList();
            magics.forEach(m -> {session.delete(m);});
            t.commit();

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}
