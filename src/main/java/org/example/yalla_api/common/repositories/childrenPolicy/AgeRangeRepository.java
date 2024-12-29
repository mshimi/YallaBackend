package org.example.yalla_api.common.repositories.childrenPolicy;

import org.example.yalla_api.common.entities.childrenPolicy.AgeRange;
import org.example.yalla_api.common.entities.childrenPolicy.ChildrenPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgeRangeRepository extends JpaRepository<AgeRange,Long> {


    List<AgeRange> getAllByChildrenPolicy(ChildrenPolicy childrenPolicy);
    
}
