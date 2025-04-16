package org.example.yalla_api.common.logic.transfer.extras;

import java.util.List;

public class TransferExtras {

   private final List<TransferExtraItem> items;

   TransferExtras(List<TransferExtraItem> items) {
       this.items = items;
   }


   public List<TransferExtraItem> getItems() {
       return items;
   }

   public int getTotalQuantity() {
       return items.stream().mapToInt(TransferExtraItem::quantity).sum();
   }

   public double getTotalPaxValue() {
       return items.stream().mapToDouble(TransferExtraItem::getTotalValue).sum();
   }

}
