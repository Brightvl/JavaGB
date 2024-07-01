package ru.gb.junior.lesson5_sockets_client_server.S5_server.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json.AbstractRequest;
import ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json.LoginRequest;
import ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json.LoginResponse;
import ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json.SendMessageRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс ChatServer представляет собой сервер чата, который обрабатывает подключения клиентов, принимает и обрабатывает
 * сообщения от клиентов.
 */
public class ChatServer {

    // Объект ObjectMapper используется для преобразования объектов в JSON и обратно.
    private final static ObjectMapper objectMapper = new ObjectMapper();

    // Socket - абстракция, к которой можно подключиться
    // ip-address + port - socket
    // network - сеть - набор соединенных устройств
    // ip-address - это адрес устройства в какой-то сети
    // 8080 - http
    // 443 - https
    // 35 - smtp
    // 21 - ftp
    // 5432 - стандартный порт postgres
    // клиент подключается к серверу

  /*
   Порядок взаимодействия:
   1. Клиент подключается к серверу
   2. Клиент посылает сообщение, в котором указан логин. Если на сервере уже есть подключенный клиент с таким логином,
   то сведение разрывается
   3. Клиент может посылать 3 типа команд:
   3.1 list - получить логины других клиентов
   <p>
   3.2 send @login message - отправить личное сообщение с содержимым message другому клиенту с логином login
   3.3 send message - отправить сообщение всем с содержимым message
   */

    // Пример: 1324.132.12.3:8888
    public static void main(String[] args) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Map<String, ClientHandler> clients = new ConcurrentHashMap<>(); // Для хранения подключенных клиентов по их логинам
        try (ServerSocket server = new ServerSocket(8888)) { // слушаем трафик на порту
            System.out.println("Сервер запущен");

            while (true) {
                System.out.println("Ждем клиентского подключения");
                Socket client = server.accept(); // Ожидание подключения клиента

                // Создание нового обработчика клиента и запуск его в отдельном потоке
                ClientHandler clientHandler = new ClientHandler(client, clients);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка во время работы сервера: " + e.getMessage());
        }
    }

    /**
     * Класс ClientHandler обрабатывает взаимодействие с конкретным клиентом.
     */
    private static class ClientHandler implements Runnable {

        private final Socket client;
        private final Scanner in;
        private final PrintWriter out;
        private final Map<String, ClientHandler> clients;
        private String clientLogin;

        public ClientHandler(Socket client, Map<String, ClientHandler> clients) throws IOException {
            this.client = client;
            this.clients = clients;

            this.in = new Scanner(client.getInputStream()); // читаем с консоли
            this.out = new PrintWriter(client.getOutputStream(), true); // вывод в консоль (system.out)
        }

        /*
        Поток запускается как запустится клиент
         */
        @Override
        public void run() {
            System.out.println("Подключен новый клиент");

            // Чтение запроса на логин клиента
            try {
                String loginRequest = in.nextLine();
                LoginRequest request = objectMapper.reader().readValue(loginRequest, LoginRequest.class);
                this.clientLogin = request.getLogin();
            } catch (IOException e) {
                System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                String unsuccessfulResponse = createLoginResponse(false);
                out.println(unsuccessfulResponse);
                doClose();
                return;
            }

            System.out.println("Запрос от клиента: " + clientLogin);
            // Проверка, что логин не занят
            if (clients.containsKey(clientLogin)) {
                String unsuccessfulResponse = createLoginResponse(false);
                out.println(unsuccessfulResponse);
                doClose();
                return;
            }
            // Добавление клиента в map подключенных клиентов
            clients.put(clientLogin, this);
            String successfulLoginResponse = createLoginResponse(true);
            out.println(successfulLoginResponse);

            // Обработка сообщений от клиента
            while (true) {
                String msgFromClient = in.nextLine();

                final String type;
                try {
                    AbstractRequest request = objectMapper.reader().readValue(msgFromClient, AbstractRequest.class);
                    type = request.getType();
                } catch (IOException e) {
                    System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                    sendMessage("Не удалось прочитать сообщение: " + e.getMessage());
                    continue;
                }
                // Обработка сообщения типа SendMessageRequest
                if (SendMessageRequest.TYPE.equals(type)) {
                    // Клиент прислал SendMessageRequest

                    final SendMessageRequest request;
                    try {
                        request = objectMapper.reader().readValue(msgFromClient, SendMessageRequest.class);
                    } catch (IOException e) {
                        System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                        sendMessage("Не удалось прочитать сообщение SendMessageRequest: " + e.getMessage());
                        continue;
                    }

                    ClientHandler clientTo = clients.get(request.getRecipient());
                    if (clientTo == null) {
                        sendMessage("Клиент с логином [" + request.getRecipient() + "] не найден");
                        continue;
                    }
                    clientTo.sendMessage(request.getMessage());
                } else if (false) { // BroadcastRequest.TYPE.equals(type)
                    // TODO: Читать остальные типы сообщений
                } else if (false) { // DisconnectRequest.TYPE.equals(type)
                    break;
                } else {
                    System.err.println("Неизвестный тип сообщения: " + type);
                    sendMessage("Неизвестный тип сообщения: " + type);
                    continue;
                }
            }

            doClose();
        }

        /**
         * Закрытие соединения с клиентом.
         */
        private void doClose() {
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                System.err.println("Ошибка во время отключения клиента: " + e.getMessage());
            }
        }

        /**
         * Отправка сообщения клиенту.
         *
         * @param message текст сообщения
         */
        public void sendMessage(String message) {
            // TODO: нужно придумать структуру сообщения
            out.println(message);
        }

        /**
         * Создание ответа на запрос логина.
         *
         * @param success успешность запроса
         * @return строка JSON с ответом на запрос логина
         */
        private String createLoginResponse(boolean success) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setConnected(success);
            try {
                return objectMapper.writer().writeValueAsString(loginResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Не удалось создать loginResponse: " + e.getMessage());
            }
        }

    }

}
