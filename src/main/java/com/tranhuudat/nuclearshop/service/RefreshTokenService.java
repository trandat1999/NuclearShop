package com.tranhuudat.nuclearshop.service;

import com.tranhuudat.nuclearshop.entity.RefreshToken;
import com.tranhuudat.nuclearshop.exception.NuclearShopException;
import com.tranhuudat.nuclearshop.repository.RefreshTokenRepository;
import com.tranhuudat.nuclearshop.request.RefreshTokenRequest;
import com.tranhuudat.nuclearshop.util.ConstUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(String username) {
        RefreshToken refreshToken = refreshTokenRepository.findByUsername(username).orElse(null);
        if(refreshToken!=null){
            refreshTokenRepository.delete(refreshToken);
        }
        RefreshToken refreshTokenNew = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiredDate(LocalDateTime.now().plusSeconds(ConstUtil.TIMEOUT_REFRESH_TOKEN))
                .username(username).build();
        return refreshTokenRepository.save(refreshTokenNew);
    }

    void validateRefreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken =  refreshTokenRepository.findByTokenAndUsername(refreshTokenRequest.getRefreshToken(), refreshTokenRequest.getUsername())
                .orElseThrow(() -> new NuclearShopException("Invalid refresh Token"));
        refreshToken.setExpiredDate(LocalDateTime.now().plusSeconds(ConstUtil.TIMEOUT_REFRESH_TOKEN));
        refreshTokenRepository.save(refreshToken);
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
