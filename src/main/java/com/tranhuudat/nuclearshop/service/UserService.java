package com.tranhuudat.nuclearshop.service;

import com.tranhuudat.nuclearshop.entity.Person;
import com.tranhuudat.nuclearshop.entity.Role;
import com.tranhuudat.nuclearshop.entity.User;
import com.tranhuudat.nuclearshop.repository.RoleRepository;
import com.tranhuudat.nuclearshop.repository.UserRepository;
import com.tranhuudat.nuclearshop.request.RoleRequest;
import com.tranhuudat.nuclearshop.request.UserRequest;
import com.tranhuudat.nuclearshop.request.search.SearchRequest;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.response.CurrentUserResponse;
import com.tranhuudat.nuclearshop.response.UserResponse;
import com.tranhuudat.nuclearshop.util.CommonUtils;
import com.tranhuudat.nuclearshop.util.SecurityUtils;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import com.tranhuudat.nuclearshop.util.SystemVariable;
import lombok.AllArgsConstructor;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService extends BaseService {

    private UserRepository userRepository;
    private MessageSource messageSource;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public BaseResponse update(UserRequest userRequest, Long id) {
        User user = null;
        if (id != null) {
            user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return getResponse404(messageSource.getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,
                        new Object[]{SystemVariable.USER}, LocaleContextHolder.getLocale()));
            }
        }
        if (CollectionUtils.isEmpty(userRequest.getRoles())) {
            return getResponse400(messageSource.getMessage(SystemMessage.MESSAGE_NOT_NULL_OR_EMPTY_LIST,
                    new Object[]{SystemVariable.ROLES}, LocaleContextHolder.getLocale()));
        }
        Person person = Person.builder()
                .idNumberIssueBy(userRequest.getPerson().getIdNumberIssueBy())
                .lastName(userRequest.getPerson().getLastName())
                .gender(userRequest.getPerson().getGender())
                .firstName(userRequest.getPerson().getFirstName())
                .email(userRequest.getEmail())
                .displayName(userRequest.getPerson().getFirstName() + userRequest.getPerson().getLastName())
                .birthDate(userRequest.getPerson().getBirthDate())
                .idNumber(userRequest.getPerson().getIdNumber())
                .idNumberIssueDate(userRequest.getPerson().getIdNumberIssueDate())
                .phoneNumber(userRequest.getPerson().getPhoneNumber())
                .build();
        Set<Role> roles = new HashSet<>();
        for (RoleRequest roleRequest : userRequest.getRoles()) {
            try {
                Role role = roleRepository.getOne(roleRequest.getId());
                roles.add(role);
            } catch (EntityNotFoundException e) {
                return getResponse404(messageSource.getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,
                        new Object[]{SystemVariable.ROLE}, LocaleContextHolder.getLocale()));
            }
        }
        user = User.builder()
                .roles(roles)
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .person(person)
                .email(userRequest.getEmail())
                .accountNonExpired(false)
                .credentialsNonExpired(false)
                .accountNonLocked(false)
                .build();
        user.setId(id);
        if (id == null) {
            user.setEnabled(false);
        } else {
            user.setEnabled(userRequest.getEnabled());
        }
        user = userRepository.save(user);
        UserResponse userResponse = userRepository.getById(user.getId());
        if (id == null) {
            sendMailActive(user);
            return getResponse201(userResponse,
                    messageSource.getMessage(SystemMessage.MESSAGE_CREATE_SUCCESS, new Object[]{SystemVariable.USER}, LocaleContextHolder.getLocale()));
        }
        return getResponse200(userResponse,
                messageSource.getMessage(SystemMessage.MESSAGE_UPDATE_SUCCESS, new Object[]{SystemVariable.USER}, LocaleContextHolder.getLocale()));

    }

    public BaseResponse save(UserRequest userRequest) {
        return update(userRequest, null);
    }

    public BaseResponse get(long id) {
        UserResponse userResponse = userRepository.getById(id);
        if (CommonUtils.isNull(userResponse)) {
            return getResponse404(messageSource.getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,
                    new Object[]{SystemVariable.ROLE}, LocaleContextHolder.getLocale()));
        }
        return getResponse200(userResponse, messageSource.getMessage(SystemMessage.MESSAGE_FOUND, new Object[]{SystemVariable.USER}, LocaleContextHolder.getLocale()));
    }

    public BaseResponse getAll() {
        List<UserResponse> userResponseList = userRepository.getAll();
        return getResponse200(userResponseList, messageSource.getMessage(SystemMessage.MESSAGE_GET_ALL_SUCCESS, new Object[]{SystemVariable.USER}, LocaleContextHolder.getLocale()));
    }

    public BaseResponse getPage(SearchRequest searchRequest) {
        int pageIndex = 1;
        int pageSize = 10;
        if (CommonUtils.isNotNull(searchRequest.getPageIndex()) && searchRequest.getPageIndex() > 0) {
            pageIndex = searchRequest.getPageIndex();
        }
        if (CommonUtils.isNotNull(searchRequest.getPageSize()) && searchRequest.getPageSize() > 0) {
            pageSize = searchRequest.getPageSize();
        }
        TypedParameterValue keyWord = new TypedParameterValue(StandardBasicTypes.STRING, searchRequest.getKeyword());
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<UserResponse> userResponsePage = userRepository.getPage(keyWord, pageable);
        return getResponse200(userResponsePage, messageSource.getMessage(SystemMessage.MESSAGE_GET_PAGE_SUCCESS, new Object[]{SystemVariable.USER}, LocaleContextHolder.getLocale()));
    }

    public BaseResponse getCurrentUser() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityUtils.getPrincipal();
        if (user != null) {
            TypedParameterValue username = new TypedParameterValue(StandardBasicTypes.STRING, user.getUsername());
            CurrentUserResponse response = userRepository.getCurrentUser(username);
            return getResponse200(response, messageSource.getMessage(SystemMessage.MESSAGE_FOUND, new Object[]{SystemVariable.USER}, LocaleContextHolder.getLocale()));
        }
        return getResponse400(messageSource.getMessage(SystemMessage.MESSAGE_NOT_LOGIN, null, LocaleContextHolder.getLocale()));
    }
}
