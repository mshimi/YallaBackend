package org.example.yalla_api.common.logic.transfer.childrenpolicy;

import java.util.List;

public class EvaluatedChildren {

    private final List<EvaluatedChild> children;

    public EvaluatedChildren(List<EvaluatedChild> children) {
        this.children = children;
    }

    public List<EvaluatedChild> getChildren() {
        return children;
    }

    public double getTotalPrice() {
        return children.stream().mapToDouble(EvaluatedChild::getPriceValue).sum();
    }

    public double getTotalPaxValue() {
        return children.stream().mapToDouble(EvaluatedChild::getPaxValue).sum();
    }

    public int getTotalChildren() {
        return children.size();
    }
}