package com.heath.bo.user.domain.service;

import com.heath.bo.user.domain.repository.UserRepository;
import com.heath.bo.user.domain.model.enums.Role;
import com.heath.bo.user.domain.model.User;
import com.heath.bo.user.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("등록되지 않는 사용자 입니다."));
    }

    public Long save(UserDto userDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(userDto.getPassword());

        return userRepository.save(
                User.builder()
                        .email(userDto.getEmail())
                        .name(userDto.getName())
                        .password(encode)
                        .role(Role.valueOf(userDto.getRole()))
                        .build()
        ).getId();
    }
}