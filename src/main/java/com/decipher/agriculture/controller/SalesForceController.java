package com.decipher.agriculture.controller;

import com.decipher.agriculture.service.salesForce.SalesForceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/salesForceController")
public class SalesForceController {

    @Autowired
    private SalesForceService salesForceService;

    /**
     * verify for SalesForce account
     * @param enquiryId
     * @return
     */
    @RequestMapping(value = "/sendDetailsToSalesForce", method = {RequestMethod.GET, RequestMethod.POST})
    public String sendDetailsToSalesForce(@RequestParam(value = "enquiryId") Integer enquiryId) {
        salesForceService.submitLead(enquiryId);
        return "redirect:/";
    }
}
