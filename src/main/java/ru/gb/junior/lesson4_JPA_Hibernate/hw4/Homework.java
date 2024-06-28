package ru.gb.junior.lesson4_JPA_Hibernate.hw4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.gb.junior.lesson4_JPA_Hibernate.hw4.entity.Post;

import static java.lang.module.ModuleDescriptor.read;

public class Homework {

    /*
     Используя hibernate, создать таблицы:
     1. Post (публикация) (id, title)
     2. PostComment (комментарий к публикации) (id, text, post_id)
     Написать стандартные CRUD-методы: создание, загрузка, удаление.

     Дополнительные задания:
     1.* В сущностях post и postComment добавить поля timestamp с датами.
     2.* Создать таблицу users(id, name) и в сущностях post и postComment добавить ссылку на юзера.
     3.* Реализовать методы:
     3.1 Загрузить все комментарии публикации
     3.2 Загрузить все публикации по идентификатору юзера
     3.3 ** Загрузить все комментарии по идентификатору юзера
     3.4 **** По идентификатору юзера загрузить юзеров, под чьими публикациями он оставлял комменты. userId -> List<User>
     Замечание: 1. Можно использовать ЛЮБУЮ базу данных (например, h2)
     */

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            // Написать стандартные CRUD-методы: создание, загрузка, удаление.
            createPost(sessionFactory);
            readPost(sessionFactory);

            updatePost(sessionFactory);
            readPost(sessionFactory);

            deletePost(sessionFactory);
            readPost(sessionFactory);
        }
    }

    /**
     * Создать пост
     *
     * @param sessionFactory
     */
    private static void createPost(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Post post = new Post();
            post.setTitle("First Post");
            session.persist(post);
            transaction.commit();
        }

    }

    private static void readPost(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.find(Post.class, 1);
            System.out.println(post);
        }

    }

    private static void updatePost(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.find(Post.class, 1);
            Transaction transaction = session.beginTransaction();
            post.setTitle("Update Post");
            transaction.commit();
        }
    }

    private static void deletePost(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.find(Post.class, 1);
            Transaction transaction = session.beginTransaction();
            session.remove(post);
            transaction.commit();
        }
    }


}
