package com.macaplix.squareGames.httpInterface;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HtmlInterface
{
    @RequestMapping("login")
    public ModelAndView loginpage()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

}
