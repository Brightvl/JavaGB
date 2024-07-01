package ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс LoginRequest представляет запрос на логин клиента.
 * Пример JSON-запроса:
 * {
 *   "type": "login",
 *   "login": "nagibator"
 * }
 */
@Setter
@Getter
public class LoginRequest {

  private String login;

}
