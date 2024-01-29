package org.poo.cb;

public class AccountFactory{

    public enum AccountType {
        EUR, GBP, JPY, CAD, USD
    }
    private Account create(AccountType accountType, String currencyName, Double currencyBalance) {
        return switch (accountType) {
            case EUR -> new EurAccount(currencyName, currencyBalance);
            case GBP -> new GbpAccount(currencyName, currencyBalance);
            case JPY -> new JpyAccount(currencyName, currencyBalance);
            case CAD -> new CadAccount(currencyName, currencyBalance);
            case USD -> new UsdAccount(currencyName, currencyBalance);
        };
    }

    public Account createByType(String currencyName, Double currencyBalance) {
        for (AccountType type : AccountType.values()) {
            if (type.name().equals(currencyName)) {
                return create(type, currencyName, currencyBalance);
            }
        }
        return null;
    }
}
