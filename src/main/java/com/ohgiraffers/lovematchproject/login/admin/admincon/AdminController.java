package com.ohgiraffers.lovematchproject.login.admin.admincon;

import com.ohgiraffers.lovematchproject.login.admin.UserDTO;
import com.ohgiraffers.lovematchproject.login.admin.adminsv.AdminService;
import com.ohgiraffers.lovematchproject.login.model.dto.CustomOAuth2User;
import com.ohgiraffers.lovematchproject.login.model.dto.OAuth2Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/login/admin") // 이 경로로 들어오면
@PreAuthorize("hasAuthority('ADMIN')") // ADMIN권한만 접근가능
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping // 관리자 메인페이지 (회원리스트 노출)
    public ModelAndView adminPage(ModelAndView mv){
        List<UserDTO> userList = adminService.allUserList();
        mv.addObject("userList", userList);
        mv.setViewName("/login/admin/admin");
        return mv;
    }

    @GetMapping("/user/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        UserDTO userDTO = adminService.userDetail(id); // id 값을 기준으로 상세조회한 결과값을 DTO에 담음

        if(userDTO != null) {
            model.addAttribute("user", userDTO);
        }else {
            model.addAttribute("message","조회에 실패했습니다. 다시 시도해주세요.");
        }
        return "login/admin/user";
    }

//    @GetMapping("/user/delete/{id}")
//    public ModelAndView deleteUser(@PathVariable Integer id, RedirectAttributes rdat, ModelAndView mv){
//    }
}
