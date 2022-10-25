package com.tranhuudat.nuclearshop.service;

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
import lombok.AllArgsConstructor;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
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

    public BaseResponse register(RegisterRequest registerRequest) {
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
        TypedParameterValue username = new TypedParameterValue(StandardBasicTypes.STRING, request.getUsername());
        TypedParameterValue email = new TypedParameterValue(StandardBasicTypes.STRING, request.getEmail());
        User user = userRepository.findByUsernameAndEmail(username, email).orElse(null);
        if(user!=null){
            String newPassword = CommonUtils.generatePassword();
            user.setPassword(passwordEncoder.encode(newPassword));
            user =  userRepository.save(user);
            senMailResetPassword(newPassword,user);
            return getResponse200(SystemMessage.MESSAGE_RESET_PASSWORD_SUCCESS_INFO,
                    messageSource.getMessage(SystemMessage.MESSAGE_RESET_PASSWORD_SUCCESS,null,Locale.ROOT));
        }
        return getResponse404(messageSource.getMessage(SystemMessage.MESSAGE_USERNAME_EMAIL_NOT_MATCH,
                new Object[]{request.getUsername(),request.getEmail()} , Locale.ROOT));
    }
}
