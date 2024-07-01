package ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json;

import lombok.Getter;
import lombok.Setter;
import ru.gb.junior.lesson5_sockets_client_server.S5_server.data.User;

import java.util.List;

/**
 * Класс ListResponse представляет ответ со списком пользователей.
 * Пример JSON-ответа:
 * {
 *   "users": [
 *     {
 *       "login": "anonymous"
 *     },
 *     {
 *       "login": "nagibator"
 *     },
 *     {
 *       "login": "admin"
 *     }
 *   ]
 * }
 */
@Setter
@Getter
public class ListResponse {

  private List<User> users;

}
