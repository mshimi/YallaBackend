package org.example.yalla_api.common.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaginationService<T> {
    Page<T> findAll(Pageable pageable);
}
