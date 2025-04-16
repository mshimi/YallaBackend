package org.example.yalla_api.common.logic.transfer.extras;

import org.example.yalla_api.common.entities.transfer.TransferExtra;
import org.example.yalla_api.common.logic.transfer.childrenpolicy.ChildPolicyEvaluator;
import org.example.yalla_api.common.services.transfer.TransferExtraService;
import org.example.yalla_api.user.services.transfer.ChildrenPolicyService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TransferExtraCalculator {

    private final TransferExtraService transferExtraService;


    public TransferExtraCalculator(TransferExtraService transferExtraService) {
        this.transferExtraService = transferExtraService;

    }

    public TransferExtras calculateItems(Map<Integer, Long> extras) {
        List<TransferExtraItem> result = new ArrayList<>();

        List<Long> ids = new ArrayList<>(extras.values());
        List<TransferExtra> extraEntities = transferExtraService.findAllById(ids);

        for (Map.Entry<Integer, Long> entry : extras.entrySet()) {
            int quantity = entry.getKey();
            long id = entry.getValue();

            TransferExtra extra = extraEntities.stream()
                    .filter(e -> e.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Extra ID not found: " + id));

            result.add(new TransferExtraItem(extra, quantity));
        }

        return new TransferExtras(result);
    }


}
