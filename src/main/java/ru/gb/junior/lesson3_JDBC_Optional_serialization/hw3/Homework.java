package ru.gb.junior.lesson3_JDBC_Optional_serialization.hw3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Homework {

    /*
        С помощью JDBC, выполнить следующие пункты:
        1+. Создать таблицу Person (скопировать код с семинара)
        2. Создать таблицу Department (id bigint primary key, name varchar(128) not null)
        3. Добавить в таблицу Person поле department_id типа bigint (внешний ключ)
        4. Написать метод, который загружает Имя department по Идентификатору    person
        5. * Написать метод, который загружает Map<String, String>, в которой маппинг person.name ->
    department.name Пример: [{"person #1", "department #1"}, {"person #2", "department #3}]
        6. ** Написать метод,    который загружает
    Map<String, List<String>>, в которой маппинг department.name -> <person.name>
    Пример: [{"department #1", ["person #1", "person #2"]}, {"department #2", ["person #3", "person #4"]} ]
        7. *** Создать классы-обертки над таблицами, и в пунктах 4, 5, 6 возвращать объекты.
     */

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
            // 1. Создать таблицу Person (скопировать код с семинара)
            createPersonTable(connection);
            // 2. Создать таблицу Department (id bigint primary key, name varchar(128) not null)
            createDepartmentTable(connection);
            // 3. Добавить колонку department_id и внешний ключ в таблицу Person
            addDepartmentColumnToPerson(connection);

            // Вставить данные в таблицы
            insertPersonData(connection);
            insertDepartmentData(connection);

            // Обновить таблицу person, добавив department_id
            updatePersonDepartment(connection);

            System.out.println();
        } catch (SQLException e) {
            System.err.println("Во время подключения произошла ошибка: " + e.getMessage());
        }
    }

    /**
     * Создает таблицу Person
     *
     * @param connection   соединение с БД
     * @throws SQLException ошибка подключения к SQL
     */
    protected static void createPersonTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
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
     * Создает таблицу Department
     *
     * @param connection   соединение с БД
     * @throws SQLException ошибка подключения к SQL
     */
    protected static void createDepartmentTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table department (
                        id bigint primary key,
                        name varchar(128) not null
                    )
                    """);
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Добавляет колонку department_id и внешний ключ в таблицу person
     *
     * @param connection соединение
     * @throws SQLException ошибка подключения к SQL
     */
    protected static void addDepartmentColumnToPerson(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Сначала добавляем колонку department_id
            statement.execute("""
                    alter table person
                    add column department_id bigint""");
            // Затем добавляем ограничение внешнего ключа
            statement.execute("""
                    alter table person
                        add constraint fk_department foreign key (department_id) references department(id)""");

        } catch (SQLException e) {
            System.err.println("Во время изменения таблицы Person произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Вставляет данные в таблицу person
     *
     * @param connection соединение
     * @throws SQLException ошибка подключения к SQL
     */
    protected static void insertPersonData(Connection connection) throws SQLException {
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
     * Вставляет данные в таблицу department
     *
     * @param connection соединение
     * @throws SQLException ошибка подключения к SQL
     */
    protected static void insertDepartmentData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            StringBuilder insertQuery = new StringBuilder("insert into department(id, name) values\n");
            for (int i = 1; i <= 5; i++) {
                insertQuery.append(String.format("(%s, 'Department #%s')", i, i));

                if (i != 5) {
                    insertQuery.append(",\n");
                }
            }

            int insertCount = statement.executeUpdate(insertQuery.toString());
            System.out.println("Вставлено строк: " + insertCount);
        }
    }

    /**
     * Обновляет таблицу person, добавляя department_id
     *
     * @param connection соединение
     * @throws SQLException ошибка подключения к SQL
     */
    protected static void updatePersonDepartment(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            StringBuilder updateQuery = new StringBuilder();
            for (int i = 1; i <= 10; i++) {
                long departmentId = ThreadLocalRandom.current().nextLong(1, 6); // Генерируем случайный ID отдела
                updateQuery.append(String.format("update person set department_id = %s where id = %s;\n", departmentId, i));
            }
            statement.executeUpdate(updateQuery.toString());
        } catch (SQLException e) {
            System.err.println("Во время обновления таблицы Person произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Пункт 4
     */
    private static String getPersonDepartmentName(Connection connection, long personId) throws SQLException {
        // FIXME: Ваш код тут
        throw new UnsupportedOperationException();
    }

    /**
     * Пункт 5
     */
    private static Map<String, String> getPersonDepartments(Connection connection) throws SQLException {
        // FIXME: Ваш код тут
        throw new UnsupportedOperationException();
    }

    /**
     * Пункт 6
     */
    private static Map<String, List<String>> getDepartmentPersons(Connection connection) throws SQLException {
        // FIXME: Ваш код тут
        throw new UnsupportedOperationException();
    }

}
