package com.web3.start;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: mianyun.yt
 * @Date: 2022/12/23
 */
@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("name", "world");

        return "index";
    }

}
