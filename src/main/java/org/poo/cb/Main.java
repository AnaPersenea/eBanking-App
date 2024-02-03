package org.poo.cb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args == null) {
            System.out.println("Running Main");
            return;
        }
        String testName = args[2];
        String readPath  = "src/main/resources/" + testName;
        File in = new File(readPath);
        EBank eBank = new EBank();
        List<String> recommendedStocks = new ArrayList<>();

        try (Scanner scanner = new Scanner(in)) {
            while (scanner.hasNextLine()) {
                String[] fullCommand = scanner.nextLine().split(" ");
                String command = fullCommand[0] + " " + fullCommand[1];
                if (command.equals("CREATE USER")) {
                    User user = eBank.getUser(fullCommand);
                    eBank.addUser(user);
                }

                if (command.equals("LIST USER")) {
                    eBank.listUser(fullCommand[2]);
                }

                if (command.equals("ADD FRIEND")) {
                    eBank.addFriend(fullCommand[2], fullCommand[3]);
                }

                if (command.equals("ADD ACCOUNT")) {
                    eBank.addAccount(fullCommand[2], fullCommand[3]);
                }

                if (command.equals("ADD MONEY")) {
                    int currency = Integer.parseInt(fullCommand[4]);
                    Double currencyBalance = (double) currency;
                    eBank.addMoney(fullCommand[2], fullCommand[3], currencyBalance);
                }

                if (command.equals("LIST PORTFOLIO")) {
                    eBank.listPortfolio(fullCommand[2]);
                }

                if (command.equals("EXCHANGE MONEY")) {
                    int currency = Integer.parseInt(fullCommand[5]);
                    Double currencyBalance = (double) currency;
                    eBank.exchangeMoney(fullCommand[2], fullCommand[3], fullCommand[4], currencyBalance);
                }

                if (command.equals("TRANSFER MONEY")) {
                    int currency = Integer.parseInt(fullCommand[5]);
                    Double currencyBalance = (double) currency;
                    eBank.transferMoney(fullCommand[2], fullCommand[3], fullCommand[4], currencyBalance);
                }

                if (command.equals("RECOMMEND STOCKS")) {
                    String stockPath  = "src/main/resources/" + args[1];
                    recommendedStocks = eBank.recommendStocks(stockPath);
                    eBank.printRecommendedStocks(stockPath);
                }

                if (command.equals("BUY STOCKS")) {
                    String stockPath  = "src/main/resources/" + args[1];
                    int stock = Integer.parseInt(fullCommand[4]);
                    Double stockBalance = (double) stock;
                    eBank.buyStock(stockPath, fullCommand[2], fullCommand[3], stockBalance, recommendedStocks);
                }

                if (command.equals("BUY PREMIUM")) {
                    eBank.buyPremium(fullCommand[2]);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}