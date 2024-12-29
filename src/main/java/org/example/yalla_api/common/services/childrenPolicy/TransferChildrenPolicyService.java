package org.example.yalla_api.common.services.childrenPolicy;

import jakarta.transaction.Transactional;
import org.example.yalla_api.common.entities.childrenPolicy.AgeRange;
import org.example.yalla_api.common.entities.childrenPolicy.ChildrenPolicy;
import org.example.yalla_api.common.entities.childrenPolicy.TransferChildrenPolicy;
import org.example.yalla_api.common.repositories.childrenPolicy.AgeRangeRepository;
import org.example.yalla_api.common.repositories.childrenPolicy.ChildrenPolicyRepository;
import org.example.yalla_api.common.repositories.childrenPolicy.TransferChildrenPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferChildrenPolicyService {

    @Autowired
    TransferChildrenPolicyRepository childrenPolicyRepository;

    @Autowired
    AgeRangeRepository ageRangeRepository;

    public TransferChildrenPolicy getActiveChildrenPolicy(){

       return childrenPolicyRepository.findActivePolicy().orElse(null);

    }


    public void setPolicyAsInactive(Long id){
        TransferChildrenPolicy activePolicy =  childrenPolicyRepository.findById(id).orElseThrow();

        activePolicy.setActive(false);

        childrenPolicyRepository.save(activePolicy);

    }


    public TransferChildrenPolicy addAgeRangeToExistingPolicy(AgeRange newAgeRange) {
        // Retrieve the active policy
        TransferChildrenPolicy activePolicy = childrenPolicyRepository.findActivePolicy()
                .orElseThrow(() -> new IllegalArgumentException("No active ChildrenPolicy found."));

        // Get the current age ranges of the policy
        List<AgeRange> ageRanges = activePolicy.getAgeRanges();

        // Check for overlap
        for (AgeRange existingRange : ageRanges) {
            if (rangesOverlap(existingRange, newAgeRange)) {
                throw new IllegalArgumentException("New age range overlaps with an existing range: " + existingRange);
            }
        }

        // Add the new range to the age ranges
        newAgeRange.setChildrenPolicy(activePolicy);
        ageRanges.add(newAgeRange);

        // Save and return the updated policy
        return childrenPolicyRepository.save(activePolicy);
    }

    /**
     * Check if two AgeRanges overlap.
     *
     * @param range1 The first age range.
     * @param range2 The second age range.
     * @return True if the ranges overlap, false otherwise.
     */
    private boolean rangesOverlap(AgeRange range1, AgeRange range2) {
        return range1.getAgeFrom() <= range2.getAgeTo() && range1.getAgeTo() >= range2.getAgeFrom();
    }




    @Transactional
    public ChildrenPolicy createNewPolicyWithAgeRanges(List<AgeRange> newAgeRanges) {
        // Validate the new age ranges for overlaps
        validateAgeRanges(newAgeRanges);

        // Deactivate the current active policy
        List<TransferChildrenPolicy> activePolices = childrenPolicyRepository.findAllActivePolicies();

        if(!activePolices.isEmpty()){
            activePolices.forEach(e-> e.setActive(false));
            childrenPolicyRepository.saveAll(activePolices);
        }

        // Create the new policy
        TransferChildrenPolicy newPolicy = new TransferChildrenPolicy();
        newPolicy.setActive(true);

        // Associate the age ranges with the new policy
        newAgeRanges.forEach(range -> range.setChildrenPolicy(newPolicy));
        newPolicy.setAgeRanges(newAgeRanges);

        // Save and return the new policy
        return childrenPolicyRepository.save(newPolicy);
    }

    /**
     * Validate that age ranges do not overlap.
     *
     * @param ageRanges the list of age ranges to validate.
     */
    private void validateAgeRanges(List<AgeRange> ageRanges) {
        for (int i = 0; i < ageRanges.size(); i++) {
            for (int j = i + 1; j < ageRanges.size(); j++) {
                AgeRange range1 = ageRanges.get(i);
                AgeRange range2 = ageRanges.get(j);

                if (rangesOverlap(range1, range2)) {
                    throw new IllegalArgumentException("Age ranges overlap: " + range1 + " and " + range2);
                }
            }
        }
    }


}
