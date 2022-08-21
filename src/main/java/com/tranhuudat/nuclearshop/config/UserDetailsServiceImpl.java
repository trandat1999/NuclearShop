package com.tranhuudat.nuclearshop.config;

import java.util.*;

import com.tranhuudat.nuclearshop.entity.Role;
import com.tranhuudat.nuclearshop.entity.User;
import com.tranhuudat.nuclearshop.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional= userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(()-> new UsernameNotFoundException("username not found: "+username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.isEnabled()
        ,true,true,true,grantedAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> grantedAuthorities(Set<Role> roles){
        List<SimpleGrantedAuthority> list= new ArrayList<>();
        for(Role role: roles){
            list.add(new SimpleGrantedAuthority(role.getName()));
        }
        return list;
    }
    
}
