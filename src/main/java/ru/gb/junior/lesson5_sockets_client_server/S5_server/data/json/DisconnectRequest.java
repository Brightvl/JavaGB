package ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json;

/**
 * {
 *   "type": "disconnect"
 * }
 */
public class DisconnectRequest extends AbstractRequest {

    public static final String TYPE = "disconnect";

    public DisconnectRequest() {
        setType(TYPE);
    }
}
