package org.example.yalla_api.common.logic.transfer.childrenpolicy;

public class EvaluatedChild {
    private final int age;
    private final double priceValue;
    private final double paxValue;

    public EvaluatedChild(int age, double priceValue, double paxValue) {
        this.age = age;
        this.priceValue = priceValue;
        this.paxValue = paxValue;
    }

    public int getAge() {
        return age;
    }

    public double getPriceValue() {
        return priceValue;
    }

    public double getPaxValue() {
        return paxValue;
    }
}