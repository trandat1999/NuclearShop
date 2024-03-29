package com.tranhuudat.nuclearshop.service;

import com.tranhuudat.nuclearshop.entity.NotificationEmail;
import com.tranhuudat.nuclearshop.entity.User;
import com.tranhuudat.nuclearshop.entity.VerificationToken;
import com.tranhuudat.nuclearshop.repository.NotificationEmailRepository;
import com.tranhuudat.nuclearshop.repository.VerificationTokenRepository;
import com.tranhuudat.nuclearshop.dto.search.SearchRequest;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.type.TypeEmail;
import com.tranhuudat.nuclearshop.util.CommonUtils;
import com.tranhuudat.nuclearshop.util.ConstUtil;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public abstract class BaseService {

    protected ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

    @Resource
    private Validator validator;
    @Resource
    private MessageSource messageSource;
    @Resource
    private NotificationEmailRepository notificationEmailRepository;
    @Resource
    private MailService mailService;
    @Resource
    private VerificationTokenRepository verificationTokenRepository;

    protected BaseResponse getResponse404(String message) {
        return BaseResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.name())
                .message(message)
                .build();
    }

    protected BaseResponse getResponse200(Object object, String message) {
        return BaseResponse.builder()
                .body(object)
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .message(message)
                .build();
    }

    protected BaseResponse getResponse204(Object object, String message) {
        return BaseResponse.builder()
                .body(object)
                .code(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NO_CONTENT.name())
                .message(message)
                .build();
    }

    protected BaseResponse getResponse201(Object object, String message) {
        return BaseResponse.builder()
                .body(object)
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED.name())
                .message(message)
                .build();
    }

    protected BaseResponse getResponse400(String message, Object object) {
        return BaseResponse.builder()
                .body(object)
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .message(message)
                .build();
    }

    protected BaseResponse getResponse400(String message) {
        return BaseResponse.builder()
                .body(null)
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .message(message)
                .build();
    }

    protected BaseResponse getResponse500(String message) {
        return BaseResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(message)
                .build();
    }

    protected void sendMailActive(User user) {
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
        notificationEmail = notificationEmailRepository.save(notificationEmail);
        mailService.sendMail(notificationEmail);
    }

    protected VerificationToken generateVerificationToken(User user) {
        VerificationToken verificationToken = VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryTime(LocalDateTime.now().plusMinutes(ConstUtil.TIME_MINUTES_EXPIRED_VERIFICATION_TOKEN))
                .build();
        verificationToken = verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    protected void senMailResetPassword(String password, User user) {
        NotificationEmail notificationEmail = NotificationEmail.builder()
                .email(user.getEmail())
                .type(TypeEmail.FORGOT)
                .content(messageSource.getMessage(SystemMessage.CONTENT_MAIL_RESET_PASSWORD,new Object[]{password}, LocaleContextHolder.getLocale()))
                .subject(SystemMessage.SUBJECT_MAIL_RESET_PASSWORD)
                .user(user)
                .link(null)
                .build();
        notificationEmail = notificationEmailRepository.save(notificationEmail);
        mailService.sendMailResetPassword(notificationEmail);
    }

    protected String getMessage(String message, Object... objects){
        return messageSource.getMessage(message, objects, LocaleContextHolder.getLocale());
    }

    protected Pageable getPageable(Object object){
        SearchRequest searchRequest = (SearchRequest) object;
        int pageIndex = 0;
        int pageSize = 10;
        if (CommonUtils.isNotNull(searchRequest.getPageIndex()) && searchRequest.getPageIndex() >= 0) {
            pageIndex = searchRequest.getPageIndex();
        }
        if (CommonUtils.isNotNull(searchRequest.getPageSize()) && searchRequest.getPageSize() > 0) {
            pageSize = searchRequest.getPageSize();
        }
        return PageRequest.of(pageIndex, pageSize);
    }

    protected HashMap<String, String> validation(Object object){
        HashMap<String, String> rs = new HashMap<>();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> constraintViolation : violations) {
                rs.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage());
            }
        }
        return rs;
    }
}
