package ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс ListRequest представляет запрос на получение списка пользователей.
 * Пример JSON-запроса:
 * {
 *   "type": "users"
 * }
 */
@Setter
@Getter
public class ListRequest {

  private String type;

}
