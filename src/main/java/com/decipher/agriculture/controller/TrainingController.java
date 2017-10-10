package com.decipher.agriculture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by abhishek on 28/9/16.
 */
@Controller
public class TrainingController {

    @RequestMapping(value = "/training.htm", method = {RequestMethod.GET})
    public ModelAndView renderTurorialsPage(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("training-page");

        return modelAndView;

    }

}
