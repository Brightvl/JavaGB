package ru.gb.junior.lesson3_JDBC_Optional_serialization.s3_JDBC_Optional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class JDBC {
    /*
     JDBC Java DataBase Connectivity - набор классов и интерфейсов (в JDK), с помощью которых
     можно организовать взаимодействие с реляционными БД

     java.sql.Driver - интерфейс, в котором прописано взаимодействие с БД
     org.postgresql.Driver, oracle.Driver, org.h2.Driver, ...mysql.Driver, ...

     DriverManager - класс, в котором зарегистрированы все драйверы

     Connection - интерфейс, отвечающий за соединение. createStatement - создает запрос для SQL инструкций
     Statement - интерфейс, отвечающий за запрос в сторону БД
     PreparedStatement - расширяет возможности Statement и позволяет избежать sql-инъекций
     (по сути атака на сервер, когда можно с помощью запроса с HTTP сделать с бд что угодно
     пример: String age = "55; delete from person;" с обычным statement базу просто снесет
     варианты спасения либо парсить параметры )

     boolean execute - применилось ли изменение на стороне БД
     int executeUpdate - сколько применилось изменений на стороне БД
     ResultSet executeQuery возвращает ResultSet - это таблица с данными которые вернула БД, в качестве элементов строки
     void set - для того чтобы изменить параметр

     */
    public static void main(String[] args) {
    /* Подключение к БД
        пример postgres -> jdbc:postgres:user@password:host:port///
        h2 - бд которая существует только во время работы программы
     */
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
            createPersonTable(connection);
            insertPersonData(connection);
            String age = "55";
            System.out.println("Person с возрастом 55: " + selectNamesByAge(connection, age));

            updateData(connection);
            selectData(connection);
        } catch (SQLException e) {
            System.err.println("Во время подключения произошла ошибка: " + e.getMessage());
        }
    }

    /**
     * Создает таблицу
     *
     * @param connection соединение с БД
     * @throws SQLException ошибка подключения к SQL
     */
    public static void createPersonTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // выполняет заданную инструкцию SQL
            statement.execute("""
                    create table person (
                      id bigint primary key,
                      name varchar(256),
                      age integer,
                      active boolean
                    )
                    """);
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Вставить данные
     *
     * @param connection соединение
     * @throws SQLException ошибка подключения к SQL
     */
    public static void insertPersonData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            StringBuilder insertQuery = new StringBuilder("insert into person(id, name, age, active) values\n");
            for (int i = 1; i <= 10; i++) {
                int age = ThreadLocalRandom.current().nextInt(20, 60);
                boolean active = ThreadLocalRandom.current().nextBoolean();
                insertQuery.append(String.format("(%s, '%s', %s, %s)", i, "Person #" + i, age, active));

                if (i != 10) {
                    insertQuery.append(",\n");
                }
            }

            int insertCount = statement.executeUpdate(insertQuery.toString());
            System.out.println("Вставлено строк: " + insertCount);
        }
    }

    /**
     * Обновление БД
     *
     * @param connection соединение
     * @throws SQLException ошибка подключения к SQL
     */
    private static void updateData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int updateCount = statement.executeUpdate("update person set active = true where id > 5");
            System.out.println("Обновлено строк: " + updateCount);
        }
    }

    // static Optional<String> selectNameById(long id) {
    //   ...
    // }


    /**
     * Получить имена по возрасту
     *
     * @param connection соединение
     * @param age        возраст
     * @return список
     * @throws SQLException
     */
    private static List<String> selectNamesByAge(Connection connection, String age) throws SQLException {
        // без проверок на инъекции
        //    try (Statement statement = connection.createStatement()) {
        //      statement.executeQuery("select name from person where age = " + age);
        //      // where age = 1 or 1=1
        //    }

        try (PreparedStatement statement =
                     connection.prepareStatement("select name from person where age = ?")) {
            statement.setInt(1, Integer.parseInt(age));
            ResultSet resultSet = statement.executeQuery();

            List<String> names = new ArrayList<>();
            while (resultSet.next()) {
                names.add(resultSet.getString("name"));
            }
            return names;
        }
    }

    /**
     * Получить данные
     *
     * @param connection соединение
     * @throws SQLException ошибка подключения к SQL
     */
    public static void selectData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    select id, name, age
                    from person
                    where active is true""");

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                // persons.add(new Person(id, name, age))
                System.out.println("Найдена строка: [id = " + id + ", name = " + name + ", age = " + age + "]");
            }
        }
    }

}
