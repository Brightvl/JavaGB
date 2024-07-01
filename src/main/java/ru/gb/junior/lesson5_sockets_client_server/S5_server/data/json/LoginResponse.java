package ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс LoginResponse представляет ответ на запрос логина.
 * Пример JSON-ответа:
 * {
 *   "connected": true
 * }
 */
@Setter
@Getter
public class LoginResponse {

  private boolean connected;

}
