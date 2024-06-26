package ru.gb.junior.lesson3_JDBC_Optional_serialization.hw3;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.List;
import java.util.Map;

import static ru.gb.junior.lesson3_JDBC_Optional_serialization.s3_JDBC_Optional.JDBC.createPersonTable;
import static ru.gb.junior.lesson3_JDBC_Optional_serialization.s3_JDBC_Optional.JDBC.insertPersonData;

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
            insertPersonData(connection);

            // 2. Создать таблицу Department (id bigint primary key, name varchar(128) not null)
            createDepartmentTable(connection);
            insertDepartmentData(connection);

            // 3. Добавить колонку department_id и внешний ключ в таблицу Person
            addColumnToPerson(connection);

            // 4. Написать метод, который загружает Имя department по Идентификатору person
            System.out.println(getPersonDepartmentName(connection, 2));


            System.out.println();
        } catch (SQLException e) {
            System.err.println("Во время подключения произошла ошибка: " + e.getMessage());
        }
    }


    /**
     * Создает таблицу Department
     *
     * @param connection соединение с БД
     * @throws SQLException ошибка подключения к SQL
     */
    private static void createDepartmentTable(Connection connection) throws SQLException {
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
     * Вставляет данные в таблицу department
     *
     * @param connection соединение
     * @throws SQLException ошибка подключения к SQL
     */
    private static void insertDepartmentData(Connection connection) throws SQLException {
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
     * Добавить в таблицу Person поле department_id типа bigint (внешний ключ)
     *
     * @param connection соединение
     */
    private static void addColumnToPerson(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    ALTER TABLE person
                    ADD COLUMN department_id bigint;""");

            statement.execute("""
                    ALTER TABLE person
                    ADD CONSTRAINT fk_department_id
                    FOREIGN KEY (department_id)
                    REFERENCES department(id)""");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Пункт 4
     */
    private static String getPersonDepartmentName(Connection connection, long personId) throws SQLException {

        try (Statement statement = connection.prepareStatement("""
                """)) {
        }
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

@Setter
@Getter
class Person {
    private long id;
    private String name;
    private int age;
    private boolean active;
    private Long departmentId;

    public Person(long id, String name, int age, boolean active, Long departmentId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.active = active;
        this.departmentId = departmentId;
    }

}

@Setter
@Getter
class Department {
    private long id;
    private String name;

    public Department(long id, String name) {
        this.id = id;
        this.name = name;
    }

}
