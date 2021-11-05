package com.example.mybatis.web.member;

import com.example.mybatis.domain.member.Member;
import com.example.mybatis.domain.member.MemberMapper;
import com.example.mybatis.domain.member.MemberService;
import com.example.mybatis.web.SessionConst;
import com.example.mybatis.web.argumentResolver.Login;
import com.example.mybatis.web.login.AuthController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute(new SignInDto());
        return "member/addForm";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute SignInDto signInDto, BindingResult bindingResult, @RequestParam String password2) throws Exception {

        if(!signInDto.getPassword().equals(password2)) {
            bindingResult.addError(new ObjectError("member", "Password is not equal."));
        }

        if(memberMapper.findByEmail(signInDto.getEmail()) > 0) {
            bindingResult.addError(new ObjectError("member", "An account with this email already exists."));
        }

        if(memberMapper.findByName(signInDto.getName()) > 0) {
            bindingResult.addError(new ObjectError("member", "An account with this name already exists."));
        }

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "member/addForm";
        }

        Member member = new Member();
        member.setEmail(signInDto.getEmail());
        member.setName(signInDto.getName());
        member.setPassword(signInDto.getPassword());

        memberService.save(member);
        return "redirect:/message";
    }

    @GetMapping("/chk/{authKey}")
    public String mailChek(@PathVariable String authKey) {
        memberMapper.mailCheck(authKey);
        return "redirect:/message";
    }

    @GetMapping("/updateForm")
    public String updateForm(@Login Member loginUser, Model model) {
        model.addAttribute("loginUser", loginUser);

        UpdateDto updateDto = new UpdateDto();
        updateDto.setName(loginUser.getName());
        updateDto.setEmail(loginUser.getEmail());
        model.addAttribute("updateDto", updateDto);

        return "member/updateForm";
    }

    @PostMapping("/update")
    public String update(HttpServletRequest request,
                         @Login Member loginUser,
                         @Validated @ModelAttribute UpdateDto updateDto, BindingResult bindingResult,
                         Model model) throws Exception {
        Long memberId = loginUser.getId();

        if (updateDto.getCurrentPassword().isBlank() && updateDto.getPassword().isBlank() && updateDto.getName().equals(loginUser.getName())) {
            return "redirect:/member/updateForm";
        } else {
            if (memberService.verifyCurrentPassword(loginUser.getId(), updateDto.getCurrentPassword()) == 0) {
              bindingResult.addError(new FieldError("updateDto", "currentPassword", "Current password is not correct."));
            } else {
                if(memberMapper.findByName(updateDto.getName()) > 0) {
                    if(!updateDto.getName().equals(loginUser.getName())) {
                        bindingResult.addError(new FieldError("updateDto", "name", updateDto.getName(),false, null, null, "Same name already exists."));
                    }
                }
                if (!updateDto.getPassword().equals(updateDto.getPassword2())) {
                    bindingResult.addError(new FieldError("updateDto", "password2", "New password and check password are not equal."));
                }
                if(updateDto.getPassword().length() < 9 || updateDto.getPassword().length() > 21) {
                    bindingResult.addError(new FieldError("updateDto", "password","Your password must be between 10 to 20 characters."));
                }
            }
        }

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult.getAllErrors());
            return "member/updateForm";
        }

        int result = memberMapper.Update(updateDto.getPassword(), updateDto.getName(), loginUser);
        loginUser.setName(updateDto.getName());
        loginUser.setPassword(updateDto.getPassword());

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
