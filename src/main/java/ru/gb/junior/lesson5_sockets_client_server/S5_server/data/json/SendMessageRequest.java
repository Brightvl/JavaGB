package ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс SendMessageRequest представляет запрос на отправку сообщения другому клиенту.
 * Пример JSON-запроса:
 * {
 *   "type": "sendMessage",
 *   "recipient": "nagibator",
 *   "message": "text to nagibator"
 * }
 */
@Setter
@Getter
public class SendMessageRequest extends AbstractRequest {

  public static final String TYPE = "sendMessage";

  private String recipient;
  private String message;

  public SendMessageRequest() {
    setType(TYPE);
  }

}
