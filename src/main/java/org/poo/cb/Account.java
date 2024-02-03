package org.poo.cb;

abstract class Account implements PortfolioComponent{
    String currencyName;
    Double currencyBalance;
    public Account(String currencyName, Double currencyBalance) {
        this.currencyName = currencyName;
        this.currencyBalance = currencyBalance;
    }

    @Override
    public Double getBalance() {
        return currencyBalance;
    }
    public void increaseBalance(Double amount) {
        currencyBalance += amount;
    }
    public void decreaseBalance(Double amount) {
        currencyBalance -= amount;
    }

    @Override
    public abstract String getType();

    @Override
    public String toString() {
        String formattedBalance = String.format("%.2f", currencyBalance);
        return "{\"currencyName\":\"" + currencyName + "\",\"amount\":\"" + formattedBalance + "\"}";
    }
}

class EurAccount extends Account {
    public EurAccount(String currencyName, Double currencyBalance) {
        super(currencyName, currencyBalance);
    }

    @Override
    public String getType() {
        return "EUR";
    }
}
class GbpAccount extends Account {
    public GbpAccount(String currencyName, Double currencyBalance) {
        super(currencyName, currencyBalance);
    }

    @Override
    public String getType() {
        return "GBP";
    }
}
class JpyAccount extends Account {
    public JpyAccount(String currencyName, Double currencyBalance) {
        super(currencyName, currencyBalance);
    }
    @Override
    public String getType() {
        return "JPY";
    }
}
class CadAccount extends Account {
    public CadAccount(String currencyName, Double currencyBalance) {
        super(currencyName, currencyBalance);
    }
    @Override
    public String getType() {
        return "CAD";
    }
}
class UsdAccount extends Account {
    public UsdAccount(String currencyName, Double currencyBalance) {
        super(currencyName, currencyBalance);
    }
    @Override
    public String getType() {
        return "USD";
    }
}

