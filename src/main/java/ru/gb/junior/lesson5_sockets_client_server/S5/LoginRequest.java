package ru.gb.junior.lesson5_sockets_client_server.S5;

/**
 * {
 *   "type": "login",
 *   "login": "nagibator"
 * }
 */
public class LoginRequest {

  private String login;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }
}
