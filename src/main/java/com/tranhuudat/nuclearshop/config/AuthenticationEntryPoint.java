package com.tranhuudat.nuclearshop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tranhuudat.nuclearshop.response.ErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

import static com.tranhuudat.nuclearshop.util.SystemMessage.MESSAGE_FORBIDDEN;
import static com.tranhuudat.nuclearshop.util.SystemMessage.MESSAGE_UNAUTHORIZED;

/**
 * @author DatNuclear 6/6/2023
 * @project NuclearShop
 */
@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint, AccessDeniedHandler {
    @Resource
    private MessageSource messageSource;
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .timestamp(LocalDateTime.now())
                .message(messageSource.getMessage(MESSAGE_UNAUTHORIZED,null, LocaleContextHolder.getLocale()))
                .build();
        OutputStream outputStream = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, errorResponse);
        outputStream.flush();
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(403);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN)
                .timestamp(LocalDateTime.now())
                .message(messageSource.getMessage(MESSAGE_FORBIDDEN,null, LocaleContextHolder.getLocale()))
                .build();
        OutputStream outputStream = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, errorResponse);
        outputStream.flush();
    }
}
