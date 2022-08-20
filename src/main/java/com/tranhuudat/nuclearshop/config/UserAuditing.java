package com.tranhuudat.nuclearshop.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UserAuditing implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String name = "unknownUser";
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            name = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return Optional.of(name);
    }
}
