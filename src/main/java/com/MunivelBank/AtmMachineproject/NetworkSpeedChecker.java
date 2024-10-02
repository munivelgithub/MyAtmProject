package com.MunivelBank.AtmMachineproject;



import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class NetworkSpeedChecker {

    private static final String TEST_URL = "https://canarabank.com"; // Sample test file URL"https://canarabank.com/"https://www.indianbank.net.in/jsp/startIB.jsp
    private static final long MIN_SPEED_MBPS = 1; // Minimum speed threshold
    private static final long MAX_LATENCY_MS =10_000; // Maximum acceptable latency
    private static final double MAX_PACKET_LOSS_PERCENTAGE = 1; // Maximum acceptable packet loss

    public static  String   Widhrawlcheck() {
        try {
            long speed = measureDownloadSpeed(TEST_URL);
            long latency = measureLatency(TEST_URL);
            double packetLoss = measurePacketLoss(TEST_URL);
//
//            System.out.printf("Download Speed: %d Mbps%n", speed);
//            System.out.printf("Latency: %d ms%n", latency);
//            System.out.printf("Packet Loss: %.2f%%%n", packetLoss);

             return   alertUser(speed, latency, packetLoss);
        } catch (IOException e) {
            return "Error occurred while checking network conditions: " + e.getMessage();
        }
        
    }

    private static long measureDownloadSpeed(String urlString) throws IOException {
        HttpGet request = new HttpGet(urlString);
        long totalBytes = 0;
        long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
             
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream in = new BufferedInputStream(entity.getContent())) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        totalBytes += bytesRead;
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long durationInSeconds = (endTime - startTime) / 1000;
        return (durationInSeconds > 0) ? (totalBytes * 8) / (durationInSeconds * 1024 * 1024) : 0; // Convert bytes to Mbps
    }

    private static long measureLatency(String urlString) throws IOException {
        long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(urlString))) {
            // Just a simple request to measure latency
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime; // Latency in milliseconds
    }

    private static double measurePacketLoss(String urlString) {
        // This is a mock implementation; replace it with real packet loss measurement logic
        return Math.random() * 10; // Simulate packet loss between 0% and 10%
    }

    private static String  alertUser(long speed, long latency, double packetLoss) {
        if (speed < MIN_SPEED_MBPS ||  (latency > MAX_LATENCY_MS ) ||  (packetLoss > MAX_PACKET_LOSS_PERCENTAGE) ) {
             return "Alert: Network speed is too low. Avoid taking funds.";   
        }
           return "Network conditions are acceptable. You can proceed with the transaction.";
        
    }
}
//
//Key Features:
//
//1. Download Speed Measurement: Measures download speed using a sample file.
//
//
//2. Latency Measurement: Measures latency by timing a request to the same file.
//
//
//3. Packet Loss Simulation: A mock implementation simulating packet loss (replace this with real packet loss measurement logic if needed).
//
//
//4. Alerts: Checks if any of the conditions (low speed, high latency, or high packet loss) are met, and alerts the user accordingly.
//
//
//
//Notes:
//
//Replace the measurePacketLoss method with actual logic to measure packet loss if needed.
//
//Ensure you have the Apache HttpClient library included in your project dependencies for this code to work.