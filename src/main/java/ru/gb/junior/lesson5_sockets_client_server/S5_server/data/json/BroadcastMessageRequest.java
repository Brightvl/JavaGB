package ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json;


import lombok.Getter;
import lombok.Setter;

/**
 * {
 *   "type": "broadcastMessage",
 *   "message": "text to all"
 * }
 */
@Setter
@Getter
public class BroadcastMessageRequest extends AbstractRequest {

    public static final String TYPE = "broadcastMessage";

    private String message;

    public BroadcastMessageRequest() {
        setType(TYPE);
    }

}
