package org.example.yalla_api.common.repositories.core;

import org.example.yalla_api.common.entities.core.City;
import org.example.yalla_api.common.entities.image.Image;
import org.example.yalla_api.common.repositories.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends BaseRepository<Image, Long> {
    Image findByOwnerIdAndOwnerType(Long ownerId, String ownerType);
}