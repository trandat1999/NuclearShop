package com.tranhuudat.nuclearshop.util;

import com.tranhuudat.nuclearshop.entity.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return CommonUtils.isNotNull(authentication) && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public static User getCurrentUser() {
        if (!isAuthenticated()) {
            return null;
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (User)authentication.getPrincipal();
        }
    }

    public static Object getPrincipal() {
        if (!isAuthenticated()) {
            return null;
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authentication.getPrincipal();
        }
    }
}
