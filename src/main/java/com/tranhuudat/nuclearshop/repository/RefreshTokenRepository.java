package com.tranhuudat.nuclearshop.repository;

import com.tranhuudat.nuclearshop.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByTokenAndUsername(String token,String username);
    Optional<RefreshToken> findByUsername(String username);

    @Modifying
    void deleteByToken(String token);
}
