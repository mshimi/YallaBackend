package org.example.yalla_api.common.repositories.excursions;

import org.example.yalla_api.common.entities.excursion.category.Category;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
}
