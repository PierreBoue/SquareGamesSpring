package com.macaplix.squareGames.httpInterface;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "square-games")
public class HtmlInterface
{
    @RequestMapping("/")
    private ModelAndView indexPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }
    @RequestMapping("/login")
    private ModelAndView loginPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }
    @RequestMapping("errorHtml")
    private ModelAndView errorHtml()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error.html");
        return modelAndView;
    }

}
