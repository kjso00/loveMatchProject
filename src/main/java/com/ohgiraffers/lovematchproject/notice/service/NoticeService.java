package com.ohgiraffers.lovematchproject.notice.service;

import com.ohgiraffers.lovematchproject.notice.model.dto.NoticeDTO;
import com.ohgiraffers.lovematchproject.notice.model.entity.Notice;
import com.ohgiraffers.lovematchproject.notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Service
public class NoticeService {

    private static NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository boardRepository) {
        this.noticeRepository = boardRepository;
    }

    // 비즈니스 로직을 작성함
    @Transactional
    public String createPost(NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) { // 반환타입 정하기
        // 만약 게시글 제목이 중복된다면? findPost 게시글 제목
        Notice findPost = noticeRepository.findByNoticeTitle(noticeDTO.getNoticeTitle());
        // findPost 중복됨
        if (findPost != null) {
            redirectAttributes.addFlashAttribute("message", "게시글 제목 중복");
            return "redirect:/noticeregist";

        }

        // 사용자 입력 데이터를 DTO로 담아줌
        Notice notice = new Notice();
        notice.setCreateDate(new Date());
        notice.setNoticeContent(noticeDTO.getNoticeContent());
        notice.setNoticeTitle(noticeDTO.getNoticeTitle());

        noticeRepository.save(notice);
        redirectAttributes.addFlashAttribute("success", "게시글 성공적으로 등록");
        return "redirect:/noticeregist";

    }


    // 모든 게시물 목록 조회
    public List<Notice> getAllPosts() {
        return noticeRepository.findAll();
//        List<Notice> lists = noticeRepository.findAll();
//
//        if (lists.isEmpty()) {
//            throw new RuntimeException("게시글 목록이 비어 있습니다.");
//        }
//        return lists;
    }


    // 게시글 상세 조회
    public NoticeDTO getPostById(int id) {
        Notice post = noticeRepository.findById(id).get();
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setId(post.getId());
        noticeDTO.setNoticeTitle(post.getNoticeTitle());
        noticeDTO.setNoticeContent(post.getNoticeContent());
        return noticeDTO;

    }


    // 게시글 수정
    public NoticeDTO updatePost(int id, NoticeDTO boardDTO) {
        // 게시글 업데이트 로직
        // 게시글 ID를 사용하여 데이터베이스에서 게시글을 조회
       Notice updatedPost = noticeRepository.findById(id).get();
        // optionalPost.isPresent() 메서드는 optionalPost가 값(게시글)을 가지고 있는지 확인

        if (updatedPost == null) {
            return null;
        }
        // 업데이트된 제목 설정
        updatedPost.setNoticeTitle(boardDTO.getNoticeTitle());
        // 업데이트된 내용 설정
        updatedPost.setNoticeContent(boardDTO.getNoticeContent());
        // 변경된 내용을 existingPost 데이터베이스에 저장
        // 수정 성공 여부가 없음
        Notice modifyPost = noticeRepository.save(updatedPost);

        NoticeDTO modifyDTO = new NoticeDTO();
        if(modifyPost != null){
            modifyDTO.setId(modifyPost.getId());
            modifyDTO.setNoticeContent(modifyPost.getNoticeContent());
            modifyDTO.setNoticeTitle(modifyPost.getNoticeTitle());
        }

          return modifyDTO;
    }

    public String deletePost(int id) {
        if (noticeRepository.existsById(id)) {
            noticeRepository.deleteById(id);
            return "게시글 삭제 성공";
        }
        return "게시글 삭제 실패";
    }
}




