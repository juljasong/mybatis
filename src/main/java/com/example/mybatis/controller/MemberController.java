package com.example.mybatis.controller;

import com.example.mybatis.dto.FindPasswordDTO;
import com.example.mybatis.entity.Member;
import com.example.mybatis.service.MemberService;
import com.example.mybatis.service.OrderService;
import com.example.mybatis.util.SessionConst;
import com.example.mybatis.util.argumentResolver.Login;
import com.example.mybatis.dto.SignInDTO;
import com.example.mybatis.dto.UpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthController authController;
    private final OrderService orderService;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute(new SignInDTO());
        return "member/addForm";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute SignInDTO signInDTO, BindingResult bindingResult, @RequestParam String password2, RedirectAttributes redirectAttributes) throws Exception {

        if(!signInDTO.getPassword().equals(password2)) {
            bindingResult.addError(new ObjectError("member", "Password is not equal."));
        }

        if(memberService.findMemberByEmail(signInDTO.getEmail()) != null) {
            bindingResult.addError(new ObjectError("member", "An account with this email already exists."));
        }

        if(memberService.findCntByName(signInDTO.getName()) > 0) {
            bindingResult.addError(new ObjectError("member", "An account with this name already exists."));
        }

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "member/addForm";
        }

        Member member = new Member();
        member.setEmail(signInDTO.getEmail());
        member.setName(signInDTO.getName());
        member.setPassword(signInDTO.getPassword());

        int result = memberService.add(member);
        if (result == 1) {
            redirectAttributes.addFlashAttribute("message", "Check your email.");
        }
        return "redirect:/message";
    }

    @GetMapping("/chk/{authKey}")
    public String checkMail(@PathVariable String authKey, RedirectAttributes redirectAttributes) {
        String message = memberService.modifyAuthKey(authKey);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/message";
    }

    @GetMapping("/updateForm")
    public String updateForm(@Login Member loginUser, Model model) {
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("order", orderService.findAvailableOrderByMemberId(loginUser.getId()));

        UpdateDTO updateDTO = new UpdateDTO();
        updateDTO.setName(loginUser.getName());
        updateDTO.setEmail(loginUser.getEmail());
        model.addAttribute("updateDTO", updateDTO);

        return "member/updateForm";
    }

    @PostMapping("/update")
    public String update(HttpServletRequest request,
                         @Login Member loginUser,
                         @Validated @ModelAttribute UpdateDTO updateDTO, BindingResult bindingResult,
                         Model model) throws Exception {
        Long memberId = loginUser.getId();

        if (loginUser.getProvider() == null) {

            if (updateDTO.getCurrentPassword().isBlank() && updateDTO.getPassword().isBlank() && updateDTO.getName().equals(loginUser.getName())) {
                return "redirect:/member/updateForm";
            } else {
                if (memberService.verifyCurrentPassword(loginUser.getId(), updateDTO.getCurrentPassword()) == 0) {
                    bindingResult.addError(new FieldError("updateDTO", "currentPassword", "Current password is not correct."));
                } else {
                    if (memberService.findCntByName(updateDTO.getName()) > 0) {
                        if (!updateDTO.getName().equals(loginUser.getName())) {
                            bindingResult.addError(new FieldError("updateDTO", "name", updateDTO.getName(), false, null, null, "Same name already exists."));
                        }
                    }
                    if (!updateDTO.getPassword().equals(updateDTO.getPassword2())) {
                        bindingResult.addError(new FieldError("updateDTO", "password2", "New password and check password are not equal."));
                    }
                    if (updateDTO.getPassword().length() < 9 || updateDTO.getPassword().length() > 21) {
                        bindingResult.addError(new FieldError("updateDTO", "password", "Your password must be between 10 to 20 characters."));
                    }
                }
            }
        }

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult.getAllErrors());
            model.addAttribute("order", orderService.findAvailableOrderByMemberId(loginUser.getId()));
            return "member/updateForm";
        }

        int result = memberService.modifyMember(updateDTO.getPassword(), updateDTO.getName(), loginUser);
        loginUser.setName(updateDTO.getName());
        loginUser.setPassword(updateDTO.getPassword());

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
    public String findPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        String message = memberService.findPasswordByEmail(email);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/message";
    }

    @GetMapping("/resetpwd/{authKey}")
    public String resetPasswordForm(@PathVariable String authKey, Model model) {
        model.addAttribute(new FindPasswordDTO("", "", authKey));
        return "member/resetPassword";
    }

    @PostMapping("/resetpwd")
    public String resetPassword(@ModelAttribute FindPasswordDTO findPasswordDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (!findPasswordDTO.getPassword1().equals(findPasswordDTO.getPassword2())) {
            bindingResult.addError(new ObjectError("findPasswordDTO", "New password and check password are not equal."));
        }
        if(findPasswordDTO.getPassword1().length() < 9 || findPasswordDTO.getPassword1().length() > 21) {
            bindingResult.addError(new ObjectError("findPasswordDTO", "Your password must be between 10 to 20 characters."));
        }

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult.getAllErrors());
            return "member/resetPassword";
        }

        String message = memberService.modifyPassword(findPasswordDTO.getPassword1(), findPasswordDTO.getAuthKey());
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/message";
    }

}
