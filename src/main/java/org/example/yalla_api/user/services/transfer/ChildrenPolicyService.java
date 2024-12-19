package org.example.yalla_api.user.services.transfer;

import org.example.yalla_api.common.entities.childrenPolicy.AgeRange;
import org.example.yalla_api.common.entities.childrenPolicy.ChildrenPolicy;
import org.example.yalla_api.common.repositories.childrenPolicy.ChildrenPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildrenPolicyService {

    @Autowired
    private ChildrenPolicyRepository childrenPolicyRepository;

    /**
     * Calculates the equivalent pax count from the list of children's ages.
     *
     * @param childrenAges List of children's ages.
     * @return The total pax calculated from the children.
     */
    public double calculatePaxFromChildren(List<Integer> childrenAges) {
        if (childrenAges == null || childrenAges.isEmpty()) {
            return 0;
        }

        // Retrieve the active ChildrenPolicy (assumes there's a single active policy at a time)
        ChildrenPolicy policy = getActiveChildrenPolicy();

        double totalPax = 0;

        for (Integer age : childrenAges) {
            AgeRange matchingRange = findMatchingAgeRange(policy.getAgeRanges(), age);

            if (matchingRange != null) {
                totalPax += matchingRange.getBasePrice();
            } else {
                throw new IllegalArgumentException("No age range found for child age: " + age);
            }
        }

        return totalPax;
    }

    /**
     * Finds the active children policy.
     *
     * @return The active ChildrenPolicy.
     */
    private ChildrenPolicy getActiveChildrenPolicy() {
        return childrenPolicyRepository.findActivePolicy()
                .orElseThrow(() -> new IllegalStateException("No active ChildrenPolicy found"));
    }

    /**
     * Finds the matching age range for a given child's age.
     *
     * @param ageRanges List of age ranges.
     * @param age       The age of the child.
     * @return The matching AgeRange.
     */
    private AgeRange findMatchingAgeRange(List<AgeRange> ageRanges, Integer age) {
        for (AgeRange range : ageRanges) {
            if (age >= range.getAgeFrom() && age <= range.getAgeTo()) {
                return range;
            }
        }
        return null;
    }
}