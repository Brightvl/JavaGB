package ru.gb.junior.lesson4_JPA_Hibernate.hw4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.gb.junior.lesson4_JPA_Hibernate.hw4.entity.Post;
import ru.gb.junior.lesson4_JPA_Hibernate.hw4.entity.PostComment;
import ru.gb.junior.lesson4_JPA_Hibernate.hw4.entity.User;

import java.util.List;

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

     Подготовка:
     console docker:
     docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=pass -d postgres:16
   */

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            for (int i = 0; i < 10; i++) {
                createPost(sessionFactory);
            }
            System.out.println("Все посты:");
            getAllPosts(sessionFactory).forEach(System.out::println);

            System.out.println("\nОбновляем пост с ID 2:");
            updatePost(sessionFactory, 2L);
            System.out.println(getPostById(sessionFactory, 2L));

            System.out.println("\nУдаляем пост с ID 1:");
            deletePost(sessionFactory, 1L);
            System.out.println(getPostById(sessionFactory, 1L));

            Post post = getPostById(sessionFactory, 3L);
            System.out.println("\nДобавляем комментарий к посту с ID 3:");
            addPostCommentaryByPost(sessionFactory, post, "This is a comment");
            System.out.println("Комментарии к посту с ID 3:");
            getCommentsByPostId(sessionFactory, post.getId()).forEach(System.out::println);

            System.out.println("\nВсе посты пользователя с ID 3:");
            getPostsByUserId(sessionFactory, 3L).forEach(System.out::println);
        }
    }

    /**
     * Создать пост
     *
     * @param sessionFactory «экземпляр» Hibernate
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

    /**
     * Прочитать пост
     *
     * @param sessionFactory «экземпляр» Hibernate
     * @param id             идентификатор поста
     * @return пост
     */
    private static Post getPostById(SessionFactory sessionFactory, Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Post.class, id);
        }
    }

    /**
     * Обновить пост
     *
     * @param sessionFactory «экземпляр» Hibernate
     * @param id             идентификатор поста
     */
    private static void updatePost(SessionFactory sessionFactory, Long id) {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.find(Post.class, id);
            Transaction transaction = session.beginTransaction();
            post.setTitle("Updated Post");
            transaction.commit();
        }
    }

    /**
     * Удалить пост
     *
     * @param sessionFactory «экземпляр» Hibernate
     * @param id             идентификатор поста
     */
    private static void deletePost(SessionFactory sessionFactory, long id) {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.find(Post.class, id);
            Transaction transaction = session.beginTransaction();
            session.remove(post);
            transaction.commit();
        }
    }

    /**
     * Получить все посты
     *
     * @param sessionFactory «экземпляр» Hibernate
     * @return список постов
     */
    private static List<Post> getAllPosts(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Post", Post.class).list();
        }
    }

    /**
     * Добавить комментарий к посту
     *
     * @param sessionFactory «экземпляр» Hibernate
     * @param post           пост, к которому добавляется комментарий
     * @param textComment    текст комментария
     */
    private static void addPostCommentaryByPost(SessionFactory sessionFactory, Post post, String textComment) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            PostComment comment = new PostComment();
            comment.setText(textComment);
            comment.setPost(post);
            session.persist(comment);
            transaction.commit();
        }
    }

    /**
     * Прочитать комментарий по идентификатору
     *
     * @param sessionFactory «экземпляр» Hibernate
     * @param id             идентификатор комментария
     * @return комментарий
     */
    private static PostComment getPostCommentById(SessionFactory sessionFactory, Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(PostComment.class, id);
        }
    }

    /**
     * Получить все комментарии
     *
     * @param sessionFactory «экземпляр» Hibernate
     * @return список комментариев
     */
    private static List<PostComment> getAllPostComments(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PostComment", PostComment.class).list();
        }
    }

    /**
     * Загрузить все комментарии публикации
     *
     * @param sessionFactory «экземпляр» Hibernate
     * @param postId         идентификатор поста
     * @return список комментариев
     */
    private static List<PostComment> getCommentsByPostId(SessionFactory sessionFactory, Long postId) {
        try (Session session = sessionFactory.openSession()) {
            Query<PostComment> query = session.createQuery("from PostComment where post.id = :postId", PostComment.class);
            query.setParameter("postId", postId);
            return query.list();
        }
    }

    /**
     * Загрузить все публикации по идентификатору юзера
     *
     * @param sessionFactory «экземпляр» Hibernate
     * @param userId         идентификатор юзера
     * @return список публикаций
     */
    private static List<Post> getPostsByUserId(SessionFactory sessionFactory, Long userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post where user.id = :userId", Post.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    /**
     * Загрузить все комментарии по идентификатору юзера
     *
     * @param sessionFactory «экземпляр» Hibernate
     * @param userId         идентификатор юзера
     * @return список комментариев
     */
    private static List<PostComment> getCommentsByUserId(SessionFactory sessionFactory, Long userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<PostComment> query = session.createQuery("from PostComment where user.id = :userId", PostComment.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    /**
     * По идентификатору юзера загрузить юзеров, под чьими публикациями он оставлял комменты
     *
     * @param sessionFactory «экземпляр» Hibernate
     * @param userId         идентификатор юзера
     * @return список юзеров
     */
    private static List<User> getUsersWithCommentsFromUser(SessionFactory sessionFactory, Long userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "select distinct pc.post.user from PostComment pc where pc.user.id = :userId", User.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }
}
