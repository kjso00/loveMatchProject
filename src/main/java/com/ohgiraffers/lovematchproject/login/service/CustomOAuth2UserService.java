package com.ohgiraffers.lovematchproject.login.service;

import com.ohgiraffers.lovematchproject.login.dto.*;
import com.ohgiraffers.lovematchproject.login.entity.UserEntity;
import com.ohgiraffers.lovematchproject.login.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사이트의 사용자 정보 데이터를 인자로 받아옴
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        // 네이버인지 구글인지 어떤 인증 provider인지
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes()); // dto 객체에 담음
        } else if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes()); // dto 객체에 담음
        } else {
            return null;
        }

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId(); // 임의로 만들어준 id
        UserEntity existData = userRepository.findByUsername(username); // id값으로 repo에서 찾아서 넣어줌

        Role role; // enum 타입의 Role 클래스를 쓰기 위해 선언
        if (existData == null) { // 데이터가 없으면 신규회원
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setEmail(oAuth2Response.getEmail());
            role = Role.USER;
            userEntity.setRole(role);
            userRepository.save(userEntity); // 제공자에게 받아온 정보 넣어주고 entity 저장
        }
        else { // 존재하는경우 새로 업데이트 시켜줌
            existData.setUsername(username);
            existData.setEmail(oAuth2Response.getEmail());
            role = existData.getRole();
            existData.setRole(role);
            userRepository.save(existData); // 업데이트 한 정보 저장
        }
        return new CustomOAuth2User(oAuth2Response, role.getKey());
    }
}
