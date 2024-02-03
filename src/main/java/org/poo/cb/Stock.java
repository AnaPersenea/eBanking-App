package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Stock implements PortfolioComponent {
    String stockName;
    Double stockBalance;
    Integer wasRecommended;
    public Stock(String stockName, Double stockBalance) {
        this.stockName = stockName;
        this.stockBalance = stockBalance;
        this.wasRecommended = 0;
    }
    @Override
    public Double getBalance() {
        return stockBalance;
    }

    @Override
    public String getType() {
        return "Stock";
    }

    @Override
    public String toString() {
        int intBalance = stockBalance.intValue();
        return "{\"stockName\":\"" + stockName + "\",\"amount\":" + intBalance + "}";
    }

    public Double getStockValue(String stockPath, String stockName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(stockPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Stock")) {
                    continue;
                }
                String[] values = line.split(",");
                String name = values[0];
                if (stockName.equals(name)) {
                    return Double.parseDouble(values[values.length - 1]);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return 0.0;
    }
}
