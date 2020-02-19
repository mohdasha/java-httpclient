package com.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

public class WebSocketExample {

    public static void main(String[] args) throws InterruptedException {

        int msgCount = 5;
        CountDownLatch receiveLatch = new CountDownLatch(msgCount);

        CompletableFuture<WebSocket> wsFuture = HttpClient.newHttpClient()
                        .newWebSocketBuilder()
                .buildAsync(URI.create("ws://echo.websocket.org"), new EchoListener(receiveLatch));

        wsFuture.thenAccept(webSocket -> {
            webSocket.request(msgCount);
            for(int i = 0; i < msgCount; i++)
                webSocket.sendText("Message: "+i, true);
        });

        receiveLatch.await();
    }

    static class EchoListener implements WebSocket.Listener {

        private CountDownLatch receiverLatch;

        EchoListener(CountDownLatch receiverLatch) {
            this.receiverLatch = receiverLatch;
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            System.out.println("onText "+data);
            receiverLatch.countDown();
            return null;
        }

        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("Websocket opened");
        }
    }
}
