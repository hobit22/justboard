package com.sparta.justboard.controller;

import com.sparta.justboard.model.Board;
import com.sparta.justboard.repository.BoardRepository;
import com.sparta.justboard.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if(userDetails == null){
            model.addAttribute("user","null");
        }else{
            model.addAttribute("user",userDetails.getUser().getUsername());
        }

        List<Board> boardList =boardRepository.findAllByOrderByModifiedAtDesc();
        model.addAttribute("boardList", boardList);
        return "index";
    }
}