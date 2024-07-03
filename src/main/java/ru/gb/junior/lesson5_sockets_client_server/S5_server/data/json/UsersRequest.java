package ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json;

/**
 * {
 *   "type": "usersRequest"
 * }
 */
public class UsersRequest extends AbstractRequest {

    public static final String TYPE = "usersRequest";

    public UsersRequest() {
        setType(TYPE);
    }
}

