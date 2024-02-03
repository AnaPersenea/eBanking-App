package org.poo.cb;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final Portfolio userPortfolio;
    private final List<String> friends;
    private Integer premiumUser;


    public User(String email, String firstName, String lastName, String address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.friends = new ArrayList<>();
        this.userPortfolio = new Portfolio();
        this.premiumUser = 0;
    }

    public String getEmail() {
        return email;
    }
    public void addFriendToUser(String emailFriend) {
        friends.add(emailFriend);
    }
    public boolean alreadyFriends(String emailFriend) {
        for (String email : friends) {
            if (email.equals(emailFriend)) {
                return false;
            }
        }
        return true;
    }

    public Portfolio getUserPortfolio() {
        return userPortfolio;
    }

    public void addAccountToPortfolio(String currencyName) {
        AccountFactory accountFactory = new AccountFactory();
        userPortfolio.addComponent(accountFactory.createByType(currencyName, 0.00));
    }

    public Account alreadyExistsAccount(String currencyType) {
        for (PortfolioComponent component : userPortfolio.getComponents()) {
            if (component instanceof Account && component.getType().equals(currencyType)) {
                return (Account) component;
            }
        }
        return null;
    }
    public void addMoneyToAccount(String currencyName, Double currencyBalance) {
        Account account = alreadyExistsAccount(currencyName);
        account.increaseBalance(currencyBalance);
    }

    public void exchange(Exchange exchangeRates, String fromCurrency, String toCurrency, Double amount) {
        Account fromAccount = alreadyExistsAccount(fromCurrency);
        Account toAccount = alreadyExistsAccount(toCurrency);

        if (fromAccount != null) {
            Double rate = exchangeRates.getExchangeRate(toCurrency, fromCurrency);

            if (fromAccount.getBalance() < amount * rate) {
                System.out.println("Insufficient amount in account " + fromCurrency + " for exchange");
                return;
            }
            if (fromAccount.getBalance() / 2 < amount * rate && premiumUser == 0) {
                fromAccount.decreaseBalance(amount * rate / 100);
            }
            fromAccount.decreaseBalance(amount * rate);
            toAccount.increaseBalance(amount);
        }
    }

    public void transfer(User friend, String currencyName, Double amount) {
        Account fromAccount = alreadyExistsAccount(currencyName);
        Account toAccount = friend.alreadyExistsAccount(currencyName);

        fromAccount.decreaseBalance(amount);
        toAccount.increaseBalance(amount);
    }

    public void buy(String stockPath, String stockName, Double amount, List<String> recommendedStocks) {
        Account account = alreadyExistsAccount("USD");
        Stock stock = new Stock(stockName, amount);
        Double stockValue = stock.getStockValue(stockPath, stockName);
        if (account != null) {
            if (account.currencyBalance < amount * stockValue) {
                System.out.println("Insufficient amount in account for buying stock");
                return;
            }
            String checkStockName = "\"" + stockName + "\"";
            if (premiumUser == 1 && recommendedStocks.contains(checkStockName)) {
                account.decreaseBalance(amount * stockValue * 0.95);
            } else {
                account.decreaseBalance(amount * stockValue);
            }
            userPortfolio.addComponent(stock);
        }
    }

    public void buyP() {
        Account account = alreadyExistsAccount("USD");
        if (account.getBalance() < 100.00) {
            System.out.println("Insufficient amount in account for buying premium option");
        } else {
            account.decreaseBalance(100.00);
            premiumUser = 1;
        }
    }

    @Override
    public String toString() {
        String friendList;
        if (friends.isEmpty()) {
            friendList = "[]";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            for (String emailFriend : friends) {
                stringBuilder.append("\"").append(emailFriend).append("\"");
            }
            stringBuilder.append("]");
            friendList = stringBuilder.toString();
        }
        return "{\"email\":\"" + email + "\",\"firstname\":\"" + firstName + "\",\"lastname\":\"" + lastName
                + "\",\"address\":\"" + address + "\",\"friends\":" + friendList + "}";

    }
}
