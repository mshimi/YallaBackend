package org.example.yalla_api.common.logic.transfer.extras;

import lombok.Data;
import org.example.yalla_api.common.entities.transfer.TransferExtra;

public record TransferExtraItem(TransferExtra extra, int quantity) {


    public double getTotalValue() {
        return quantity * extra.getPaxValue();
    }
}
