package com.example.mybatis.web.member;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import com.example.mybatis.domain.member.MemberService;
import com.example.mybatis.web.SessionConst;
import com.example.mybatis.web.login.AuthController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;
    private final AuthController authController;

    public MemberController(MemberMapper memberMapper, MemberService memberService, AuthController authController) {
        this.memberMapper = memberMapper;
        this.memberService = memberService;
        this.authController = authController;
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute(new Member());
        return "member/addForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Member member, BindingResult bindingResult, @RequestParam String password2) throws Exception {

        log.info("password length={}", member.getPassword().length());

        if(!Pattern.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", member.getEmail())) {
            bindingResult.addError(new FieldError("member", "email", "Please enter the correct email format."));
        }
        if(!StringUtils.hasText(member.getPassword()) || member.getPassword().length() < 10 || member.getPassword().length() > 20) {
            bindingResult.addError(new FieldError("member", "password", "Password must be at least 10 characters long."));
        }
        if(!member.getPassword().equals(password2)) {
            bindingResult.addError(new ObjectError("member", "Password is not equal."));
        }
        if(member.getName().length() < 4) {
            bindingResult.addError(new FieldError("member", "name", "Name must be at least 3 characters long."));
        }

        if(memberMapper.findByEmail(member.getEmail()) > 0) {
            bindingResult.addError(new ObjectError("member", "An account with this email already exists."));
        }

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "member/addForm";
        }
        memberService.save(member);
        return "redirect:/message";
    }

    @GetMapping("/{authKey}")
    public String mailChek(@PathVariable String authKey) {
        memberMapper.mailCheck(authKey);
        return "redirect:/message";
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
