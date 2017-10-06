package com.decipher.agriculture.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by decipher03 on 6/10/17.
 */
@Controller
public class TroubleShootController {

    @RequestMapping(value = "/troubleshoot.htm", method = RequestMethod.GET)
    public ModelAndView troubleShoot(){
        return new ModelAndView("trouble-shoot");
    }
}
