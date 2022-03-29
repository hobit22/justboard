package com.sparta.justboard.controller;

import com.sparta.justboard.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if(userDetails == null){
            model.addAttribute("user","null");
        }else{
            model.addAttribute("user",userDetails.getUser().getUsername());
        }
        return "index";
    }
}