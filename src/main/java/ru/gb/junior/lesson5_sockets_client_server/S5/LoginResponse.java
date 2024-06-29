package ru.gb.junior.lesson5_sockets_client_server.S5;

/**
 * {
 *   "connected": true
 * }
 */
public class LoginResponse {

  private boolean connected;

  public boolean isConnected() {
    return connected;
  }

  public void setConnected(boolean connected) {
    this.connected = connected;
  }
}
