package org.example.yalla_api.common.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.example.yalla_api.common.utils.FilterSpecificationBuilder;
import org.example.yalla_api.common.utils.UpdateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseService<T, ID> {
    private final FilterSpecificationBuilder<T> filterSpecificationBuilder = new FilterSpecificationBuilder<>();


    protected abstract BaseRepository<T, ID> getRepository();


    public T save(T entity) {
        return getRepository().save(entity);
    }

    public Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    public List<T> findAllById(List<ID> id) {
       return getRepository().findAllById(id);
    }

    public T update(ID id, T dto) {
        T existingEntity = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        UpdateUtil.copyNonNullProperties(dto, existingEntity);
        return getRepository().save(existingEntity);
    }

    public void deleteById(ID id) {
        T existingEntity = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        getRepository().delete(existingEntity);
    }

    public List<T> findAll(Map<String, String> filters) {

        if (filters != null && !filters.isEmpty()) {
            Specification<T> specification = filterSpecificationBuilder.buildSpecification(filters);
            return getRepository().findAll(specification);
        }

        return getRepository().findAll();
    }

    public Page<T> findAll(Pageable pageable,Map<String, String> filters) {

        if (filters != null && !filters.isEmpty()) {
            Specification<T> specification = filterSpecificationBuilder.buildSpecification(filters);
            return getRepository().findAll(specification,pageable);
        }

        return getRepository().findAll(pageable);
    }


}