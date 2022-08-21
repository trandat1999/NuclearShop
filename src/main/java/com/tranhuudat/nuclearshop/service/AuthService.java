package com.tranhuudat.nuclearshop.service;

import com.tranhuudat.nuclearshop.jwt.JwtProvider;
import com.tranhuudat.nuclearshop.entity.NotificationEmail;
import com.tranhuudat.nuclearshop.entity.User;
import com.tranhuudat.nuclearshop.entity.VerificationToken;
import com.tranhuudat.nuclearshop.repository.NotificationEmailRepository;
import com.tranhuudat.nuclearshop.repository.UserRepository;
import com.tranhuudat.nuclearshop.repository.VerificationTokenRepository;
import com.tranhuudat.nuclearshop.request.LoginRequest;
import com.tranhuudat.nuclearshop.request.RefreshTokenRequest;
import com.tranhuudat.nuclearshop.request.RegisterRequest;
import com.tranhuudat.nuclearshop.response.AuthResponse;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.type.TypeEmail;
import com.tranhuudat.nuclearshop.type.YesNoStatus;
import com.tranhuudat.nuclearshop.util.CommonUtils;
import com.tranhuudat.nuclearshop.util.ConstUtil;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final NotificationEmailRepository notificationEmailRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public BaseResponse<?> register(RegisterRequest registerRequest) {
        User user = User.builder().username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .enabled(false)
                .accountNonExpired(false)
                .credentialsNonExpired(false)
                .accountNonLocked(false)
                .build();
        user = userRepository.save(user);

        VerificationToken verificationToken = generateVerificationToken(user);
        NotificationEmail notificationEmail = NotificationEmail.builder()
                .email(user.getEmail())
                .type(TypeEmail.REGISTER)
                .content(SystemMessage.CONTENT_MAIL_REGISTER)
                .subject(SystemMessage.SUBJECT_MAIL_REGISTER)
                .user(user)
                .link(ConstUtil.HOST_URL + ConstUtil.SLASH + ConstUtil.API + ConstUtil.SLASH
                        + ConstUtil.URL_VERIFICATION_TOKEN + ConstUtil.SLASH + verificationToken.getToken())
                .build();
        notificationEmail =  notificationEmailRepository.save(notificationEmail);
        mailService.sendMail(notificationEmail);
        return BaseResponse.builder()
                .body(true)
                .code(YesNoStatus.YES)
                .message(SystemMessage.MESSAGE_REGISTER_SUCCESS)
                .build();
    }

    private VerificationToken generateVerificationToken(User user) {
        VerificationToken verificationToken = VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryTime(LocalDateTime.now().plusMinutes(ConstUtil.TIME_MINUTES_EXPIRED_VERIFICATION_TOKEN))
                .build();
        verificationToken = verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public BaseResponse<?> verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElse(null);
        if(CommonUtils.isNotNull(verificationToken) && CommonUtils.isNotNull(verificationToken.getUser())){
            if(CommonUtils.isNull(verificationToken.getExpiryTime()) || verificationToken.getExpiryTime().isBefore(LocalDateTime.now())){
                return  BaseResponse.builder()
                        .body(false)
                        .code(YesNoStatus.NO)
                        .message(SystemMessage.MESSAGE_INVALID_TOKEN)
                        .build();
            }
            User user = userRepository.findByUsername(verificationToken.getUser().getUsername()).orElse(null);
            if (CommonUtils.isNotNull(user)){
                user.setAccountNonExpired(false);
                user.setAccountNonLocked(false);
                user.setEnabled(true);
                user.setCredentialsNonExpired(false);
                user = userRepository.save(user);
                return BaseResponse.builder()
                        .body(true)
                        .code(YesNoStatus.YES)
                        .message(SystemMessage.MESSAGE_ACCOUNT_IS_ACTIVE)
                        .build();
            }

        }
        return  BaseResponse.builder()
                .body(false)
                .code(YesNoStatus.NO)
                .message(SystemMessage.MESSAGE_INVALID_TOKEN)
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest){
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

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
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

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
