package ru.gb.junior.lesson3_JDBC_Optional_serialization.hw3;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.gb.junior.lesson3_JDBC_Optional_serialization.s3_JDBC_Optional.JDBC.*;

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
            updatePersonDepartments(connection);

            selectAllData(connection, "person");

            // 4. Написать метод, который загружает Имя department по Идентификатору person
            System.out.println(getPersonDepartmentName(connection, 3));

            // 5.* Написать метод, который загружает Map<String, String>, в которой маппинг person.name ->
            //    department.name Пример: [{"person #1", "department #1"}, {"person #2", "department #3}]
            System.out.println(getPersonDepartments(connection));

            // 6. ** Написать метод,    который загружает
            //    Map<String, List<String>>, в которой маппинг department.name -> <person.name>
            //    Пример: [{"department #1", ["person #1", "person #2"]}, {"department #2", ["person #3", "person #4"]} ]
            System.out.println(getDepartmentPersons(connection));

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
        String query = """
                create table department (
                    id bigint primary key,
                    name varchar(128) not null
                )
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы department произошла ошибка: " + e.getMessage());
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
            System.out.println("Вставлено строк в department: " + insertCount);
        }
    }

    /**
     * Добавить в таблицу Person поле department_id типа bigint (внешний ключ)
     *
     * @param connection соединение
     */
    private static void addColumnToPerson(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            // сначала мутим саму колонку
            statement.execute("""
                    ALTER TABLE person
                    ADD COLUMN department_id bigint;""");
            // потом даем ей параметры
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
     * Обновить таблицу Person, добавив каждому person department
     *
     * @param connection соединение
     * @throws SQLException ошибка подключения к SQL
     */
    private static void updatePersonDepartments(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            for (int i = 1; i <= 10; i++) {
                long departmentId = (i % 5) + 1;
                statement.executeUpdate(String.format(
                        "UPDATE person SET department_id = %d WHERE id = %d", departmentId, i));
            }
        }
    }

    /**
     * Получить имя department по id person
     *
     * @param connection соединение
     * @param personId   id пользователя
     * @return строка
     * @throws SQLException исключение
     */
    private static String getPersonDepartmentName(Connection connection, long personId) throws SQLException {
        String query = """
                SELECT d.name
                FROM person p
                JOIN Department d ON p.department_id = d.id
                WHERE p.id = ?""";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, personId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    return "Не установлен!";
                }
            }
        }
    }

    /**
     * Получить все данные таблицы
     *
     * @param connection соединение
     * @throws SQLException ошибка подключения к SQL
     */
    private static void selectAllData(Connection connection, String tableName) throws SQLException {
        String query = String.format("SELECT * FROM %s", tableName);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    System.out.print(columnName + " = " + columnValue + "; ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Во время выполнения запроса произошла ошибка: " + e.getMessage());
            throw e;
        }
    }


    /**
     * Пункт 5 * Написать метод, который загружает Map<String, String>, в которой маппинг person.name -> department.name
     * Пример: [{"person #1", "department #1"}, {"person #2", "department #3}]
     */
    private static Map<String, String> getPersonDepartments(Connection connection) throws SQLException {
        HashMap<String, String> map = new HashMap<>();

        String query = """
                SELECT p.name AS person_name, d.name AS department_name
                FROM person p
                JOIN department d ON p.department_id = d.id
                """;

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            // если связанны join можно достать параметры из двух таблиц
            while (resultSet.next()) {
                String personName = resultSet.getString("person_name");
                String departmentName = resultSet.getString("department_name");
                map.put(personName, departmentName);
            }

            return map;
        }
    }

    /**
     * Пункт 6 ** Написать метод,который загружает Map<String, List<String>>, в которой маппинг department.name ->
     * <person.name> Пример: [{"department #1", ["person #1", "person #2"]}, {"department #2", ["person #3", "person
     * #4"]} ]
     */
    private static Map<String, List<String>> getDepartmentPersons(Connection connection) throws SQLException {
        HashMap<String, List<String>> map = new HashMap<>();

        String query = """
                SELECT p.name AS person_name, d.name AS department_name
                FROM person p
                JOIN department d ON p.department_id = d.id
                """;

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String personName = resultSet.getString("person_name");
                String departmentName = resultSet.getString("department_name");

                // Проверяем, есть ли уже список сотрудников для этого отдела
                if (!map.containsKey(departmentName)) {
                    // Если нет, создаем новый список
                    map.put(departmentName, new ArrayList<>());
                }
                // Добавляем имя сотрудника в список для соответствующего отдела
                map.get(departmentName).add(personName);
            }

            return map;
        }
    }


    //region 7 задание

    /**
     * Получить имя department по id person
     *
     * @param connection соединение
     * @param personId   id пользователя
     * @return строка
     * @throws SQLException исключение
     */
    private static Department getPersonDepartment(Connection connection, long personId) throws SQLException {
        String query = """
                SELECT d.id, d.name
                FROM person p
                JOIN department d ON p.department_id = d.id
                WHERE p.id = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, personId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Department(resultSet.getLong("id"), resultSet.getString("name"));
                } else {
                    return null;
                }
            }
        }
    }

    /**
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    private static Map<Person, Department> getPersonDepartmentMap(Connection connection) throws SQLException {
        Map<Person, Department> map = new HashMap<>();

        String query = """
                SELECT
                    p.id AS person_id,
                    p.name AS person_name,
                    p.age AS person_age,
                    p.department_id,
                    d.id AS department_id,
                    d.name AS department_name
                FROM person p
                JOIN department d ON p.department_id = d.id
                """;

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Person person = new Person(
                        resultSet.getLong("person_id"),
                        resultSet.getString("person_name"),
                        resultSet.getInt("person_age"),
                        resultSet.getLong("department_id")
                );
                Department department = new Department(
                        resultSet.getLong("department_id"),
                        resultSet.getString("department_name")
                );
                map.put(person, department);
            }

            return map;
        }
    }

    /**
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    private static Map<Department, List<Person>> getDepartmentPersonMap(Connection connection) throws SQLException {
        Map<Department, List<Person>> map = new HashMap<>();

        String query = """
                SELECT p.id AS person_id, p.name AS person_name, p.age AS person_age, p.department_id, d.id AS department_id, d.name AS department_name
                FROM person p
                JOIN department d ON p.department_id = d.id
                """;

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Department department = new Department(
                        resultSet.getLong("department_id"),
                        resultSet.getString("department_name")
                );
                Person person = new Person(
                        resultSet.getLong("person_id"),
                        resultSet.getString("person_name"),
                        resultSet.getInt("person_age"),
                        resultSet.getLong("department_id")
                );

                // Проверяем, есть ли уже список сотрудников для этого отдела
                if (!map.containsKey(department)) {
                    // Если нет, создаем новый список
                    map.put(department, new ArrayList<>());
                }
                // Добавляем сотрудника в список для соответствующего отдела
                map.get(department).add(person);
            }

            return map;
        }
    }

    //endregion

}

@Getter
@Setter
class Person {
    private long id;
    private String name;
    private int age;
    private long departmentId;

    public Person(long id, String name, int age, long departmentId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", departmentId=" + departmentId +
                '}';
    }
}

@Getter
@Setter
class Department {
    private long id;
    private String name;

    public Department(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}