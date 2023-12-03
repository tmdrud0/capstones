package capstone.app.service;

import capstone.app.api.dto.UserDto;
import capstone.app.domain.*;
import capstone.app.exception.CustomException;
import capstone.app.exception.ErrorCode;
import capstone.app.jwt.SecurityUtil;
import capstone.app.repository.DealRepository;
import capstone.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public User signup(User user) {
        if (userRepository.findOneWithAuthoritiesByUsername(user.getUsername()).orElse(null) != null) {
            throw new CustomException(ErrorCode.ALREADY_PRESENT);
        }

        user.setActivated(true);
        user.setAuthorities(new ArrayList<>());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Transactional
    public User putUser(User user){
        User me = getMyUserWithAuthorities().get();

        if(user.getPassword() != null)
            user.setPassword(passwordEncoder.encode(user.getPassword()));

        me.setPassword(user.getPassword()==null?me.getPassword():user.getPassword());
        me.setName(user.getName()==null?me.getName():user.getName());
        me.setCallNumber(user.getCallNumber()==null?me.getCallNumber():user.getCallNumber());

        Company myCompany = me.getCompany(), company = user.getCompany();

        myCompany.setCompanyAddress(company.getCompanyAddress()==null? myCompany.getCompanyAddress() : company.getCompanyAddress());
        myCompany.setCompanyEmail(company.getCompanyEmail()==null? myCompany.getCompanyEmail() : company.getCompanyEmail());
        myCompany.setCompanyName(company.getCompanyName()==null? myCompany.getCompanyName() : company.getCompanyName());
        myCompany.setCompanyCallNumber(company.getCompanyCallNumber()==null?myCompany.getCompanyCallNumber():company.getCompanyCallNumber());
        myCompany.setCompanyFaxNumber(company.getCompanyFaxNumber()==null?myCompany.getCompanyFaxNumber():company.getCompanyFaxNumber());

        return me;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal==null);
        User user = (User)principal;

        String username = user.getUsername();
        return userRepository.findOneWithAuthoritiesByUsername(username);
        //return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
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