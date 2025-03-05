package org.example.securityexam.securityExam4.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.securityexam.securityExam4.domain.Role;
import org.example.securityexam.securityExam4.domain.User;
import org.example.securityexam.securityExam4.domain.UserRegisterDTO;
import org.example.securityexam.securityExam4.repository.RoleRepository;
import org.example.securityexam.securityExam4.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "exam4/users/signup"; // signup.html 템플릿 반환
    }

    @PostMapping("/register123")
    public String register123(@ModelAttribute UserRegisterDTO user,

                              RedirectAttributes redirectAttributes) {
        try {

            if (userService.existsByUsername(user.getUsername())) {
                log.error("이미 있는 유저 입니다");
                redirectAttributes.addFlashAttribute("errorMessage", "이미 사용된 아이디 : " + user.getUsername());
                return "redirect:/signup";
            } else {

                userService.registerUser(user);

                return "redirect:/welcome";
            }

        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "회원가입 중 오류가 발생했습니다.");
            return "redirect:/signup";
        }
    }


    @GetMapping("/welcome")
    public String welcome() {
        return "exam4/users/welcome"; // welcome.html 필요
    }


    @GetMapping("/loginform")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "exam4/users/login"; // login.html 템플릿을 반환
    }

    @GetMapping("/myinfo")
    public String myinfo(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        System.out.println("123");
        if (userDetails != null) {
            // 인증된 사용자의 정보를 가져옴
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("authorities", "true");
            model.addAttribute("accountNonExpired", "true");
            model.addAttribute("accountNonLocked", "true");
            model.addAttribute("credentialsNonExpired", "true");
            model.addAttribute("enabled", "true");

        }

        return "exam4/users/myinfo"; // login.html 템플릿을 반환
    }

}
