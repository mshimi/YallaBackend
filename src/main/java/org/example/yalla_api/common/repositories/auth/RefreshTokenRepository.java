package org.example.yalla_api.common.repositories.auth;

import org.example.yalla_api.common.entities.auth.RefreshToken;
import org.example.yalla_api.common.entities.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
    void deleteByUser(User user);
}
