package org.example.yalla_api.common.repositories.excursions;

import org.example.yalla_api.common.entities.excursion.category.Category;
import org.example.yalla_api.common.entities.excursion.category.CategoryTranslation;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryTranslationRepository extends BaseRepository<CategoryTranslation, Long> {
}
