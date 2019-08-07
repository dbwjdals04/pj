package com.bit.academy.controller;

import com.bit.academy.model.MemberVO;
import com.bit.academy.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;



    // 메인화면 요청

    @GetMapping("/member/main")
    public String main(){
        return "member/main";
    }

    // 회원가입 처리

    @GetMapping("/member/regist")
    public String regist() {

        return "member/regist";
    }

    @PostMapping("/member/regist")
    public String registExecute(Model model, @ModelAttribute MemberVO memberVO) {
        log.debug("---------회원가입 요청 처리-------");
        log.debug(memberVO.toString());
        this.memberService.regist(memberVO);

        log.debug("--------게시글 등록 결과---------");
        log.debug(memberVO.toString());

        memberVO = this.memberService.selectMember(memberVO.getM_no());
        model.addAttribute("member", memberVO);

        return "member/login";
    }

    //회원정보조회

    @GetMapping("/member/view/{m_no}")
    public String selectMember(Model model, int m_no) {
        model.addAttribute("member", this.memberService.selectMember(m_no));
        return "board/view";
    }

    //회원정보삭제

    @PostMapping("/member/view/{m_no}")
    public String deleteMember(HttpServletRequest request, @PathVariable int m_no) {
        this.memberService.deleteMember(request, m_no);
        return "member/main";
    }

    //회원정보수정

    @GetMapping("/member/update/{m_no}")
    public String updateMember(Model model, @PathVariable int m_no) {
        model.addAttribute("member", this.memberService.selectMember(m_no));
        return "member/update";
    }

    @PostMapping("/member/update/{m_no}")
    public String updateMemberExecute(Model model, @ModelAttribute MemberVO memberVO) {
        this.memberService.updateMember(memberVO);
        model.addAttribute("member", this.memberService.selectMember(memberVO.getM_no()));
        return "member/update_after";
    }

    //회원리스트

    @GetMapping("/member/list")
    public String memberList(Model model) {
        model.addAttribute("memberList", this.memberService.selectMemberList());

        List<MemberVO> memberList = new ArrayList<>();

        for (MemberVO memberVO : memberList) {

        }
        return "member/list";
    }
    //로그인

    /**
     * login form HTML
     * @return
     */
    @GetMapping("/member/login")
    public String login(MemberVO memberVO){

        return "member/login";
    }

    /**
     * login 수행
     * @param memberVO
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/member/login")
    public String loginExecute(@ModelAttribute MemberVO memberVO
            , Model model
            , HttpServletRequest request){

        memberVO = this.memberService.login(memberVO, request);
        model.addAttribute("member", memberVO);
        return "member/main";
    }

    @GetMapping("/member/idPwFind")
    public String idPwFind(){
        return "member/idPwFind";
    }

    //아이디찾기
    @GetMapping("/member/idFind")
    public String idFind(){
        return "/member/login";
    }

    @PostMapping("/member/idFind")
    public String idFindExecute(@ModelAttribute MemberVO memberVO, Model model, HttpServletRequest request){
        memberVO = this.memberService.idFind(memberVO,request);
        model.addAttribute("member",memberVO);

        return "member/login";
    }
    //비밀번호찾기
    @GetMapping("/member/pwFind")
    public String pwFind(){
        return "/member/login";
    }
    @PostMapping("/member/pwFind")
    public String pwFindExcute(@ModelAttribute MemberVO memberVO, Model model){
        memberVO = this.memberService.pwFind(memberVO);
        model.addAttribute("member",memberVO);

        return "member/login";
    }

    //로그아웃
    @PostMapping("member/main")
    public String logOut(HttpServletRequest request){
        this.memberService.logOut(request);
        return "member/main";
    }


}



