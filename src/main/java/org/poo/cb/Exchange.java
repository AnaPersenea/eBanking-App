package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Exchange {
    private final Map<String, Map<String, Double>> exchangeRates;
    private static Exchange oneInstance;
    private Exchange() {
        exchangeRates = new HashMap<>();
        initExchangeRates();
    }
    public static Exchange Instance() {
        if (oneInstance == null) {
            oneInstance = new Exchange();
        }
        return oneInstance;
    }

    private void initExchangeRates() {
        String csvFile = "src/main/resources/common/exchangeRates.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            if ((line = reader.readLine()) != null) {
                String[] mainKeys = line.split(",");
                for (int i = 1; i < 6; i++) {
                    exchangeRates.put(mainKeys[i], new HashMap<>());
                }
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    if (exchangeRates.containsKey(values[0])) {
                        for (int i = 1; i < 6; i++) {
                            Double value = Double.parseDouble(values[i]);
                            addExchangeRate(values[0], mainKeys[i], value);
                        }
                    }

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addExchangeRate(String from, String to, Double rate) {
        exchangeRates.computeIfAbsent(from, k -> new HashMap<>()).put(to, rate);
    }
    public Double getExchangeRate(String to, String from) {
        return exchangeRates.get(to).getOrDefault(from, 1.0);
    }
}
