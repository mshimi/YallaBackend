package org.example.yalla_api.admin.controllers.childrenpolicy;

import jakarta.validation.Valid;
import org.example.yalla_api.admin.dtos.transfer.AgeRangesDTO;
import org.example.yalla_api.admin.mappers.transfer.AgeRangeMapper;
import org.example.yalla_api.admin.mappers.transfer.TransferChildrenPolicyMapper;
import org.example.yalla_api.common.entities.childrenPolicy.AgeRange;
import org.example.yalla_api.common.entities.childrenPolicy.TransferChildrenPolicy;
import org.example.yalla_api.common.services.childrenPolicy.TransferChildrenPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/transfer/childrenpolicy")
public class TransferChildrenPolicyController {
    @Autowired
    private TransferChildrenPolicyService transferChildrenPolicyService;



    @Autowired
    @Qualifier("transferChildrenPolicyMapperImpl")
    private TransferChildrenPolicyMapper mapper;

    @Autowired
    private AgeRangeMapper ageRangeMapper;


    /**
     * Get the active TransferChildrenPolicy.
     *
     * @return ResponseEntity with the active policy.
     */
    @GetMapping("/active")
    public ResponseEntity<?> getActiveChildrenPolicy() {
        TransferChildrenPolicy activePolicy = transferChildrenPolicyService.getActiveChildrenPolicy();
        if(activePolicy != null){
            return ResponseEntity.ok(mapper.toDto(activePolicy));
        } else {
            return ResponseEntity.ok("no active children Policy");
        }
    }

    /**
     * Set a specific TransferChildrenPolicy as inactive.
     *
     * @param id The ID of the policy to deactivate.
     * @return ResponseEntity with no content.
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> setPolicyAsInactive(@PathVariable Long id) {
        transferChildrenPolicyService.setPolicyAsInactive(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Add a new AgeRange to the active policy.
     *
     * @param newAgeRange The AgeRange to add.
     * @return ResponseEntity with the updated policy.
     */
    @PostMapping("/add-age-range")
    public ResponseEntity<?> addAgeRangeToExistingPolicy(@Valid  @RequestBody  AgeRangesDTO newAgeRange) {
        TransferChildrenPolicy updatedPolicy = transferChildrenPolicyService.addAgeRangeToExistingPolicy(ageRangeMapper.toSource(newAgeRange));
        return ResponseEntity.ok(mapper.toDto(updatedPolicy));
    }

    /**
     * Create a new TransferChildrenPolicy with a list of AgeRanges.
     *
     * @param newAgeRanges The list of AgeRanges for the new policy.
     * @return ResponseEntity with the newly created policy.
     */
    @PostMapping("/new-policy")
    public ResponseEntity<?> createNewPolicyWithAgeRanges( @Valid @RequestBody  List<AgeRangesDTO> newAgeRanges) {
        List<AgeRange> ranges = newAgeRanges.stream().map(e -> ageRangeMapper.toSource(e)).toList();
        TransferChildrenPolicy newPolicy = (TransferChildrenPolicy) transferChildrenPolicyService.createNewPolicyWithAgeRanges(ranges);
        return ResponseEntity.ok(mapper.toDto(newPolicy));
    }
}
