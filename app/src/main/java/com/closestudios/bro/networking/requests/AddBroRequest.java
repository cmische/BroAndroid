package com.closestudios.bro.networking.requests;

import com.closestudios.bro.networking.DataMessage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by closestudios on 11/23/15.
 */
public class AddBroRequest {

    ServerRequest serverRequest;
    String token;
    String broName;

    public AddBroRequest(ServerRequest request) {
        serverRequest = request;

        ArrayList<byte[]> blocks = DataMessage.getBlocks(serverRequest.getRequestBytes());

        token = new String(blocks.get(0));
        broName = new String(blocks.get(1));

    }

    public String getToken() {
        return token;
    }
    public String getBroName() {
        return broName;
    }

    public static byte[] createMessage(String token, String broName) throws IOException {

        ArrayList<byte[]> data = new ArrayList<>();
        data.add(token.getBytes());
        data.add(broName.getBytes());

        return ServerRequest.createMessage(DataMessage.createBlocks(data), ServerRequest.ServerRequestType.AddBro);
    }
}
