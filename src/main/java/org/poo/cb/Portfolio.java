package org.poo.cb;

import java.util.ArrayList;
import java.util.List;

public class Portfolio implements PortfolioComponent {
    private final List<PortfolioComponent> components = new ArrayList<>();

    public List<PortfolioComponent> getComponents() {
        return components;
    }
    public void addComponent(PortfolioComponent component) {
        components.add(component);
    }

    @Override
    public Double getBalance() {
        return 0.00;
    }

    @Override
    public String getType() {
        return "Portfolio";
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"stocks\":[");

        boolean firstStock = true;
        for (PortfolioComponent component : components) {
            if (component instanceof Stock) {
                if (!firstStock) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(component);
                firstStock = false;
            }
        }

        stringBuilder.append("],\"accounts\":[");
        boolean firstAccount = true;
        for (PortfolioComponent component : components) {
            if (component instanceof Account) {
                if (!firstAccount) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(component);
                firstAccount = false;
            }
        }

        stringBuilder.append("]}");
        return stringBuilder.toString();
    }
}
