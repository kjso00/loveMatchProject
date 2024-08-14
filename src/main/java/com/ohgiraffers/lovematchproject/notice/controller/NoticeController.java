package com.ohgiraffers.lovematchproject.notice.controller;

import com.ohgiraffers.lovematchproject.notice.model.dto.NoticeDTO;
import com.ohgiraffers.lovematchproject.notice.model.entity.Notice;
import com.ohgiraffers.lovematchproject.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NoticeController {


    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {

        this.noticeService = noticeService;
    }


    // 메인 페이지 요청 처리
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/notice/admin/mainpage")
    public String mainpage() {
        return "notice/admin/mainpage";
    }

    // 게시판 페이지 요청 처리
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/noticeregist")
    public String regist() {

        return "notice/admin/noticeregist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/insert")
    public ModelAndView postpage(@ModelAttribute NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) {
        String result = String.valueOf(noticeService.createPost(noticeDTO, redirectAttributes));
        ModelAndView modelAndView = new ModelAndView();


        if (result == null) {
            modelAndView.addObject("error", "게시글 등록에 실패하였습니다.");
            modelAndView.setViewName("error"); // "error"는 에러 페이지로 사용될 템플릿 파일 이름입니다.
        } else {
            redirectAttributes.addFlashAttribute("success", "게시글이 성공적으로 등록되었습니다.");
            modelAndView.setViewName("redirect:/notice/admin/noticelist");
        }
        return modelAndView;
    }





    // [전체 조회]
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/notice/admin/noticelist")
    public ModelAndView noticelist() {
        List<Notice> noticelist = noticeService.getAllPosts();
        ModelAndView modelAndView = new ModelAndView("notice/admin/noticelist");

        if (noticelist == null || noticelist.isEmpty()) {
            modelAndView.addObject("noPost", true);
        } else {
            modelAndView.addObject("noticelist", noticelist);
        }

        return modelAndView;
    }







    // 상세 조회
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/notice/admin/noticelist/{id}")
    public ModelAndView getPostDetail(@PathVariable("id") int id) {
        NoticeDTO noticeDTO = noticeService.getPostById(id);
        ModelAndView modelAndView = new ModelAndView();

        if (noticeDTO == null) {
            modelAndView.setViewName("notice/admin/error");
            modelAndView.addObject("error", "게시물을 찾을 수 없습니다.");
        } else {
            modelAndView.setViewName("notice/admin/noticedetail");
            modelAndView.addObject("notice", noticeDTO);
        }

        return modelAndView;
    }




    // [수정]
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/notice/admin/noticelist/editpost/{id}")
    public ModelAndView editPost(@PathVariable("id") int id) {
        NoticeDTO edits = noticeService.getPostById(id);
        ModelAndView modelAndView = new ModelAndView();

        if (edits == null) {
            modelAndView.setViewName("redirect:/notice/admin/noticelist");
        } else {
            modelAndView.setViewName("notice/admin/editpost");
            modelAndView.addObject("noticeDTO", edits);
        }

        return modelAndView;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/notice/admin/noticelist/editpost/{id}")
    public ModelAndView updatePost(@PathVariable("id") int id, @ModelAttribute NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) {
        NoticeDTO modifyPost = noticeService.updatePost(id, noticeDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/notice/admin/noticelist");

        if (modifyPost == null) {
            redirectAttributes.addFlashAttribute("error", "게시글 수정 실패");
        } else {
            redirectAttributes.addFlashAttribute("success", "게시글 수정 완료");
        }

        return modelAndView;
    }





    // [삭제]
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/notice/admin/noticelist/{id}")
    public ModelAndView deletePost(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        String resultMessage = noticeService.deletePost(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/notice/admin/noticelist");

        if (resultMessage.contains("성공")) {
            redirectAttributes.addFlashAttribute("success", resultMessage);
        } else {
            redirectAttributes.addFlashAttribute("error", resultMessage);
        }

        return modelAndView;
    }

    // 사용자용 공지사항 목록 페이지
    @GetMapping("/notice/user/usernoticelist")
    public ModelAndView userNoticelist() {
        List<Notice> usernoticelist = noticeService.getAllPosts();
        ModelAndView modelAndView = new ModelAndView("notice/user/usernoticelist");

        if (usernoticelist == null || usernoticelist.isEmpty()) {
            modelAndView.addObject("noPost", true);
        } else {
            modelAndView.addObject("noticelist", usernoticelist);
        }

        return modelAndView;
    }

    // 사용자용 공지사항 상세 페이지
    @GetMapping("/notice/user/usernoticelist/{id}")
    public ModelAndView userPostDetail(@PathVariable("id") int id ) {
        NoticeDTO noticeDTO = noticeService.getPostById(id);
        ModelAndView modelAndView = new ModelAndView();

        if (noticeDTO == null) {
            modelAndView.setViewName("notice/user/error");
            modelAndView.addObject("error", "공지사항을 찾을 수 없습니다.");
        } else {
            modelAndView.setViewName("notice/user/usernoticedetail");
            modelAndView.addObject("notice", noticeDTO);
        }

        return modelAndView;
    }






}
















