package com.ohgiraffers.lovematchproject.notice.controller;



import com.ohgiraffers.lovematchproject.notice.model.dto.NoticeDTO;
import com.ohgiraffers.lovematchproject.notice.model.entity.Notice;
import com.ohgiraffers.lovematchproject.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @GetMapping("/manager")
    public String mainpage() {
        return "notice/admin/mainpage";
    }

    // 게시판 페이지 요청 처리
    @GetMapping("/noticeregist")
    public String regist() {

        return "notice/admin/noticeregist";
    }


    @PostMapping("/insert")
    public String postpage(@ModelAttribute NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) {

        String result = noticeService.createPost(noticeDTO, redirectAttributes);


        if (result == null) {
            // 등록 실패 시 메시지 추가
            redirectAttributes.addAttribute("message", "게시글 등록에 실패하였습니다");
            return "redirect:/notice/admin/noticeregist";
        } else {

            redirectAttributes.addAttribute("success", "게시글 성공적으로 등록");
            return "redirect:/notice/admin/noticelist";
        }
    }


    /* [전체 조회]*/
    @GetMapping("/notice/admin/noticelist")
    public String noticelist(Model model) {

        List<Notice> noticelist = noticeService.getAllPosts();


        // 게시물 목록이 비어있으면 noPost 속성을 true로 설정하여 처리
        if (noticelist == null || noticelist.size() == 0) {
            model.addAttribute("noPost", true);
        } else {

            model.addAttribute("noticelist", noticelist);
        }

        return "notice/admin/noticelist";
    }

    // 상세 조회
    @GetMapping("/notice/admin/noticelist/{id}")
    public String getPostDetail(@PathVariable("id") int id, Model model) {
        NoticeDTO noticeDTO = noticeService.getPostById(id);

        if (noticeDTO == null) {
            model.addAttribute("error", "게시물을 찾을 수 없습니다");
            return "notice/admin/error";

        } else {
            model.addAttribute("notice", noticeDTO);
            return "notice/admin/noticedetail";
        }
    }



    //[수정]
    // 게시글 수정 페이지 요청 처리
    @GetMapping("/notice/admin/noticelist/editpost/{id}")
    //요청 URL 경로의 일부로 전달된 id 값을 메서드의 파라미터 id에 바인딩 // Model model 컨트롤러에서 뷰로 데이터를 전달하는 데 사용
    public String editPost(@PathVariable("id") int id, Model model) {

        NoticeDTO edits = noticeService.getPostById(id);
        // 존재하지 않는 게시글이면 게시판 목록으로 리다이렉트

        if (edits == null) {
            return "redirect:/notice/admin/noticelist";
        }
        //boardDTO 뷰에서 참조할 키값
        model.addAttribute("noticeDTO", edits); // boardDTO가 실제로는 Post 객체를 의미하는 것으로 가정한다.
        return "notice/admin/editpost"; // editpost.html로 반환하여 게시글 수정페이지를 표시
    }


    //POSt요청 -> 서버에 데이터를 보내고 서버에 있는 리소스를 생성하거나 업데이트하는데 사용
    //사용자가 수정 내용 입력하고 저장 버튼 누르면 경로 변수 id와 수정된 게시글 데이터가 함께 전달!!
    @PostMapping("/notice/admin/noticelist/editpost/{id}")
    public String updatePost(@PathVariable("id") int id, @ModelAttribute NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) {

        NoticeDTO modifyPost = noticeService.updatePost(id, noticeDTO);

        if (modifyPost == null) {
            redirectAttributes.addFlashAttribute("error", "게시글 수정 실패");
        } else {
            redirectAttributes.addFlashAttribute("success", "게시글 수정 완료");
        }


//        return "redirect:/notice/admin/noticelist"+modifyPost.getId();
        return "redirect:/notice/admin/noticelist";

    }


    //[삭제]
    //게시글 삭제 요청 처리
    @PostMapping("/notice/admin/noticelist/{id}")
    public String deletePost(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        String resultMessage = noticeService.deletePost(id); // 삭제 결과와 메시지를 반환받음

        // 메시지를 Flash Attribute로 추가
        if (resultMessage.contains("성공")) {
            redirectAttributes.addFlashAttribute("success", resultMessage);
        } else {
            redirectAttributes.addFlashAttribute("error", resultMessage);
        }

        // 게시물 목록 페이지로 리다이렉트
        return "redirect:/notice/admin/noticelist";
    }


}
















