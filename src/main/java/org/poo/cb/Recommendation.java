package org.poo.cb;

import java.util.List;

interface Strategy {
    double calculateSMA(List<Double> stockValues, int numberDays);
}

class SMA implements Strategy {
    @Override
    public double calculateSMA(List<Double> stockValues, int numberDays) {
        Double sum = 0.0;
        for (int i = stockValues.size() - numberDays; i < stockValues.size(); i++) {
            sum += stockValues.get(i);
        }
        return sum / numberDays;
    }
}

public class Recommendation {
    private final List<Double> stockValues;
    private final String stockName;
    private final Strategy strategy;

    public Recommendation(List<Double> stockValues, String stockName, Strategy strategy) {
        this.stockValues = stockValues;
        this.stockName = stockName;
        this.strategy = strategy;
    }

    public String makeRecommendation() {
        double shortSMA = strategy.calculateSMA(stockValues, 5);
        double longSMA = strategy.calculateSMA(stockValues, 10);

        if (shortSMA > longSMA) {
            return "\"" + stockName + "\"";
        }
        return null;
    }

}
