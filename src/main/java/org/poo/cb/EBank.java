package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EBank {
    private final List<User> users = new ArrayList<>();
    Exchange exchangeRates = Exchange.Instance();


    public User getUser(String[] fullCommand) {
        String email = fullCommand[2];
        String firstName = fullCommand[3];
        String lastName = fullCommand[4];

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 5; i < fullCommand.length - 1; i++) {
            stringBuilder.append(fullCommand[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.append(fullCommand[fullCommand.length - 1]);
        String address = stringBuilder.toString();
        return new User(email, firstName, lastName, address);
    }
    public void addUser(User user) {
        if (searchUserByEmail(user.getEmail()) != null) {
            System.out.println("User with " + user.getEmail() + " already exists");
            return;
        }
        users.add(user);
    }
    public User searchUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void listUser(String email) {
        User user = searchUserByEmail(email);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User with email " + email + " doesn't exist");
        }
    }
    public void addFriend(String emailUser, String emailFriend) {
        User user = searchUserByEmail(emailUser);
        User friend = searchUserByEmail(emailFriend);
        if ((user != null) && (friend != null)) {
            if (user.alreadyFriends(emailFriend)) {
                user.addFriendToUser(emailFriend);
                friend.addFriendToUser(emailUser);
            } else {
                System.out.println("User with " + emailFriend + " is already a friend");
            }
        } else if (user == null) {
            System.out.println("User with " + emailUser + " doesn't exist");
        } else {
            System.out.println("User with " + emailFriend + " doesn't exist");
        }
    }

    public void addAccount(String email, String currencyName) {
        User user = searchUserByEmail(email);
        if (user != null) {
            if (user.alreadyExistsAccount(currencyName) == null) {
                user.addAccountToPortfolio(currencyName);
            } else {
                System.out.println("Account in currency " + currencyName + " already exists for user");
            }
        }
    }

    public void addMoney(String email, String currencyName, Double currencyBalance) {
        User user = searchUserByEmail(email);
        if (user != null) {
            user.addMoneyToAccount(currencyName, currencyBalance);
        }
    }

    public void listPortfolio(String email) {
        User user = searchUserByEmail(email);
        System.out.println(user.getUserPortfolio().toString());
    }

    public void exchangeMoney(String email, String fromCurrency, String toCurrency, Double amount) {
        User user = searchUserByEmail(email);
        if (user != null) {
            user.exchange(exchangeRates, fromCurrency, toCurrency, amount);
        }
    }

    public void transferMoney(String email, String emailFriend, String currency, Double amount) {
        User user = searchUserByEmail(email);
        User friend = searchUserByEmail(emailFriend);
        if ((user != null) && (friend != null)) {
            if (user.alreadyFriends(emailFriend)) {
                System.out.println("You are not allowed to transfer money to " + emailFriend);
                return;
            }
            if (user.alreadyExistsAccount(currency).getBalance() < amount) {
                System.out.println("Insufficient amount in account " + currency + " for transfer");
                return;
            }
            user.transfer(friend, currency, amount);
        }
    }

    public List<String> recommendStocks(String stockPath) {
        List<String> recommendedStocks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(stockPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Stock") || line.startsWith(",")) {
                    continue;
                }
                String[] values = line.split(",");
                String stockName = values[0];
                List<Double> stockValues = new ArrayList<>();
                for (int i = 1; i < values.length; i++) {
                    Double val = Double.parseDouble(values[i]);
                    stockValues.add(val);
                }
                Recommendation recommendation = new Recommendation(stockValues, stockName, new SMA());
                String stock = recommendation.makeRecommendation();
                if (stock != null) {
                    recommendedStocks.add(stock);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return recommendedStocks;
    }

    public void printRecommendedStocks(String stockPath) {
        List<String> recommendedStocks = recommendStocks(stockPath);
        StringBuilder stringBuilder = new StringBuilder("{\"stockstobuy\":[");
        boolean firstStock = true;
        for (String stock : recommendedStocks) {
            if (!firstStock) {
                stringBuilder.append(",");
            }
            stringBuilder.append(stock);
            firstStock = false;
        }
        stringBuilder.append("]}");
        System.out.println(stringBuilder);
    }

    public void buyStock(String stockPath, String email, String stockName, Double stockValue, List<String> recommendedStocks) {
        User user = searchUserByEmail(email);
        if (user != null) {
            user.buy(stockPath, stockName, stockValue, recommendedStocks);
        }
    }

    public void buyPremium(String email) {
        User user = searchUserByEmail(email);
        if (user != null) {
            user.buyP();
        } else {
            System.out.println("User with " + email + " doesn't exist");
        }
    }
}
