package com.tranhuudat.nuclearshop.config;

import com.tranhuudat.nuclearshop.util.CommonUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UserAuditing implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String name = "anonymousUser";
        if (CommonUtils.isNotNull(SecurityContextHolder.getContext()) && CommonUtils.isNotNull(SecurityContextHolder.getContext().getAuthentication()) &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            name = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return Optional.of(name);
    }
}
