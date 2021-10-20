package com.example.mybatis.web.member;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import com.example.mybatis.domain.member.MemberService;
import com.example.mybatis.web.SessionConst;
import com.example.mybatis.web.login.AuthController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private MemberMapper memberMapper;
    private MemberService memberService;
    private AuthController authController;

    @Autowired
    public MemberController(MemberMapper memberMapper, MemberService memberService, AuthController authController) {
        this.memberMapper = memberMapper;
        this.memberService = memberService;
        this.authController = authController;
    }

    @GetMapping("/add")
    public String addForm() {
        return "member/addForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Member member) {
        memberMapper.insert(member);
        return "redirect:/";
    }

    @GetMapping("/updateForm")
    public String updateForm(@SessionAttribute(name = SessionConst.LOGIN_USER) Member loginUser, Model model) {
        model.addAttribute("loginUser", loginUser);
        return "member/updateForm";
    }

    @PostMapping("/update")
    public String update(HttpServletRequest request,
                         @SessionAttribute(name = SessionConst.LOGIN_USER) Member loginUser,
                         @RequestParam String password,
                         @RequestParam String name,
                         Model model) throws Exception {
        log.info("id={}, email={}", loginUser.getId(), loginUser.getEmail());
        Long memberId = loginUser.getId();

        if(password.isBlank()) {
            password = null;
        }

        int result = memberMapper.Update(password, name, loginUser);

        if (result == 0) {
            throw new Exception("Error");
        } else {
            authController.logoutByHttpSession(request);
            Member currentUser = memberService.findMemberById(memberId);

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_USER, currentUser);
            return "redirect:/member/updateForm";
        }
    }

}
