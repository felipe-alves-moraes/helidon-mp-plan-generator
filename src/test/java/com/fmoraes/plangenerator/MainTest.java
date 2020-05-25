package com.fmoraes.plangenerator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MainTest {
    private static int port;

    @BeforeAll
    public static void startTheServer() throws Exception {
        port = Main.startServer().port();
    }

//    @Test
//    void testRestResource() {
//        Client client = ClientBuilder.newClient();
//
//        String response = client
//                .target(getConnectionString("/example"))
//                .request()
//                .get(String.class);
//        Assertions.assertEquals("It works!", response);
//    }

    @Test
    void testRoot(){
        Client client = ClientBuilder.newClient();
        Response response = client
                .target(getConnectionString("/"))
                .request()
                .get();
        Assertions.assertEquals(404, response.getStatus());
    }

    private static String getConnectionString(String path) {
        return "http://localhost:" + port + path;
    }
}
