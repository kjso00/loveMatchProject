package com.ohgiraffers.lovematchproject.notice.repository;


import com.ohgiraffers.lovematchproject.notice.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    Notice findByNoticeTitle(String noticeTitle);
}
