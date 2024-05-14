package com.tranhuudat.nuclearshop.service;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import com.tranhuudat.nuclearshop.entity.Person;
import com.tranhuudat.nuclearshop.entity.Role;
import com.tranhuudat.nuclearshop.entity.User;
import com.tranhuudat.nuclearshop.entity.VerificationToken;
import com.tranhuudat.nuclearshop.jwt.JwtProvider;
import com.tranhuudat.nuclearshop.repository.RoleRepository;
import com.tranhuudat.nuclearshop.repository.UserRepository;
import com.tranhuudat.nuclearshop.repository.VerificationTokenRepository;
import com.tranhuudat.nuclearshop.request.LoginRequest;
import com.tranhuudat.nuclearshop.request.RefreshTokenRequest;
import com.tranhuudat.nuclearshop.request.RegisterRequest;
import com.tranhuudat.nuclearshop.request.ResetPasswordRequest;
import com.tranhuudat.nuclearshop.response.AuthResponse;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.util.CommonUtils;
import com.tranhuudat.nuclearshop.util.ConstUtil;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import com.tranhuudat.nuclearshop.util.SystemVariable;
import lombok.AllArgsConstructor;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.MapUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService extends BaseService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository;
    private final MessageSource messageSource;
    private final CaptchaService captchaService;

    public BaseResponse register(RegisterRequest registerRequest) {
        Map<String, String> errors = new HashMap<>();
        if(checkExistEmail(registerRequest.getEmail())){
            errors.put(SystemVariable.EMAIL, getMessage(SystemMessage.MESSAGE_ERROR_EXISTS,registerRequest.getEmail()));
        }
        if(checkExistUsername(registerRequest.getUsername())){
            errors.put(SystemVariable.USERNAME, getMessage(SystemMessage.MESSAGE_ERROR_EXISTS,registerRequest.getUsername()));
        }
        if(!MapUtils.isEmpty(errors)){
            return getResponse400(SystemMessage.MESSAGE_BAD_REQUEST, errors);
        }
        Role userRole = roleRepository.findByName(ConstUtil.USER_ROLE).orElse(Role.builder().name(ConstUtil.USER_ROLE).build());
        Person person = Person.builder()
                .email(registerRequest.getEmail())
                .build();
        User user = User.builder().username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .enabled(false)
                .accountNonExpired(false)
                .credentialsNonExpired(false)
                .accountNonLocked(false)
                .person(person)
                .roles(Set.of(userRole))
                .build();
        user = userRepository.save(user);
        sendMailActive(user);
        return getResponse200(true,SystemMessage.MESSAGE_REGISTER_SUCCESS);
    }

    public BaseResponse verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElse(null);
        if (CommonUtils.isNotNull(verificationToken) && CommonUtils.isNotNull(verificationToken.getUser())) {
            if (CommonUtils.isNull(verificationToken.getExpiryTime()) || verificationToken.getExpiryTime().isBefore(LocalDateTime.now())) {
                return  getResponse404(SystemMessage.MESSAGE_INVALID_TOKEN);
            }
            User user = userRepository.findByUsername(verificationToken.getUser().getUsername()).orElse(null);
            if (CommonUtils.isNotNull(user)) {
                user.setAccountNonExpired(true);
                user.setAccountNonLocked(true);
                user.setEnabled(true);
                user.setCredentialsNonExpired(true);
                user = userRepository.save(user);
                return getResponse200(true,SystemMessage.MESSAGE_ACCOUNT_IS_ACTIVE);
            }
        }
        return getResponse404(SystemMessage.MESSAGE_INVALID_TOKEN);
    }

    public AuthResponse login(LoginRequest loginRequest) {
//        boolean captchaVerified = captchaService.verify(loginRequest.getRecaptchaResponse());
//        if(!captchaVerified) {
//            return null;
//        }
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthResponse.builder()
                .accessToken(token)
                .expiresAt(LocalDateTime.now().plusSeconds(ConstUtil.TIMEOUT_TOKEN))
                .refreshToken(refreshTokenService.generateRefreshToken(loginRequest.getUsername()).getToken())
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest);
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(LocalDateTime.now().plusSeconds(ConstUtil.TIMEOUT_TOKEN))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public BaseResponse resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByUsernameAndEmail(request.getUsername(), request.getEmail()).orElse(null);
        if(user!=null){
            String newPassword = CommonUtils.generatePassword();
            user.setPassword(passwordEncoder.encode(newPassword));
            user =  userRepository.save(user);
            senMailResetPassword(newPassword,user);
            return getResponse200(SystemMessage.MESSAGE_RESET_PASSWORD_SUCCESS_INFO,
                    messageSource.getMessage(SystemMessage.MESSAGE_RESET_PASSWORD_SUCCESS,null, LocaleContextHolder.getLocale()));
        }
        return getResponse404(messageSource.getMessage(SystemMessage.MESSAGE_USERNAME_EMAIL_NOT_MATCH,
                new Object[]{request.getUsername(),request.getEmail()} , LocaleContextHolder.getLocale()));
    }

    public boolean checkExistEmail(String email){
        long count = userRepository.countUserByEmail(email);
        return count != 0;
    }

    public boolean checkExistUsername(String email){
        long count = userRepository.countUserByUsername(email);
        return count != 0;
    }
}
