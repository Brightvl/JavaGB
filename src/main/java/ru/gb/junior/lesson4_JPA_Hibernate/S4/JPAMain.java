package ru.gb.junior.lesson4_JPA_Hibernate.S4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.gb.junior.lesson4_JPA_Hibernate.S4.entity.Author;
import ru.gb.junior.lesson4_JPA_Hibernate.S4.entity.AuthorBook;
import ru.gb.junior.lesson4_JPA_Hibernate.S4.entity.Book;


public class JPAMain {

  /*
  Подготовка

    console docker:
    docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=pass -d postgres:16
    docker exec -it postgres psql -U postgres

   postgres:
    \d информация о базе
    create table author (id bigint primary key, name varchar(256));
    insert into author(id,name) values(1,'John');

   */


    // employee(id, department_id)
    // department(id)

    // Employee e = session.find(Employee.class, 1L);
    // Department d2 = session.find(Department.class, 2L);
    // e.setDepartment(d2);
    // session.merge(e)

    public static void main(String[] args) {
        // ORM - Object Relation Model - объекты рассматриваются как таблицы
        // JPA - Jakarta Persistence API - абстракция которая позволяет разговаривать с БД на языке объектов
        // Hibernate - одна из реализаций JPA
        // EclipseLink - тоже реализация JPA

        // connection poll
        // hikari

        // Читаем файл configuration в ресурсах
        Configuration configuration = new Configuration();
        configuration.configure(); // !!! иначе cfg.xml не прочитается

        // фабрика сессий - аналог Connection в JDBC
        // sessionFactory <-> connection
        // Session <-> Statement
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {

            withSession(sessionFactory);

            //  withSessionCRUD(sessionFactory);
        }
    }

    /**
     * Открываем сессию
     *
     * @param sessionFactory
     */
    private static void withSession(SessionFactory sessionFactory) {
        showAuthor(sessionFactory);
        createAuthor(sessionFactory);
    }

    /**
     * GET
     *
     * @param sessionFactory
     */
    private static void showAuthor(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Author author = session.find(Author.class, 1L);
            System.out.println(author);

//            for (Book book : author.getBooks()) {
//                System.out.println(book);
//            }
        }
    }

    /**
     * CREATE
     *
     * @param sessionFactory
     */
    private static void createAuthor(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Author author = new Author();
            author.setId(22L);
            author.setName("Author");

//            Book book = new Book();
//            book.setId(1L);
//            book.setName("Book");
//
//            AuthorBook authorBook = new AuthorBook();
//            authorBook.setId(1L); // id
//            authorBook.setAuthor(author); // author_id
//            authorBook.setBook(book); // book_id
//
            Transaction tx = session.beginTransaction();
            session.persist(author); //вместо save()
//            session.persist(book);
//            if (true) {
//                throw new RuntimeException();
//            }
//            session.persist(authorBook);
            tx.commit();
        }
    }

    // create read update delete
    private static void withSessionCRUD(SessionFactory sessionFactory) {
        // READ
        try (Session session = sessionFactory.openSession()) {
            // session <-> statement
            Author author = session.find(Author.class, 1L);
            System.out.println("Author(1) = " + author);
        }
        // CREATE
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Author author = new Author();
            author.setId(22L);
            author.setName("Author #22");

            session.persist(author); // insert
            tx.commit();
        }

        // UPDATE
        try (Session session = sessionFactory.openSession()) {
            Author toUpdate = session.find(Author.class, 22L);
            session.detach(toUpdate);
            toUpdate.setName("UPDATED");

            Transaction tx = session.beginTransaction();
            session.merge(toUpdate); // update
            tx.commit();
        }

        //DELETE
        try (Session session = sessionFactory.openSession()) {
            Author toDelete = session.find(Author.class, 1L);

            Transaction tx = session.beginTransaction();
            session.remove(toDelete); // delete
            tx.commit();
        }


    }

}
