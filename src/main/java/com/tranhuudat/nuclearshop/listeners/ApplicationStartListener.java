package com.tranhuudat.nuclearshop.listeners;

import com.tranhuudat.nuclearshop.entity.Person;
import com.tranhuudat.nuclearshop.entity.Role;
import com.tranhuudat.nuclearshop.entity.User;
import com.tranhuudat.nuclearshop.repository.RoleRepository;
import com.tranhuudat.nuclearshop.repository.UserRepository;
import com.tranhuudat.nuclearshop.type.Gender;
import com.tranhuudat.nuclearshop.util.ConstUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Environment env;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        log.info("Application started.");
        if (env.containsProperty(ConstUtil.PATH_FILE_IMAGE_PROPERTY) && env.getProperty(ConstUtil.PATH_FILE_IMAGE_PROPERTY) != null) {
            ConstUtil.FILE_PATH_IMAGE = env.getProperty(ConstUtil.PATH_FILE_IMAGE_PROPERTY);
        }
        createAdminUser();
    }

    private void createAdminUser() {
        User user = userRepository.findByUsername(ConstUtil.ADMIN_USERNAME).orElse(null);
        if (user == null) {
            Role adminRole = roleRepository.findByName(ConstUtil.ADMIN_ROLE).orElse(Role.builder().name(ConstUtil.ADMIN_ROLE).build());
            Person person = Person.builder()
                    .birthDate(new Date())
                    .displayName(ConstUtil.ADMIN_USERNAME)
                    .email(ConstUtil.ADMIN_EMAIL)
                    .firstName(ConstUtil.ADMIN_USERNAME)
                    .gender(Gender.MALE)
                    .lastName(ConstUtil.ADMIN_USERNAME)
                    .build();
            user = User.builder()
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .accountNonExpired(true)
                    .enabled(true)
                    .email(ConstUtil.ADMIN_EMAIL)
                    .password(passwordEncoder.encode(ConstUtil.ADMIN_PASSWORD))
                    .username(ConstUtil.ADMIN_USERNAME)
                    .person(person)
                    .roles(Set.of(adminRole))
                    .build();
            userRepository.save(user);
        }
    }

}
