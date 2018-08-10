package com.decipher.agriculture.service.farmDetails;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created on 3/2/17 2:36 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */

public interface FarmOutputDetailsService {

    JSONArray buildForwardSalesContent(JSONObject outputDetails);

    JSONArray buildCropLimitContent(JSONObject outputDetails);
    String getYesNoForField(int usedAcres, int minimumAcres, int maximumAcres, String minOrMax);

    String getYesNo(int usedAcres, int minimumAcres, int maximumAcres, String minOrMax);

    JSONArray buildCropAcreageContent(JSONObject outputDetails);

    JSONObject buildResourcesContent(JSONObject outputDetails);

}
