package com.example.mybatis.controller;

import com.example.mybatis.dto.FindPasswordDto;
import com.example.mybatis.entity.Member;
import com.example.mybatis.dao.MemberMapper;
import com.example.mybatis.entity.Order;
import com.example.mybatis.service.MemberService;
import com.example.mybatis.service.OrderService;
import com.example.mybatis.util.SessionConst;
import com.example.mybatis.util.argumentResolver.Login;
import com.example.mybatis.dto.SignInDto;
import com.example.mybatis.dto.UpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;
    private final AuthController authController;
    private final OrderService orderService;


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

        if(memberMapper.findByEmail(signInDto.getEmail()) != null) {
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
        model.addAttribute("order", orderService.findAvailableOrderByMemberId(loginUser.getId()));

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

    @GetMapping("/findpwd")
    public String findPasswordForm() {
        return "member/findPassword";
    }

    @PostMapping("/findpwd")
    public String findPassword(@RequestParam String email) {
        String message = memberService.findPassword(email);
        return "redirect:/message";
    }

    @GetMapping("/resetpwd/{authKey}")
    public String resetPasswordForm(@PathVariable String authKey, Model model) {
        model.addAttribute(new FindPasswordDto("", "", authKey));
        return "member/resetPassword";
    }

    @PostMapping("/resetpwd")
    public String resetPassword(@ModelAttribute FindPasswordDto findPasswordDto, BindingResult bindingResult) {

        if (!findPasswordDto.getPassword1().equals(findPasswordDto.getPassword2())) {
            bindingResult.addError(new ObjectError("findPasswordDto", "New password and check password are not equal."));
        }
        if(findPasswordDto.getPassword1().length() < 9 || findPasswordDto.getPassword1().length() > 21) {
            bindingResult.addError(new ObjectError("findPasswordDto", "Your password must be between 10 to 20 characters."));
        }

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult.getAllErrors());
            return "member/resetPassword";
        }

        String message = memberService.resetPassword(findPasswordDto.getPassword1(), findPasswordDto.getAuthKey());
        return "redirect:/message";
    }

    @PostMapping("/oauth2/add")
    public String addOauth2(@RequestParam String email, @RequestParam String name, @RequestParam String provider, Model model) {
        String message = memberService.saveOauth2(new Member(email, "google", name, "Y", provider));
        model.addAttribute("message", message);
        return "redirect:/message";
    }

    @PostMapping("/oauth2/link")
    public String linkOauth2(@RequestParam String email, @RequestParam String provider, Model model) {
        String message = memberService.linkOauth2(email, provider);
        model.addAttribute("message", message);
        return "message:/message";
    }




}
