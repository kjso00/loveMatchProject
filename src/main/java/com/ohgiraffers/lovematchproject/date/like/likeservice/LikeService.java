package com.ohgiraffers.lovematchproject.date.like.likeservice;

import com.ohgiraffers.lovematchproject.date.dateexception.DateNotFoundException;
import com.ohgiraffers.lovematchproject.date.dateexception.DateQuotaExceededException;
import com.ohgiraffers.lovematchproject.date.dateexception.InvalidDateOperationException;
import com.ohgiraffers.lovematchproject.date.datemodel.dateentity.DateEntity;
import com.ohgiraffers.lovematchproject.date.daterepository.DateRepository;
import com.ohgiraffers.lovematchproject.date.like.likemodel.likeentity.LikeEntity;
import com.ohgiraffers.lovematchproject.date.like.likerepository.LikeRepository;
import com.ohgiraffers.lovematchproject.login.dto.CustomOAuth2User;
import com.ohgiraffers.lovematchproject.common.UserEntity;
import com.ohgiraffers.lovematchproject.login.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final DateRepository dateRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, DateRepository dateRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.dateRepository = dateRepository;
    }

    @Transactional
    public void toggleLike(Long dateId) {
        UserEntity user = getCurrentUser();
        if (user == null) {
            throw new InvalidDateOperationException("로그인이 필요합니다.");
        }

        // 찜하기 제한 확인
        if (isLikeQuotaExceeded(user)) {
            throw new DateQuotaExceededException();
        }

        // 찜하기 로직
        DateEntity date = dateRepository.findById(dateId)
                .orElseThrow(() -> new DateNotFoundException(dateId));

        LikeEntity existingLike = likeRepository.findByUserAndDate(user, date).orElse(null);

        if (existingLike != null) {
            likeRepository.delete(existingLike);
        } else {
            likeRepository.save(new LikeEntity(user, date));
        }
    }

    private UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomOAuth2User) {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) auth.getPrincipal();
            return userRepository.findByUserId(oAuth2User.getUsername());
        }
        return null;
    }

    private boolean isLikeQuotaExceeded(UserEntity user) {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        long dailyLikeCount = likeRepository.countLikesByUserAndDateRange(user, startOfDay, LocalDateTime.now());
        long totalLikeCount = likeRepository.countByUser(user);

        return dailyLikeCount >= 10 || totalLikeCount >= 50;
    }
}