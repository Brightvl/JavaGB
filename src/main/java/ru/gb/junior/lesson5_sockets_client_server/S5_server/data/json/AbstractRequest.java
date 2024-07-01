package ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json;

import lombok.Getter;
import lombok.Setter;

/**
 * Абстрактный класс AbstractRequest, представляющий общий запрос.
 */
@Setter
@Getter
public class AbstractRequest {

  private String type;

}
