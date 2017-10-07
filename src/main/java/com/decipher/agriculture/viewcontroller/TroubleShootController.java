package com.decipher.agriculture.viewcontroller;

import com.decipher.agriculture.data.farm.Farm;
import com.decipher.agriculture.service.farm.FarmService;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.view.form.farmDetails.FarmInfoView;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by decipher03 on 6/10/17.
 */
@Controller
public class TroubleShootController {
    @Autowired
    private FarmService farmService;

    @RequestMapping(value = "/troubleshoot.htm", method = RequestMethod.GET)
    public ModelAndView troubleShoot(@RequestParam(value = "farmId") int farmId){
        JSONObject myModel=new JSONObject();
        FarmInfoView farmInfoView=farmService.getBaselineFarmDetails(farmId);

        Farm farm = farmService.getFarmById(farmId);
        PlantingProfitLogger.info(farmInfoView.getStrategy());
        myModel.put("farmInfoView",farmInfoView);
        myModel.put("farm",farm);
        return new ModelAndView("trouble-shoot","model",myModel);
    }
}
