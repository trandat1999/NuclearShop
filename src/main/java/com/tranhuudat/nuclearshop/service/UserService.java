package com.tranhuudat.nuclearshop.service;

import com.tranhuudat.nuclearshop.entity.Person;
import com.tranhuudat.nuclearshop.entity.Role;
import com.tranhuudat.nuclearshop.entity.User;
import com.tranhuudat.nuclearshop.repository.FileRepository;
import com.tranhuudat.nuclearshop.repository.RoleRepository;
import com.tranhuudat.nuclearshop.repository.UserRepository;
import com.tranhuudat.nuclearshop.request.PersonRequest;
import com.tranhuudat.nuclearshop.request.RoleRequest;
import com.tranhuudat.nuclearshop.request.UserRequest;
import com.tranhuudat.nuclearshop.request.search.SearchRequest;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.response.CurrentUserResponse;
import com.tranhuudat.nuclearshop.response.PersonResponse;
import com.tranhuudat.nuclearshop.response.UserResponse;
import com.tranhuudat.nuclearshop.util.*;
import lombok.AllArgsConstructor;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.MapUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService extends BaseService {

    private UserRepository userRepository;
    private MessageSource messageSource;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private FileRepository fileRepository;

    public BaseResponse update(UserRequest userRequest, Long id) {
        if (StringUtils.isEmpty(userRequest.getPassword()) && id == null) {
            return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_NULL_OR_EMPTY_LIST, SystemVariable.PASSWORD));
        }
        if (!StringUtils.isEmpty(userRequest.getPassword()) && userRequest.getPassword().length() < ConstUtil.MIN_LENGTH_PASSWORD_REQUIRED && id != null) {
            return getResponse400(getMessage(SystemMessage.MESSAGE_INVALID_LENGTH, SystemVariable.PASSWORD, ConstUtil.MIN_LENGTH_PASSWORD_REQUIRED));
        }
        Map<String, String> errors = new HashMap<>();
        if(userRepository.checkExistEmailOrUsername(null,userRequest.getEmail(),id)!=0){
            errors.put(SystemVariable.EMAIL, getMessage(SystemMessage.MESSAGE_ERROR_EXISTS,userRequest.getEmail()));
        }
        if(userRepository.checkExistEmailOrUsername(userRequest.getUsername(),null,id)!=0){
            errors.put(SystemVariable.USERNAME, getMessage(SystemMessage.MESSAGE_ERROR_EXISTS,userRequest.getUsername()));
        }
        if(!MapUtils.isEmpty(errors)){
            return getResponse400(SystemMessage.MESSAGE_BAD_REQUEST, errors);
        }
        User user = null;
        if (id != null) {
            user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return getResponse404(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,SystemVariable.USER));
            }
        }
        if (CollectionUtils.isEmpty(userRequest.getRoles())) {
            return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_NULL_OR_EMPTY_LIST,SystemVariable.ROLES));
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
                .enabled(userRequest.getEnabled())
                .person(person)
                .email(userRequest.getEmail())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .id(id)
                .build();
        if (!StringUtils.isEmpty(userRequest.getPassword())) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        if (id == null) {
            user.setEnabled(false);
        } else {
            user.setEnabled(userRequest.getEnabled());
        }
        user = userRepository.save(user);
        UserResponse userResponse = userRepository.getById(user.getId());
        if (id == null) {
            sendMailActive(user);
            return getResponse201(userResponse, getMessage(SystemMessage.MESSAGE_CREATE_SUCCESS, SystemVariable.USER));
        }
        return getResponse200(userResponse, getMessage(SystemMessage.MESSAGE_UPDATE_SUCCESS, SystemVariable.USER));
    }

    public BaseResponse save(UserRequest userRequest) {
        return update(userRequest, null);
    }

    public BaseResponse get(long id) {
        UserResponse userResponse = userRepository.getById(id);
        if (CommonUtils.isNull(userResponse)) {
            return getResponse404(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,SystemVariable.ROLE));
        }
        return getResponse200(userResponse,getMessage(SystemMessage.MESSAGE_FOUND,SystemVariable.USER));
    }

    public BaseResponse getAll() {
        List<UserResponse> userResponseList = userRepository.getAll();
        return getResponse200(userResponseList,getMessage(SystemMessage.MESSAGE_GET_ALL_SUCCESS, SystemVariable.USER));
    }

    public BaseResponse getPage(SearchRequest searchRequest) {
        TypedParameterValue keyWord = new TypedParameterValue(StandardBasicTypes.STRING, searchRequest.getKeyword());
        Pageable pageable = getPageable(searchRequest);
        Page<UserResponse> userResponsePage = userRepository.getPage(keyWord, pageable);
        return getResponse200(userResponsePage,getMessage(SystemMessage.MESSAGE_GET_PAGE_SUCCESS, SystemVariable.USER));
    }

    public BaseResponse getCurrentUser() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityUtils.getPrincipal();
        if (user != null) {
            TypedParameterValue username = new TypedParameterValue(StandardBasicTypes.STRING, user.getUsername());
            CurrentUserResponse response = userRepository.getCurrentUser(username);
            return getResponse200(response,getMessage(SystemMessage.MESSAGE_FOUND, SystemVariable.USER));
        }
        return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_LOGIN));
    }

    public BaseResponse updateProfile(PersonRequest personRequest) {
        org.springframework.security.core.userdetails.User current = (org.springframework.security.core.userdetails.User) SecurityUtils.getPrincipal();
        if (current != null) {
            User entity = userRepository.findByUsername(current.getUsername()).orElse(null);
            if (entity == null) {
                return getResponse400(getMessage(SystemMessage.MESSAGE_BAD_REQUEST));
            }
            Person entityPerson = entity.getPerson();
            if (entityPerson == null) {
                entityPerson = new Person();
            }
            entityPerson.setBirthDate(personRequest.getBirthDate());
            entityPerson.setEmail(personRequest.getEmail());
            entityPerson.setFirstName(personRequest.getFirstName());
            entityPerson.setLastName(personRequest.getLastName());
            entityPerson.setPhoneNumber(personRequest.getPhoneNumber());
            entityPerson.setGender(personRequest.getGender());
            entityPerson.setIdNumber(personRequest.getIdNumber());
            entityPerson.setIdNumberIssueBy(personRequest.getIdNumberIssueBy());
            entityPerson.setIdNumberIssueDate(personRequest.getIdNumberIssueDate());
            if (CommonUtils.isNotNull(personRequest.getPhotoFile()) && CommonUtils.isNotNull(personRequest.getPhotoFile().getId())) {
                entityPerson.setPhotoFile(fileRepository.findById(personRequest.getPhotoFile().getId()).orElse(null));
            } else {
                entityPerson.setPhotoFile(null);
            }
            entity.setPerson(entityPerson);
            entity = userRepository.save(entity);
            return getResponse200(projectionFactory.createProjection(PersonResponse.class, entity.getPerson()),
                    getMessage(SystemMessage.MESSAGE_UPDATE_SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.MESSAGE_BAD_REQUEST));
    }

    public BaseResponse getProfile() {
        org.springframework.security.core.userdetails.User current = (org.springframework.security.core.userdetails.User) SecurityUtils.getPrincipal();
        if (current != null) {
            User entity = userRepository.findByUsername(current.getUsername()).orElse(null);
            if (entity == null) {
                return getResponse400(getMessage(SystemMessage.MESSAGE_BAD_REQUEST));
            }
            return getResponse200(projectionFactory.createProjection(PersonResponse.class, entity.getPerson()),
                    getMessage(SystemMessage.MESSAGE_UPDATE_SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.MESSAGE_BAD_REQUEST));
    }
}
