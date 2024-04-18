package ru.gb.core.lesson1.task2;


import ru.gb.core.lesson1.models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Program {

    private final static Random random = new Random();

    public static void main(String[] args) throws SQLException, InterruptedException {

        Thread.sleep(20000);


        String url = "jdbc:mysql://employees-db:3306/";
        String user = "root";
        String password = "Password";

        // Подключение к базе данных
        Connection connection = DriverManager.getConnection(url, user, password);

        // Использование базы данных
        useDatabase(connection);
        System.out.println("Use database successfully");

        // Создание таблицы
        createTable(connection);
        System.out.println("Create table successfully");

        int count = random.nextInt(5, 11);
        for (int i = 0; i < count; i++)
            insertData(connection, Employee.create());
        System.out.println("Insert data successfully");

        // Чтение данных
        Collection<Employee> employees = readData(connection);
        for (var employee: employees)
            System.out.println(employee);
        System.out.println("Read data successfully");

        // Закрытие соединения
        connection.close();

    }

    private static void useDatabase(Connection connection) throws SQLException {
        String useDatabaseSQL =  "USE employeesDb;";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), age INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

    /**
     * Добавление данных в таблицу users
     * @param connection Соединение с БД
     * @param employee Рабочий
     * @throws SQLException Исключение при выполнении запроса
     */
    private static void insertData(Connection connection, Employee employee) throws SQLException {
        String insertDataSQL = "INSERT INTO users (name, age) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, employee.getName());
            statement.setInt(2, employee.getAge());
            statement.executeUpdate();
        }
    }

    /**
     * Чтение данных из таблицы users
     * @param connection Соединение с БД
     * @return Коллекция студентов
     * @throws SQLException Исключение при выполнении запроса
     */
    private static Collection<Employee> readData(Connection connection) throws SQLException {
        ArrayList<Employee> employeesList = new ArrayList<>();
        String readDataSQL = "SELECT * FROM users;";
        try (PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                employeesList.add(new Employee(id, name, age));
            }
            return employeesList;
        }
    }


}
