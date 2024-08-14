package com.ohgiraffers.lovematchproject.notice.service;

import com.ohgiraffers.lovematchproject.notice.model.dto.NoticeDTO;
import com.ohgiraffers.lovematchproject.notice.model.entity.Notice;
import com.ohgiraffers.lovematchproject.notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;


@Service
public class NoticeService {

    @Autowired
    private static NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository boardRepository) {
        this.noticeRepository = boardRepository;
    }




    @Transactional
    public ModelAndView createPost(NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        // 제목과 내용이 비어있는지 확인
        if (noticeDTO.getNoticeTitle() == null || noticeDTO.getNoticeTitle().trim().isEmpty() ||
                noticeDTO.getNoticeContent() == null || noticeDTO.getNoticeContent().trim().isEmpty()) {
            modelAndView.addObject("error", "제목과 내용을 입력해주세요.");
            modelAndView.setViewName("error"); // 에러 페이지로 포워딩
            return modelAndView;
        }

        // 제목 중복 확인
        Notice findPost = noticeRepository.findByNoticeTitle(noticeDTO.getNoticeTitle());
        if (findPost != null) {
            modelAndView.addObject("error", "게시글 제목이 중복됩니다.");
            modelAndView.setViewName("error"); // 에러 페이지로 포워딩
            return modelAndView;
        }

        // 새로운 공지사항 생성 및 저장
        Notice notice = new Notice();
        notice.setCreateDate(new Date());
        notice.setNoticeContent(noticeDTO.getNoticeContent());
        notice.setNoticeTitle(noticeDTO.getNoticeTitle());

        noticeRepository.save(notice);

        // 성공 메시지와 함께 리다이렉트
        redirectAttributes.addFlashAttribute("success", "게시글이 성공적으로 등록되었습니다.");
        modelAndView.setViewName("redirect:/notice/admin/noticelist");

        return modelAndView;
    }




    // 모든 게시물 목록 조회
    public List<Notice> getAllPosts() {
//        return noticeRepository.findAll();
        List<Notice> lists = noticeRepository.findAll();
        if (lists.isEmpty()) {
            return null;
        }
        return lists;
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
    public NoticeDTO updatePost(int id, NoticeDTO noticeDTO) {
        // 게시글 업데이트 로직
        // 게시글 ID를 사용하여 데이터베이스에서 게시글을 조회
       Notice updatedPost = noticeRepository.findById(id).get();


        if (updatedPost == null) {
            return null;
        }
        // 업데이트된 제목 설정
        updatedPost.setNoticeTitle(noticeDTO.getNoticeTitle());
        // 업데이트된 내용 설정
        updatedPost.setNoticeContent(noticeDTO.getNoticeContent());
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


        //게시글 삭제
        public String deletePost(int id) {
        if (noticeRepository.existsById(id)) {
            noticeRepository.deleteById(id);
            return "공지글 삭제 성공";
        }
             return "공지글 삭제 실패";
    }
}




