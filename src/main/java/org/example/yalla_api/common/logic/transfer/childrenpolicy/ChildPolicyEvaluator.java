package org.example.yalla_api.common.logic.transfer.childrenpolicy;

import org.example.yalla_api.common.entities.childrenPolicy.AgeRange;
import org.example.yalla_api.common.entities.childrenPolicy.ChildrenPolicy;
import org.example.yalla_api.user.services.transfer.ChildrenPolicyService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChildPolicyEvaluator {

    public ChildPolicyEvaluator(ChildrenPolicyService childrenPolicyService ){
        this.childrenPolicyService = childrenPolicyService;
    }

    private final ChildrenPolicyService childrenPolicyService;



    public EvaluatedChildren evaluate(List<Integer> childrenAges) {

        List<EvaluatedChild> evaluatedChildren = new ArrayList<>();
        if(childrenAges.isEmpty()){
            return new EvaluatedChildren(evaluatedChildren);
        }

        ChildrenPolicy policy = childrenPolicyService.getActiveChildrenPolicy();

        List<AgeRange> ageRanges = policy.getAgeRanges();



        for (int age : childrenAges) {
            AgeRange matchedRange = ageRanges.stream()
                    .filter(range -> age >= range.getAgeFrom() && age <= range.getAgeTo())
                    .findFirst()
                    .orElse(null); // don't throw here

            double basePrice = (matchedRange != null) ? matchedRange.getBasePrice() : 1.0;
            double paxValue = (matchedRange != null) ? matchedRange.getPaxValue() : 1.0;

            evaluatedChildren.add(new EvaluatedChild(age, basePrice, paxValue));
        }

        return new EvaluatedChildren(evaluatedChildren);
    }
}
