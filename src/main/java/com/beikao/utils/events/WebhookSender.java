package com.beikao.utils.events;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebhookSender {

    // Your webhook URL here
    private static final String WEBHOOK_URL = "https://discord.com/api/webhooks/1439060654609662072/1ubdpuA95pufvR8D95V-wNoaQKYDm_AnCWc9WKjI8XcnGUenEzTMQ0OrRd43Q-nH60NB";

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static void sendWebhook(String message) {
        if (WEBHOOK_URL == null || WEBHOOK_URL.isEmpty()) {
            return; // no webhook configured
        }

        // Discord JSON body
        String json = "{\"content\":\"" + message.replace("\"", "\\\"") + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WEBHOOK_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Send asynchronously (doesn't freeze the server tick)
        CLIENT.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .exceptionally(e -> {
                    System.err.println("Failed to send Discord webhook: " + e);
                    return null;
                });
    }
}
