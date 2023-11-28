package capstone.app.service;

import capstone.app.api.dto.UserDto;
import capstone.app.domain.*;
import capstone.app.exception.CustomException;
import capstone.app.exception.ErrorCode;
import capstone.app.jwt.SecurityUtil;
import capstone.app.repository.DealRepository;
import capstone.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final DealRepository dealRepository;

    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new CustomException(ErrorCode.ALREADY_PRESENT);
        }

        // 가입되어 있지 않은 회원이면,
        // 권한 정보 만들고
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // 유저 정보를 만들어서 save
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .callNumber(userDto.getCallNumber())
                .company(userDto.getCompany())
                .authorities(new ArrayList<>())
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

    @Transactional(readOnly = true)
    public List<Company> getDealCompaniesWithMe() {
        List<Deal> MyDeals = dealRepository.findByUsername(SecurityUtil.getCurrentUsername().get());

        return MyDeals.stream()
                .map(Deal::getCompany)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<String> getDealProductsWithMe() {
        List<Deal> MyDeals = dealRepository.findByUsername(SecurityUtil.getCurrentUsername().get());

        return MyDeals.stream()
                .map(Deal::getMeasurements)
                .flatMap(Collection::stream)
                .map(Measurement::getItem)
                .toList();
    }
}