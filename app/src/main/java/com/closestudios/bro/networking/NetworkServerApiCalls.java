package com.closestudios.bro.networking;

import com.closestudios.bro.networking.requests.AddBroRequest;
import com.closestudios.bro.networking.requests.BlockBroRequest;
import com.closestudios.bro.networking.requests.GetBrosRequest;
import com.closestudios.bro.networking.requests.RemoveBroRequest;
import com.closestudios.bro.networking.requests.SignInCredsRequest;
import com.closestudios.bro.networking.requests.SignInTokenRequest;
import com.closestudios.bro.networking.requests.SignUpRequest;
import com.closestudios.bro.networking.requests.UpdateLocationRequest;
import com.closestudios.bro.networking.responses.ErrorResponse;
import com.closestudios.bro.networking.responses.GetBrosResponse;
import com.closestudios.bro.networking.responses.SignInResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by closestudios on 11/23/15.
 */
public class NetworkServerApiCalls implements ServerApiCalls {

    NetworkCall networkCall;
    String host;
    int port;

    public NetworkServerApiCalls(NetworkCall networkCall, String host, int port) {
        this.networkCall = networkCall;
        this.host = host;
        this.port = port;
    }

    @Override
    public void signUp(final String broName, final String password, final SignInCallback callback) {
        try {
            new Thread(new Runnable() {
                public void run() {
                    // Do Sign Up
                    SignInResponse signInResponse = null;
                    try {
                        signInResponse = (SignInResponse)sendApiMessage(SignUpRequest.createMessage(broName, password), new SignInResponse());

                        if(signInResponse != null) {

                            if (signInResponse.getSuccess()) {
                                callback.onSuccess(signInResponse.getToken());
                            } else {
                                callback.onError(signInResponse.getError());
                            }
                        } else {
                            callback.onError("Sign Up Failed");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError(e.getMessage());
                    }

                }
            }).start();
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    @Override
    public void signIn(final String broName, final String password, final SignInCallback callback) {
        try {
            new Thread(new Runnable() {
                public void run() {
                    // Do Sign In
                    SignInResponse signInResponse = null;
                    try {
                        signInResponse = (SignInResponse)sendApiMessage(SignInCredsRequest.createMessage(broName, password), new SignInResponse());

                        if(signInResponse != null) {

                            if (signInResponse.getSuccess()) {
                                callback.onSuccess(signInResponse.getToken());
                            } else {
                                callback.onError(signInResponse.getError());
                            }
                        } else {
                            callback.onError("Sign In Failed");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError(e.getMessage());
                    }

                    networkCall.onComplete(NetworkServerApiCalls.this);
                }
            }).start();
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    @Override
    public void signIn(final String token, final SignInCallback callback) {
        try {
            new Thread(new Runnable() {
                public void run() {
                    // Do Sign In
                    SignInResponse signInResponse = null;
                    try {
                        signInResponse = (SignInResponse)sendApiMessage(SignInTokenRequest.createMessage(token), new SignInResponse());

                        if(signInResponse != null) {

                            if (signInResponse.getSuccess()) {
                                callback.onSuccess(signInResponse.getToken());
                            } else {
                                callback.onError(signInResponse.getError());
                            }
                        } else {
                            callback.onError("Sign In With Token Failed");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError(e.getMessage());
                    }

                    networkCall.onComplete(NetworkServerApiCalls.this);
                }
            }).start();
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    @Override
    public void getBros(final String token, final BroCallback callback) {
        try {
            new Thread(new Runnable() {
                public void run() {
                    // Do Get Bro
                    GetBrosResponse getBrosResponse = null;
                    try {
                        getBrosResponse = (GetBrosResponse)sendApiMessage(GetBrosRequest.createMessage(token), new GetBrosResponse());

                        if(getBrosResponse != null) {

                            if (getBrosResponse.getSuccess()) {
                                callback.onSuccess(getBrosResponse.getBros());
                            } else {
                                callback.onError(getBrosResponse.getError());
                            }
                        } else {
                            callback.onError("Get Bros Failed");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError(e.getMessage());
                    }

                    networkCall.onComplete(NetworkServerApiCalls.this);
                }
            }).start();
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    @Override
    public void addBro(final String token, final String broName, final BroCallback callback) {
        try {
            new Thread(new Runnable() {
                public void run() {
                    // Do Add Bro
                    GetBrosResponse getBrosResponse = null;
                    try {
                        getBrosResponse = (GetBrosResponse)sendApiMessage(AddBroRequest.createMessage(token, broName), new GetBrosResponse());

                        if(getBrosResponse != null) {

                            if (getBrosResponse.getSuccess()) {
                                callback.onSuccess(getBrosResponse.getBros());
                            } else {
                                callback.onError(getBrosResponse.getError());
                            }
                        } else {
                            callback.onError("Add Bro Failed");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError(e.getMessage());
                    }

                    networkCall.onComplete(NetworkServerApiCalls.this);
                }
            }).start();
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    @Override
    public void removeBro(final String token, final String broName, final BroCallback callback) {
        try {
            new Thread(new Runnable() {
                public void run() {
                    // Do Remove Bro
                    GetBrosResponse getBrosResponse = null;
                    try {
                        getBrosResponse = (GetBrosResponse)sendApiMessage(RemoveBroRequest.createMessage(token, broName), new GetBrosResponse());

                        if(getBrosResponse != null) {

                            if (getBrosResponse.getSuccess()) {
                                callback.onSuccess(getBrosResponse.getBros());
                            } else {
                                callback.onError(getBrosResponse.getError());
                            }
                        } else {
                            callback.onError("Remove Bro Failed");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError(e.getMessage());
                    }

                    networkCall.onComplete(NetworkServerApiCalls.this);
                }
            }).start();
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    @Override
    public void blockBro(final String token, final String broName, final BroCallback callback) {
        try {
            new Thread(new Runnable() {
                public void run() {
                    // Do Block Bro
                    GetBrosResponse getBrosResponse = null;
                    try {
                        getBrosResponse = (GetBrosResponse)sendApiMessage(BlockBroRequest.createMessage(token, broName), new GetBrosResponse());

                        if(getBrosResponse != null) {

                            if (getBrosResponse.getSuccess()) {
                                callback.onSuccess(getBrosResponse.getBros());
                            } else {
                                callback.onError(getBrosResponse.getError());
                            }
                        } else {
                            callback.onError("Block Bro Failed");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError(e.getMessage());
                    }

                    networkCall.onComplete(NetworkServerApiCalls.this);
                }
            }).start();
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    @Override
    public void onUpdateLocation(final String token, final BroLocation location, final UpdateCallback callback) {
        try {
            new Thread(new Runnable() {
                public void run() {
                    // Do Update Location
                    ErrorResponse errorResponse = null;
                    try {
                        errorResponse = (ErrorResponse)sendApiMessage(UpdateLocationRequest.createMessage(token, location), new ErrorResponse());

                        if(errorResponse != null) {

                            if (errorResponse.getSuccess()) {
                                callback.onSuccess();
                            } else {
                                callback.onError(errorResponse.getError());
                            }
                        } else {
                            callback.onError("Update Location Failed");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError(e.getMessage());
                    }

                    networkCall.onComplete(NetworkServerApiCalls.this);
                }
            }).start();
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    private DataMessage sendApiMessage(byte[] message, DataMessage response) {

        Socket socket = null;

        try {

            socket = new Socket(host, port);

            OutputStream outToServer = socket.getOutputStream();
            InputStream inFromServer = socket.getInputStream();

            // Send Register Message
            outToServer.write(message);
            outToServer.flush();

            // Get response
            response.getBytesFromInput(inFromServer);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return response;
    }

}
